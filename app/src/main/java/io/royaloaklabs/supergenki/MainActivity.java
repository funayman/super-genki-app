package io.royaloaklabs.supergenki;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import io.royaloaklabs.supergenki.activities.FavoriteViewActivity;
import io.royaloaklabs.supergenki.activities.SearchActivity;
import io.royaloaklabs.supergenki.adapter.DictionaryViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.DictionaryEntry;
import io.royaloaklabs.supergenki.domain.SearchResult;
import io.royaloaklabs.supergenki.domain.Sense;
import sh.drt.supergenkiutil.furiganaview.FuriganaView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private DictionaryViewAdapter dictionaryViewAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private DictionaryAdapter dictionaryAdapter;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private Intent searchActivity;

  private void showAboutDialog() {
    AlertDialog.Builder adb = new AlertDialog.Builder(this);
    View dialogView = getLayoutInflater().inflate(R.layout.about_view, null);

    TextView tv = (TextView) dialogView.findViewById(R.id.about_dialog_textview);
    String sourceString = getString(R.string.about_dialog_text);
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      tv.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
    } else {
      tv.setText(Html.fromHtml(sourceString));
    }
    tv.setMovementMethod(LinkMovementMethod.getInstance());

    adb.setView(dialogView).setPositiveButton(R.string.close, null);
    adb.create().show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case android.R.id.home:
        if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
          drawerLayout.closeDrawer(GravityCompat.START);
        } else {
          drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
      default:
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
    drawerLayout = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {
            // set item as selected to persist highlight
            menuItem.setChecked(true);
            // close drawer when item is tapped
            drawerLayout.closeDrawers();

            switch(menuItem.getItemId()) {
              case R.id.menu_about:
                // User chose the "About" item, show the app settings UI...
                showAboutDialog();
                break;
              case R.id.menu_favorites:
                Intent i = new Intent(getApplicationContext(), FavoriteViewActivity.class);
                startActivity(i);
                break;
              case R.id.menu_search:
                startActivity(searchActivity);
                break;
            }
            return true;
          }
        });

    ActionBar actionbar = getSupportActionBar();
    actionbar.setDisplayHomeAsUpEnabled(true);
    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

    dictionaryAdapter = new DictionaryAdapter(getApplicationContext());

    // use a linear layout manager
    layoutManager = new LinearLayoutManager(this);

    SearchResult wordOfTheDay = dictionaryAdapter.getOneSearchResultById(getDailyIndex());
    DictionaryEntry entry = dictionaryAdapter.getOne(wordOfTheDay.getId());

    TextView romajiText = findViewById(R.id.detailedRomajiView);
    TextView englishText = findViewById(R.id.detailedTranslationView);
    FuriganaView furiganaView = findViewById(R.id.japaneseTextView);

    String japaneseText = (entry.getFurigana().isEmpty()) ? entry.getJapanese() : String.format("{%s;%s}", entry.getJapanese(), entry.getFurigana());
    furiganaView.setText(japaneseText);

    romajiText.setText(entry.getRomaji());

    // add to other card view
    List<Sense> senses = entry.getSenses();
    if(senses.size() == 1) {
      englishText.setText(senses.get(0).toJoinedString());
    } else if(senses.size() == 2) {
      englishText.setText(String.format("1) %s\n2) %s\n",
          senses.get(0).toJoinedString(), senses.get(1).toJoinedString()));
    } else if(senses.size() == 3) {
      englishText.setText(String.format("1) %s\n2) %s\n3) %s\n",
          senses.get(0).toJoinedString(), senses.get(1).toJoinedString(), senses.get(2).toJoinedString()));
    } else {
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < senses.size(); i++) {
        sb.append(String.format("%d) %s\n", i + 1, entry.getSenses().get(i).toJoinedString()));
      }
      englishText.setText(sb.toString());
    }
  }

  private Long getDailyIndex() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String stringDate = dateFormat.format(new Date());
    BigInteger tableHashResult = BigInteger.valueOf(0L);

    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(stringDate.getBytes(), 0, stringDate.length());
      BigInteger md5Base10 = new BigInteger(1, messageDigest.digest());
      tableHashResult = md5Base10.mod(BigInteger.valueOf(dictionaryAdapter.getEntryTableCount()));
    } catch(Exception e) {
      e.printStackTrace();
      //Return a static number if for some reason the MD5 failed. (Maybe make this random #?)
      return 10L;
    }
    return tableHashResult.longValue();
  }
}

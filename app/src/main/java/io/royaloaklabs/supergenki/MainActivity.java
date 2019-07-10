package io.royaloaklabs.supergenki;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import io.royaloaklabs.supergenki.activities.FavoriteViewActivity;
import io.royaloaklabs.supergenki.activities.SearchActivity;
import io.royaloaklabs.supergenki.activities.SettingsActivity;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.DictionaryEntry;
import io.royaloaklabs.supergenki.repo.WordOfTheDayRepository;
import sh.drt.supergenkiutil.furiganaview.FuriganaView;

public class MainActivity extends AppCompatActivity {
  private RecyclerView.LayoutManager layoutManager;
  private DictionaryAdapter dictionaryAdapter;
  private DrawerLayout drawerLayout;
  private Intent searchActivity;
  private WordOfTheDayRepository wotdRepo;

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_icon_navigation, menu);

    return true;
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
      case R.id.search_icon:
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(i);
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

            Intent i = null;
            switch(menuItem.getItemId()) {
              case R.id.menu_settings:
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                i.putExtra(SettingsActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment.class.getName());
                i.putExtra(SettingsActivity.EXTRA_NO_HEADERS, true);
                startActivity(i);
                break;
              case R.id.menu_favorites:
                i = new Intent(getApplicationContext(), FavoriteViewActivity.class);
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

    // set up word of the day
    wotdRepo = new WordOfTheDayRepository(this.getApplicationContext());
    DictionaryEntry wordOfTheDay = wotdRepo.get();

    TextView romajiText = findViewById(R.id.detailedRomajiView);
    TextView englishText = findViewById(R.id.detailedTranslationView);
    FuriganaView furiganaView = findViewById(R.id.japaneseTextView);

    String japaneseText = (wordOfTheDay.getFurigana().isEmpty()) ?
        wordOfTheDay.getJapanese() : String.format("{%s;%s}", wordOfTheDay.getJapanese(), wordOfTheDay.getFurigana());
    furiganaView.setText(japaneseText);

    romajiText.setText(wordOfTheDay.getRomaji());
    englishText.setText(wordOfTheDay.getSensesAsString());
  }

}

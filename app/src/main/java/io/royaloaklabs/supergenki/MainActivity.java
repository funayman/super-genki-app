package io.royaloaklabs.supergenki;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.adapter.RecyclerViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;

public class MainActivity extends AppCompatActivity {

  private TextView mTextMessage;
  private RecyclerView rv;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.nav_about:
        // User chose the "About" item, show the app settings UI...
        showAboutDialog();
        return true;

      default:
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
  }

  private void showAboutDialog() {
    AlertDialog.Builder adb = new AlertDialog.Builder(this);
    View dialogView = getLayoutInflater().inflate(R.layout.about_view, null);

    TextView tv = (TextView) dialogView.findViewById(R.id.about_dialog_textview);
    String sourceString = getString(R.string.about_dialog_text);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      tv.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
    } else {
      tv.setText(Html.fromHtml(sourceString));
    }

    adb.setView(dialogView).setPositiveButton("Close", null);
    adb.create().show();
  }


  public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

    final MenuItem searchMenuItem = menu.findItem( R.id.search);
    final SearchView searchView   = (SearchView) searchMenuItem.getActionView();
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        Toast toast = Toast.makeText(getApplicationContext(), "Search For="+query, Toast.LENGTH_SHORT);
        toast.show();
        if( ! searchView.isIconified()) {
          searchView.setIconified(true);
        }
        searchMenuItem.collapseActionView();
        return false;
      }
      @Override
      public boolean onQueryTextChange(String s) {
        // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
        return false;
      }
    });
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DictionaryAdapter da = new DictionaryAdapter(getApplicationContext());

    // mTextMessage = (TextView) findViewById(R.id.message);
    // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    rv = (RecyclerView)findViewById(R.id.rv);
    rv.setHasFixedSize(true);

    // use a linear layout manager
    mLayoutManager = new LinearLayoutManager(this);
    rv.setLayoutManager(mLayoutManager);


    mAdapter = new RecyclerViewAdapter(da.getRandomData());
    rv.setAdapter(mAdapter);

  }
}

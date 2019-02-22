package io.royaloaklabs.supergenki;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.adapter.DictionaryViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.database.tasks.DatabaseQueryTask;

public class MainActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private DictionaryViewAdapter dictionaryViewAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private DictionaryAdapter dictionaryAdapter;

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
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
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      tv.setText(Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY));
    } else {
      tv.setText(Html.fromHtml(sourceString));
    }
    tv.setMovementMethod(LinkMovementMethod.getInstance());

    adb.setView(dialogView).setPositiveButton(R.string.close, null);
    adb.create().show();
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.navigation, menu);

    final MenuItem searchMenuItem = menu.findItem(R.id.search);
    final SearchView searchView = (SearchView) searchMenuItem.getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      DatabaseQueryTask task = null;

      @Override
      public boolean onQueryTextSubmit(String q) {
        if(task != null) {
          task.cancel(Boolean.TRUE);
          task = null;
        }
        task = new DatabaseQueryTask(dictionaryAdapter, dictionaryViewAdapter);
        task.execute(q);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String q) {
        if(task != null) {
          task.cancel(Boolean.TRUE);
          task = null;
        }
        task = new DatabaseQueryTask(dictionaryAdapter, dictionaryViewAdapter);
        task.execute(q);
        return Boolean.TRUE;
      }
    });

    // repopulate the ListView with random entries when the user closes the search bar
    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {
        dictionaryViewAdapter.updateEntries(dictionaryAdapter.getRandomData());
        return false;
      }
    });
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    dictionaryAdapter = new DictionaryAdapter(getApplicationContext());

    recyclerView = (RecyclerView) findViewById(R.id.rv);
    recyclerView.setHasFixedSize(true);

    // use a linear layout manager
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);


    dictionaryViewAdapter = new DictionaryViewAdapter(dictionaryAdapter.getRandomData());
    recyclerView.setAdapter(dictionaryViewAdapter);
  }
}

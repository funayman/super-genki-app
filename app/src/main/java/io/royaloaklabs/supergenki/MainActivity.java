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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.adapter.RecyclerViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.database.tasks.QueryDatabaseTask;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
  public static final String MESSAGE = "SERIALIZEDFORM";
  private TextView mTextMessage;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  private DictionaryAdapter dictionaryAdapter;

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

    adb.setView(dialogView).setPositiveButton(R.string.close, null);
    adb.create().show();
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.navigation, menu);

    final MenuItem searchMenuItem = menu.findItem(R.id.search);
    final SearchView searchView   = (SearchView) searchMenuItem.getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      QueryDatabaseTask currentTask = null;

      @Override
      public boolean onQueryTextSubmit(String q) {
        doQuery(q);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String q) {
        doQuery(q);
        return false;
      }

      private void doQuery(String q) {
        RecyclerView.Adapter currentAdapter = mAdapter;
        if(null == currentTask) {
          currentTask = new QueryDatabaseTask(dictionaryAdapter);
        } else {
          currentTask.cancel(true);
          currentTask = new QueryDatabaseTask(dictionaryAdapter);
        }
        try {
          mAdapter = currentTask.execute(q).get();
        } catch(ExecutionException e) {
          mAdapter = currentAdapter;
        } catch(InterruptedException e) {
          mAdapter = currentAdapter;
        } finally {
          recyclerView.setAdapter(mAdapter);
        }
      }
    });

    // repopulate the ListView with random entries when the user closes the search bar
    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {
        mAdapter = new RecyclerViewAdapter(dictionaryAdapter.getRandomData());
        recyclerView.setAdapter(mAdapter);
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

    recyclerView = (RecyclerView)findViewById(R.id.rv);
    recyclerView.setHasFixedSize(true);

    // use a linear layout manager
    mLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(mLayoutManager);


    mAdapter = new RecyclerViewAdapter(dictionaryAdapter.getRandomData());
    recyclerView.setAdapter(mAdapter);
  }
}

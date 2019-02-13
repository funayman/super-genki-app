package io.royaloaklabs.supergenki;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

  private DictionaryAdapter dictionaryAdapter;

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.navigation, menu);

    final MenuItem searchMenuItem = menu.findItem(R.id.search);
    final SearchView searchView   = (SearchView) searchMenuItem.getActionView();
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String q) {
        if(!searchView.isIconified()) {
          searchView.setIconified(true);
        }
        searchMenuItem.collapseActionView();

        mAdapter = new RecyclerViewAdapter(dictionaryAdapter.Search(q));
        rv.setAdapter(mAdapter);

        return false;
      }


      @Override
      public boolean onQueryTextChange(String q) {
        mAdapter = new RecyclerViewAdapter(dictionaryAdapter.Search(q));
        rv.setAdapter(mAdapter);
        return false;
      }

    });

    // repopulate the ListView with random entries when the user closes the search bar
    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {
        mAdapter = new RecyclerViewAdapter(dictionaryAdapter.getRandomData());
        rv.setAdapter(mAdapter);
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

    // mTextMessage = (TextView) findViewById(R.id.message);
    // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    rv = (RecyclerView)findViewById(R.id.rv);
    rv.setHasFixedSize(true);

    // use a linear layout manager
    mLayoutManager = new LinearLayoutManager(this);
    rv.setLayoutManager(mLayoutManager);


    mAdapter = new RecyclerViewAdapter(dictionaryAdapter.getRandomData());
    rv.setAdapter(mAdapter);
  }
}

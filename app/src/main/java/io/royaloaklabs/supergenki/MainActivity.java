package io.royaloaklabs.supergenki;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.adapter.RecyclerViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.JapaneseCard;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        // specify an adapter (see also next example)
        ArrayList<JapaneseCard> japaneseCards = new ArrayList<>();
        japaneseCards.add(new JapaneseCard("こんにちは", "Translation: Hello", "Pronunciation: Kon'nichiwa"));
        japaneseCards.add(new JapaneseCard("こちは", "Translation: Eric Rocks", "Pronunciation: E-Rock"));

        // mAdapter = new RecyclerViewAdapter(japaneseCards);
        if(da.getRandomData().size() != 0) {
            mAdapter = new RecyclerViewAdapter(da.getRandomData());
        } else {
            mAdapter = new RecyclerViewAdapter(japaneseCards);
        }
        rv.setAdapter(mAdapter);

    }
}

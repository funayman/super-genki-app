package io.royaloaklabs.supergenki;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

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

        mAdapter = new RecyclerViewAdapter(da.getRandomData());

        rv.setAdapter(mAdapter);

    }
}

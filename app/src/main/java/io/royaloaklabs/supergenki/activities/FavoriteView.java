package io.royaloaklabs.supergenki.activities;

import android.os.Bundle;
import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.adapter.FavoriteViewAdapter;

public class FavoriteView extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite_view);

    RecyclerView recyclerView = findViewById(R.id.favoriteRecyclerView);
    final FavoriteViewAdapter favoriteViewAdapter = new FavoriteViewAdapter(this);
    recyclerView.setAdapter(favoriteViewAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}

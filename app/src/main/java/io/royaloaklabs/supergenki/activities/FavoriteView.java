package io.royaloaklabs.supergenki.activities;

import android.os.Bundle;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.adapter.FavoriteViewAdapter;
import io.royaloaklabs.supergenki.view.FavoriteViewModel;

public class FavoriteView extends AppCompatActivity {
  private FavoriteViewModel favoriteViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite_view);

    favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

    RecyclerView recyclerView = findViewById(R.id.favoriteRecyclerView);
    final FavoriteViewAdapter favoriteViewAdapter = new FavoriteViewAdapter(this, favoriteViewModel.getAll());
    recyclerView.setAdapter(favoriteViewAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }
}

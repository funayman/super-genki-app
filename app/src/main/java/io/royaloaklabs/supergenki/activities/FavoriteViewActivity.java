package io.royaloaklabs.supergenki.activities;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.adapter.FavoriteViewAdapter;
import io.royaloaklabs.supergenki.domain.Favorite;
import io.royaloaklabs.supergenki.view.FavoriteViewModel;
import io.royaloaklabs.supergenki.view.SwipeToDeleteCallback;

import java.util.List;

public class FavoriteViewActivity extends AppCompatActivity {
  private RecyclerView recyclerView;

  private FavoriteViewAdapter favoriteViewAdapter;
  private FavoriteViewModel favoriteViewModel;
  private SwipeToDeleteCallback callback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite_view);

    recyclerView = findViewById(R.id.favoriteRecyclerView);
    favoriteViewAdapter = new FavoriteViewAdapter(this);
    recyclerView.setAdapter(favoriteViewAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    favoriteViewModel.getAll().observe(this, new Observer<List<Favorite>>() {
      @Override
      public void onChanged(List<Favorite> favorites) {
        favoriteViewAdapter.setFavoriteList(favorites);
      }
    });

    callback = makeCallback();

    new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);

  }

  private SwipeToDeleteCallback makeCallback() {
    return new SwipeToDeleteCallback(this) {
      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        final Favorite favorite = favoriteViewAdapter.getFavoriteFromList(position);

        favoriteViewAdapter.removeFavoriteFromList(position);
        favoriteViewModel.delete(favorite);

        Snackbar snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.remove_jp_fav, favorite.getJapanese()),
            Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            favoriteViewModel.insert(favorite);
            favoriteViewAdapter.restoreItem(position, favorite);
          }
        });
        snackbar.show();
      }
    };
  }
}

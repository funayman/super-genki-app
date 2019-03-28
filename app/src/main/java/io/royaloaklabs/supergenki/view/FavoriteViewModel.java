package io.royaloaklabs.supergenki.view;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.royaloaklabs.supergenki.domain.Favorite;
import io.royaloaklabs.supergenki.repo.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
  private FavoriteRepository favoriteRepository;
  private List<Favorite> favoriteList;

  public FavoriteViewModel(@NonNull Application application) {
    super(application);
    favoriteRepository = new FavoriteRepository(application);
    favoriteList = favoriteRepository.getAll();
  }

  public List<Favorite> getAll() {
    return favoriteList;
  }

  public void insert(Favorite favorite) {
    favoriteRepository.insert(favorite);
  }
}

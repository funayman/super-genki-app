package io.royaloaklabs.supergenki.view;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.royaloaklabs.supergenki.domain.Favorite;
import io.royaloaklabs.supergenki.repo.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
  private FavoriteRepository favoriteRepository;
  private LiveData<List<Favorite>> favoriteList;

  public FavoriteViewModel(@NonNull Application application) {
    super(application);
    favoriteRepository = new FavoriteRepository(application);
    favoriteList = favoriteRepository.getAll();
  }

  public void delete(Favorite favorite) { this.favoriteRepository.delete(favorite); }

  public LiveData<List<Favorite>> getAll() { return favoriteList; }

  public Favorite getOne(Long id) { return this.favoriteRepository.getOne(id); }

  public void insert(Favorite favorite) { favoriteRepository.insert(favorite); }
}

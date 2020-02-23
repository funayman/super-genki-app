package io.royaloaklabs.supergenki.view;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.royaloaklabs.supergenki.domain.CustomList;
import io.royaloaklabs.supergenki.repo.CustomListRepository;

import java.util.List;

public class CustomListViewModel extends AndroidViewModel {
  private CustomListRepository favoriteRepository;
  private LiveData<List<CustomList>> favoriteList;

  public CustomListViewModel(@NonNull Application application) {
    super(application);
    favoriteRepository = new CustomListRepository(application);
    favoriteList = favoriteRepository.getAll();
  }

  public void delete(CustomList favorite) { this.favoriteRepository.delete(favorite); }

  public LiveData<List<CustomList>> getAll() { return favoriteList; }

  public CustomList getOne(String id) { return this.favoriteRepository.getOne(id); }

  public void insert(CustomList favorite) { favoriteRepository.insert(favorite);  }
}

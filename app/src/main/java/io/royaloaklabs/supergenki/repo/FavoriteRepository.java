package io.royaloaklabs.supergenki.repo;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import io.royaloaklabs.supergenki.dao.FavoriteDao;
import io.royaloaklabs.supergenki.database.FavoriteDatabase;
import io.royaloaklabs.supergenki.domain.Favorite;

import java.util.List;

public class FavoriteRepository {
  private FavoriteDao favoriteDao;
  private LiveData<List<Favorite>> favoriteList;

  public FavoriteRepository(Application application) {
    FavoriteDatabase db = FavoriteDatabase.getDatabase(application);
    favoriteDao = db.favoriteDao();
    favoriteList = favoriteDao.getAll();
  }

  public LiveData<List<Favorite>> getAll() {
    return this.favoriteList;
  }

  public Favorite getOne(Long id) { return this.favoriteDao.getOne(id); }

  public void insert(Favorite favorite) {
    new insertAsyncTask(favoriteDao).execute(favorite);
  }

  private class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {
    FavoriteDao favoriteDao;

    public insertAsyncTask(FavoriteDao favoriteDao) {
      this.favoriteDao = favoriteDao;
    }

    @Override
    protected Void doInBackground(Favorite... favorites) {
      favoriteDao.insert(favorites[0]);
      return null;
    }
  }

  public void delete(Favorite favorite) { this.favoriteDao.delete(favorite); }
}

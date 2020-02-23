package io.royaloaklabs.supergenki.repo;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import io.royaloaklabs.supergenki.dao.CustomListDao;
import io.royaloaklabs.supergenki.database.CustomListsDatabase;
import io.royaloaklabs.supergenki.domain.CustomList;

import java.util.List;

public class CustomListRepository {
  private CustomListDao favoriteDao;
  private LiveData<List<CustomList>> favoriteList;

  public CustomListRepository(Application application) {
    CustomListsDatabase db = CustomListsDatabase.getDatabase(application);
    favoriteDao = db.customListDao();
    favoriteList = favoriteDao.getAll();
  }

  public LiveData<List<CustomList>> getAll() { return this.favoriteList; }

  public CustomList getOne(String id) { return this.favoriteDao.getOne(id); }

  public void insert(CustomList favorite) { new insertAsyncTask(favoriteDao).execute(favorite); }

  private class insertAsyncTask extends AsyncTask<CustomList, Void, Void> {
    CustomListDao favoriteDao;

    public insertAsyncTask(CustomListDao favoriteDao) { this.favoriteDao = favoriteDao; }

    @Override
    protected Void doInBackground(CustomList... favorites) {
      favoriteDao.insert(favorites[0]);
      return null;
    }
  }

  public void delete(CustomList favorite) {
    this.favoriteDao.delete(favorite);
  }
}

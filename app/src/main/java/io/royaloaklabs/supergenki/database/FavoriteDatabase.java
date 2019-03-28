package io.royaloaklabs.supergenki.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.royaloaklabs.supergenki.dao.FavoriteDao;
import io.royaloaklabs.supergenki.domain.Favorite;

@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
  public abstract FavoriteDao favoriteDao();

  private static volatile FavoriteDatabase INSTANCE;

  public static FavoriteDatabase getDatabase(final Context context) {
    if(INSTANCE == null) {
      synchronized(FavoriteDatabase.class) {
        if(INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "favorite_database").build();
        }
      }
    }

    return INSTANCE;
  }
}

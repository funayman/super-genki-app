package io.royaloaklabs.supergenki.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.royaloaklabs.supergenki.dao.CustomListDao;
import io.royaloaklabs.supergenki.domain.CustomList;

@Database(entities = {CustomList.class}, version = 1, exportSchema = false)
public abstract class CustomListsDatabase extends RoomDatabase {
  public abstract CustomListDao customListDao();

  private static volatile CustomListsDatabase INSTANCE;

  public static CustomListsDatabase getDatabase(final Context context) {
    if(INSTANCE == null) {
      synchronized(CustomListsDatabase.class) {
        if(INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CustomListsDatabase.class, "custom_lists").allowMainThreadQueries().build();
        }
      }
    }

    return INSTANCE;
  }
}

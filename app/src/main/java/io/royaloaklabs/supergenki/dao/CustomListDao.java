package io.royaloaklabs.supergenki.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.royaloaklabs.supergenki.domain.CustomList;

import java.util.List;

@Dao
public interface CustomListDao {
  @Insert
  void insert(CustomList favorite);

  @Query("DELETE FROM customlists_table")
  void deleteAll();

  @Delete
  void delete(CustomList favorite);

  @Query("SELECT * FROM customlists_table WHERE ID = :id")
  CustomList getOne(String id);

  @Query("SELECT * FROM customlists_table ORDER BY dateAdded DESC")
  LiveData<List<CustomList>> getAll();
}

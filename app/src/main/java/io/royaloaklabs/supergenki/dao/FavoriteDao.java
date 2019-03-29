package io.royaloaklabs.supergenki.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.royaloaklabs.supergenki.domain.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
  @Insert
  void insert(Favorite favorite);

  @Query("DELETE FROM favorite_table")
  void deleteAll();

  @Delete
  void delete(Favorite favorite);

  @Query("SELECT * FROM favorite_table WHERE entryId = :id")
  Favorite getOne(Long id);

  @Query("SELECT * FROM favorite_table ORDER BY dateAdded DESC")
  List<Favorite> getAll();
}

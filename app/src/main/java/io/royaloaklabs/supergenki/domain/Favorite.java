package io.royaloaklabs.supergenki.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite {

  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "entryId")
  private Long entryId;

  @ColumnInfo(name = "dateAdded")
  private Long dateAdded;

  public Favorite(Long entryId, Long dateAdded) {
    this.entryId = entryId;
    this.dateAdded = System.currentTimeMillis() / 1000L;
  }

  @NonNull
  public Long getEntryId() {
    return entryId;
  }

  public Long getDateAdded() {
    return dateAdded;
  }
}

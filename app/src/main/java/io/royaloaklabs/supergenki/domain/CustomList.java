package io.royaloaklabs.supergenki.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customlists_table")
public class CustomList {

  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "ID")
  String id;

  @ColumnInfo(name = "listName")
  String listName;

  @ColumnInfo(name = "listSize")
  Integer listSize;

  @ColumnInfo(name = "dateAdded")
  private Long dateAdded;

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public Long getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(Long dateAdded) {
    this.dateAdded = dateAdded;
  }

  public Integer getListSize() {
    return listSize;
  }

  public void setListSize(Integer listSize) {
    this.listSize = listSize;
  }
}

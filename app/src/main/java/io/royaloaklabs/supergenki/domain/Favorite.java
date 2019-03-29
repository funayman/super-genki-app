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

  @ColumnInfo(name = "japanese")
  private String japanese;

  @ColumnInfo(name = "furigana")
  private String furigana;

  @ColumnInfo(name = "english")
  private String english;

  @ColumnInfo(name = "dateAdded")
  private Long dateAdded;

  public Favorite(@NonNull Long entryId, String japanese, String furigana, String english, Long dateAdded) {
    this.entryId = entryId;
    this.japanese = japanese;
    this.furigana = furigana;
    this.english = english;
    this.dateAdded = dateAdded;
  }

  @NonNull
  public Long getEntryId() { return entryId; }

  public Long getDateAdded() { return dateAdded; }

  public String getJapanese() { return japanese; }

  public String getFurigana() { return furigana; }

  public String getEnglish() { return english; }
}

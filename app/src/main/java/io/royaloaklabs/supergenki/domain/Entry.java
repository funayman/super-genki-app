package io.royaloaklabs.supergenki.domain;

import android.database.Cursor;

import java.util.Collections;
import java.util.List;

public class Entry {

  // Constants
  public static final String DELIMITER = " ";
  public static final String SENSE_DELIMITER = "(SG)";
  public static final String GLOSS_DELIMITER = ";;";

  // DB Constants
  public static final String ENTRY_TABLE_NAME = "einihongo";
  public static final String ID_ROW_NAME = "docid";
  public static final String KANJI_ROW_NAME = "kanji";
  public static final String KANA_ROW_NAME = "kana";
  public static final String ENGLISH_ROW_NAME = "gloss";

  private long id;

  private String kanji;
  private List<String> kanjiAlt;

  private String kana;
  private List<String> kanaAlt;

  private List<Sense> senses;

  private Entry() { } // empty constructor

  private Entry(Builder builder) {
    this.id = builder.id;
    this.kanji = builder.kanji;
    this.kanjiAlt = builder.kanjiAlt;
    this.kana = builder.kana;
    this.kanaAlt = builder.kanaAlt;
    this.senses = builder.senses;
  }

  public String getKanji() {
    return kanji;
  }

  public List<String> getKanjiAlt() {
    return kanjiAlt;
  }

  public String getKana() {
    return kana;
  }

  public List<String> getKanaAlt() {
    return kanaAlt;
  }

  public List<Sense> getSenses() {
    return senses;
  }

  public static class Builder {
    private long id;
    private String kanji;
    private List<String> kanjiAlt;

    private String kana;
    private List<String> kanaAlt;

    private List<Sense> senses;

    public Builder() {
      id = 0;
      kanji = "";
      kanjiAlt = Collections.EMPTY_LIST;

      kana = "";
      kanaAlt = Collections.EMPTY_LIST;

      senses = Collections.EMPTY_LIST;
    }

    public Builder(String kanji, String kana) {
      this.kanji = kanji;
      this.kana = kana;
    }

    public Entry build() {
      return new Entry(this);
    }

    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    public Builder setKanji(String kanji) {
      this.kanji = kanji;
      return this;
    }

    public Builder setKanjiAlt(List<String> kanjiAlt) {
      this.kanjiAlt = kanjiAlt;
      return this;
    }

    public Builder setKana(String kana) {
      this.kana = kana;
      return this;
    }

    public Builder setKanaAlt(List<String> kanaAlt) {
      this.kanaAlt = kanaAlt;
      return this;
    }

    public Builder setSenses(List<Sense> senses) {
      this.senses = senses;
      return this;
    }
  }
}

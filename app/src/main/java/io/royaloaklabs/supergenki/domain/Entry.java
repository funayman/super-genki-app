package io.royaloaklabs.supergenki.domain;

import java.util.Collections;
import java.util.List;

public class Entry {

  // Constants
  public static final String DELIMITER = " ";
  public static final String SENSE_DELIMITER = "\\(SG\\)";
  public static final String GLOSS_DELIMITER = ";;";

  // DB Constants
  public static final String ENTRY_TABLE_NAME = "einihongo";
  public static final String ID_COL_NAME = "docid";
  public static final String JAPANESE_COL_NAME = "japanese";
  public static final String FURIGANA_COL_NAME = "furigana";
  public static final String ENGLISH_COL_NAME = "english";
  public static final String ROMAJI_COL_NAME = "romanji";
  public static final String FREQUENCY_COL_NAME = "freq";

  private long id;

  private String kanji;
  private List<String> kanjiAlt;

  private String kana;
  private List<String> kanaAlt;

  private List<Sense> senses;

  private String romaji;

  protected Entry() { } // empty constructor

  protected Entry(Builder builder) {
    this.id = builder.id;
    this.kanji = builder.kanji;
    this.kanjiAlt = builder.kanjiAlt;
    this.kana = builder.kana;
    this.kanaAlt = builder.kanaAlt;
    this.senses = builder.senses;
    this.romaji = builder.romaji;
  }

  public long getId() {
    return id;
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

  public String getRomaji() {
    return romaji;
  }

  public String getSensesAsString() {
    if(this.senses.size() == 1) {
      return this.senses.get(0).toJoinedString();
    }

    if(this.senses.size() == 2) {
      return String.format("%s; %s", this.senses.get(0).toJoinedString(), this.senses.get(1).toJoinedString());
    }

    if(this.senses.size() == 3) {
      return String.format("%s; %s; %s",
          this.senses.get(0).toJoinedString(), this.senses.get(1).toJoinedString(), this.senses.get(2).toJoinedString());
    }

    StringBuilder sb = new StringBuilder();
    sb.append(this.senses.get(0).toJoinedString());
    for(int i = 1; i < this.senses.size(); i++) {
      sb.append("; ");
      sb.append(this.senses.get(i));
    }
    return sb.toString();
  }

  public static class Builder {
    private long id;
    private String kanji;
    private List<String> kanjiAlt;

    private String kana;
    private List<String> kanaAlt;

    private List<Sense> senses;

    private String romaji;

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

    public Builder setRomaji(String romaji) {
      this.romaji = romaji;
      return this;
    }
  }
}

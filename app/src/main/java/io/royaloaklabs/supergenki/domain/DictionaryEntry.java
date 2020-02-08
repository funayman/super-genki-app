package io.royaloaklabs.supergenki.domain;

import java.util.ArrayList;
import java.util.List;

public class DictionaryEntry {

  // Constants
  public static final String SENSE_DELIMITER = "\\(SG\\)";

  // DB Constants
  public static final String DEFINITION_TABLE_NAME = "definitions";
  public static final String READING_TABLE_NAME = "readings";
  public static final String ENTRY_TABLE_NAME = "einihongo";
  public static final String ID_COL_NAME = "entryid";
  public static final String JAPANESE_COL_NAME = "japanese";
  public static final String FURIGANA_COL_NAME = "furigana";
  public static final String ENGLISH_COL_NAME = "english";
  public static final String ROMAJI_COL_NAME = "romaji";
  public static final String FREQUENCY_COL_NAME = "freq";

  public static final String ALTKANJI_COL_NAME = "altkanji";
  public static final String ALTKANA_COL_NAME = "altkana";
  public static final String GLOSS_COL_NAME = "gloss";
  public static final String PART_OF_SPEECH_COL_NAME = "pos";


  private long id;

  private String japanese;
  private String furigana;

  private String altKanji;
  private String altKana;

  private List<Sense> senses;

  private String romaji;

  public DictionaryEntry(long id) {
    this.id = id;
    this.senses = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getJapanese() {
    return japanese;
  }

  public void setJapanese(String japanese) {
    this.japanese = japanese;
  }

  public String getFurigana() {
    return furigana;
  }

  public void setFurigana(String furigana) {
    this.furigana = furigana;
  }

  public String getAltKanji() {
    return altKanji;
  }

  public void setAltKanji(String altKanji) {
    this.altKanji = altKanji;
  }

  public String getAltKana() {
    return altKana;
  }

  public void setAltKana(String altKana) {
    this.altKana = altKana;
  }

  public List<Sense> getSenses() {
    return senses;
  }

  public void setSenses(List<Sense> senses) {
    this.senses = senses;
  }

  public String getRomaji() {
    return romaji;
  }

  public void setRomaji(String romaji) {
    this.romaji = romaji;
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
      sb.append(this.senses.get(i).toJoinedString());
    }
    return sb.toString();
  }

}

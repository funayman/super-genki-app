package io.royaloaklabs.supergenki.domain;

public class SearchResult {

  private long id;
  private String japanese;
  private String furigana;
  private String english;
  private String romaji;

  public SearchResult() { } // empty constuctor

  public SearchResult(long id, String japanese, String furigana, String english, String romaji) {
    this.id = id;
    this.japanese = japanese;
    this.furigana = furigana;
    this.english = english;
    this.romaji = romaji;
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

  public String getEnglish() {
    return english;
  }

  public void setEnglish(String english) {
    this.english = english;
  }

  public String getRomaji() {
    return romaji;
  }

  public void setRomaji(String romaji) {
    this.romaji = romaji;
  }

}

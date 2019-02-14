package io.royaloaklabs.supergenki.domain;

import android.database.Cursor;

import java.util.LinkedList;
import java.util.List;

public class JapaneseCard {
  public String japaneseText;
  public String kanaText;
  public String englishText;

  public JapaneseCard() { } // empty constructor

  public JapaneseCard(String japaneseText, String kanaText, String englishText) {
    this.japaneseText = japaneseText;
    this.kanaText = kanaText;
    this.englishText = englishText;
  }

  public void setJapaneseText(String japaneseText) {
    this.japaneseText = japaneseText;
  }

  public void setKanaText(String kanaText) {
    this.kanaText = kanaText;
  }

  public void setEnglishText(String englishText) {
    this.englishText = englishText;
  }

  public static List<JapaneseCard> buildListFromCursor(Cursor cursor) {
    List<JapaneseCard> japaneseCards = new LinkedList<JapaneseCard>();

    for (; cursor.moveToNext(); ) {
      JapaneseCard jpCard = new JapaneseCard();

      String kanjiCol = cursor.getString(cursor.getColumnIndex("kanji"));
      String kanaCol = cursor.getString(cursor.getColumnIndex("kana"));
      String senseCol = cursor.getString(cursor.getColumnIndex("gloss"));

      String[] kanji = kanjiCol.split(";");
      String[] kana = kanaCol.split(";");
      String[] sense = senseCol.split("\\|\\|");

      if (kanji.length != 0 && !kanji[0].isEmpty()) {
        jpCard.setJapaneseText(kanji[0]);
        jpCard.setKanaText(kana[0]);
      } else {
        jpCard.setJapaneseText(kana[0]);
      }

      String[] senseArr = sense[0].split(";;");
      StringBuilder sbSense = new StringBuilder();
      sbSense.append(senseArr[0]);
      for (int i = 1; i < senseArr.length; i++) {
        sbSense.append("; ");
        sbSense.append(senseArr[i]);
      }
      jpCard.setEnglishText(sbSense.toString());

      japaneseCards.add(jpCard);
    }

    return japaneseCards;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("JapaneseCard{");
    sb.append("japaneseText='").append(japaneseText).append('\'');
    sb.append(", kanaText='").append(kanaText).append('\'');
    sb.append(", englishText='").append(englishText).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
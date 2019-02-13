package io.royaloaklabs.supergenki.domain;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class JapaneseCard {
    public String japaneseText;
    public String englishMeaning;
    public String photoId;

    public JapaneseCard() { } // empty constructor

    public JapaneseCard(String japaneseText, String englishMeaning, String photoId) {
        this.japaneseText = japaneseText;
        this.englishMeaning = englishMeaning;
        this.photoId = photoId;
    }

    public void setJapaneseText(String japaneseText) {
        this.japaneseText = japaneseText;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }


    public static List<JapaneseCard> buildListFromCursor(Cursor cursor) {
      List<JapaneseCard> mCards = new ArrayList<JapaneseCard>();

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
        jpCard.setEnglishMeaning(sbSense.toString());

        mCards.add(jpCard);
      }

      return mCards;
    }


    @Override
    public String toString() {
        return String.format("JapaneseCard = [japaneseText: %s, englishMeaning: %s, photoId: %s]", this.japaneseText, this.englishMeaning, this.photoId);
    }
}
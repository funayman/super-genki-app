package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.royaloaklabs.supergenki.domain.JapaneseCard;

public class DictionaryAdapter {
    protected DictionaryHelper mDictHelper;

    public DictionaryAdapter(Context context) {
        this.mDictHelper = new DictionaryHelper(context);
        mDictHelper.connect();
    }


    public List<JapaneseCard> getRandomData() {
        SQLiteDatabase db = mDictHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT kanji, kana, gloss FROM einihongo ORDER BY RANDOM() LIMIT 20", null);

        List<JapaneseCard> mCards = new ArrayList<JapaneseCard>();

        for(;cursor.moveToNext();) {
            JapaneseCard jpCard = new JapaneseCard();

            String kanjiCol = cursor.getString(cursor.getColumnIndex("kanji"));
            String kanaCol = cursor.getString(cursor.getColumnIndex("kana"));
            String senseCol = cursor.getString(cursor.getColumnIndex("gloss"));

            String[] kanji = kanjiCol.split(";");
            String[] kana = kanaCol.split(";");
            String[] sense = senseCol.split("\\|\\|");

            if(kanji.length != 0 && !kanji[0].isEmpty()) {
                jpCard.setJapaneseText(kanji[0]);
            } else {
                jpCard.setJapaneseText(kana[0]);
            }

            String[] senseArr = sense[0].split(";;");
            StringBuilder sbSense = new StringBuilder();
            sbSense.append(senseArr[0]);
            for(int i=1; i<senseArr.length; i++) {
                sbSense.append("; ");
                sbSense.append(senseArr[i]);
            }
            jpCard.setEnglishMeaning(sbSense.toString());

            mCards.add(jpCard);
        }

        return mCards;
    }
}

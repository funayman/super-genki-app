package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master ORDER BY RANDOM() LIMIT 20", null);

        List<JapaneseCard> mCards = new ArrayList<JapaneseCard>();

        for(;cursor.moveToNext();) {
            mCards.add(new JapaneseCard(cursor.getString(cursor.getColumnIndex("name")), "", ""));
        }

        /*
        for(;cursor.moveToNext();) {
            JapaneseCard jpCard = new JapaneseCard();

            String[] kanjiCol = cursor.getString(0).split(";");
            String[] kanaCol = cursor.getString(1).split(";");
            String[] senseCol = cursor.getString(2).split("||");

            if(kanjiCol.length != 0) {
                jpCard.setJapaneseText(kanjiCol[0]);
            } else {
                jpCard.setJapaneseText(kanaCol[0]);
            }

            String[] senseArr = senseCol[0].split(";;");
            StringBuilder sense = new StringBuilder();
            sense.append(senseArr[0]);
            for(int i=1; i<senseArr.length; i++) {
                sense.append("; ");
                sense.append(senseArr[i]);
            }
            jpCard.setEnglishMeaning(sense.toString());
        }
        */

        return mCards;
    }
}

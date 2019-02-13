package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import io.royaloaklabs.supergenki.domain.JapaneseCard;

public class DictionaryAdapter {
  private static final String RANDOM_DATA_SQL = "SELECT kanji, kana, gloss FROM einihongo ORDER BY RANDOM() LIMIT 20";
  private static final String QUERY_SQL = "SELECT kanji, kana, gloss FROM einihongo WHERE einihongo MATCH ?";

  protected DictionaryHelper mDictHelper;

  public DictionaryAdapter(Context context) {
    this.mDictHelper = new DictionaryHelper(context);
  }

  public List<JapaneseCard> Search(String q) {
    SQLiteDatabase db = mDictHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(QUERY_SQL, new String[]{q+"*"});
    return JapaneseCard.buildListFromCursor(cursor);
  }


  public List<JapaneseCard> getRandomData() {
    SQLiteDatabase db = mDictHelper.getReadableDatabase();

    Cursor cursor = db.rawQuery(RANDOM_DATA_SQL, null);

    return JapaneseCard.buildListFromCursor(cursor);
  }


}

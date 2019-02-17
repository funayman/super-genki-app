package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import io.royaloaklabs.supergenki.domain.Entry;
import io.royaloaklabs.supergenki.domain.Sense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryAdapter {
  private static final String RANDOM_DATA_SQL =
      String.format("SELECT %s, %s, %s, %s, %s FROM %s ORDER BY RANDOM() LIMIT 20",
          Entry.ID_COL_NAME, Entry.KANJI_COL_NAME, Entry.KANA_COL_NAME, Entry.ENGLISH_COL_NAME, Entry.ROMAJI_COL_NAME, Entry.ENTRY_TABLE_NAME);

  private static final String QUERY_SQL = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s MATCH ?",
      Entry.ID_COL_NAME, Entry.KANJI_COL_NAME, Entry.KANA_COL_NAME, Entry.ENGLISH_COL_NAME, Entry.ROMAJI_COL_NAME,
      Entry.ENTRY_TABLE_NAME, Entry.ENTRY_TABLE_NAME);

  protected DictionaryHelper mDictHelper;

  public DictionaryAdapter(Context context) {
    this.mDictHelper = new DictionaryHelper(context);
  }

  public List<Entry> Search(String q) {
    SQLiteDatabase db = mDictHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(QUERY_SQL, new String[]{q + "*"});
    return this.buildEntryListFromCursor(cursor);
  }


  public List<Entry> getRandomData() {
    SQLiteDatabase db = mDictHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(RANDOM_DATA_SQL, null);
    return this.buildEntryListFromCursor(cursor);
  }


  private List<Entry> buildEntryListFromCursor(Cursor cursor) {
    List<Entry> mEntries = new ArrayList<>(cursor.getCount());
    for(; cursor.moveToNext(); ) {
      // TODO fix this ugliness
      long id = cursor.getLong(cursor.getColumnIndex(Entry.ID_COL_NAME));
      List<String> kanji = new ArrayList<>(
          Arrays.asList(cursor.getString(cursor.getColumnIndex(Entry.KANJI_COL_NAME)).split(Entry.DELIMITER)));
      List<String> kana = new ArrayList<>(
          Arrays.asList(cursor.getString(cursor.getColumnIndex(Entry.KANA_COL_NAME)).split(Entry.DELIMITER)));
      List<Sense> senses = Sense.buildFromRawData(cursor.getString(cursor.getColumnIndex(Entry.ENGLISH_COL_NAME)));
      List<String> romaji = new ArrayList<>(
          Arrays.asList(cursor.getString(cursor.getColumnIndex(Entry.ROMAJI_COL_NAME)).split(Entry.DELIMITER)));

      mEntries.add(new Entry.Builder()
          .setId(id)
          .setKanji(kanji.remove(0))
          .setKanjiAlt(kanji)
          .setKana(kana.remove(0))
          .setKanaAlt(kana)
          .setSenses(senses)
          .setRomaji(romaji.remove(0))
          .build()
      );
    }
    return mEntries;
  }

}

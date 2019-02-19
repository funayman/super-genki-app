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
          Entry.ID_COL_NAME, Entry.KANJI_COL_NAME, Entry.KANA_COL_NAME,
          Entry.ENGLISH_COL_NAME, Entry.ROMAJI_COL_NAME, Entry.ENTRY_TABLE_NAME);

  private static final String QUERY_SQL = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s MATCH ?",
      Entry.ID_COL_NAME, Entry.KANJI_COL_NAME, Entry.KANA_COL_NAME, Entry.ENGLISH_COL_NAME, Entry.ROMAJI_COL_NAME,
      Entry.ENTRY_TABLE_NAME, Entry.ENTRY_TABLE_NAME);

  private static final String GET_BY_ID_SQL = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s=?",
      Entry.ID_COL_NAME, Entry.KANJI_COL_NAME, Entry.KANA_COL_NAME, Entry.ENGLISH_COL_NAME,
      Entry.ROMAJI_COL_NAME, Entry.ENTRY_TABLE_NAME, Entry.ID_COL_NAME);

  protected DictionaryHelper dictionaryHelper;

  public DictionaryAdapter(Context context) {
    this.dictionaryHelper = new DictionaryHelper(context);
  }

  public List<Entry> Search(String q) {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(QUERY_SQL, new String[]{q + "*"});
    List<Entry> entryList = this.buildEntryListFromCursor(cursor);
    return entryList;
  }


  public List<Entry> getRandomData() {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(RANDOM_DATA_SQL, null);
    List<Entry> entryList = this.buildEntryListFromCursor(cursor);
    return entryList;
  }

  public Entry getOne(Long id) {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(GET_BY_ID_SQL, new String[]{id.toString()});
    List<Entry> entryList = this.buildEntryListFromCursor(cursor);
    db.close();
    return entryList.remove(0);
  }

  private List<Entry> buildEntryListFromCursor(Cursor cursor) {
    List<Entry> mEntries = new ArrayList<>();
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

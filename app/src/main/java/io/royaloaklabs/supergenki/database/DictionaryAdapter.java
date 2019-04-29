package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import io.royaloaklabs.supergenki.domain.DictionaryEntry;
import io.royaloaklabs.supergenki.domain.SearchResult;
import io.royaloaklabs.supergenki.domain.Sense;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAdapter {
  private static final String GET_ONE_RECORD_BY_ID_SQL = String.format(
      "SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s = ?",
      DictionaryEntry.ID_COL_NAME,
      DictionaryEntry.JAPANESE_COL_NAME,
      DictionaryEntry.FURIGANA_COL_NAME,
      DictionaryEntry.ENGLISH_COL_NAME,
      DictionaryEntry.ROMAJI_COL_NAME,
      DictionaryEntry.FREQUENCY_COL_NAME,
      DictionaryEntry.ENTRY_TABLE_NAME,
      DictionaryEntry.ID_COL_NAME
  );

  private static final String RANDOM_DATA_SQL = String.format(
      "SELECT %s, %s, %s, %s, %s, %s FROM %s ORDER BY RANDOM() LIMIT 20",
      DictionaryEntry.ID_COL_NAME,
      DictionaryEntry.JAPANESE_COL_NAME,
      DictionaryEntry.FURIGANA_COL_NAME,
      DictionaryEntry.ENGLISH_COL_NAME,
      DictionaryEntry.ROMAJI_COL_NAME,
      DictionaryEntry.FREQUENCY_COL_NAME,
      DictionaryEntry.ENTRY_TABLE_NAME
  );

  private static final String GET_BY_ID_SQL = String.format(
      "SELECT %s,%s,%s,%s,%s,%s, %s FROM %s AS d INNER JOIN %s AS r ON d.id = r.id WHERE d.id = ?",
      DictionaryEntry.PART_OF_SPEECH_COL_NAME,
      DictionaryEntry.GLOSS_COL_NAME,
      DictionaryEntry.JAPANESE_COL_NAME,
      DictionaryEntry.FURIGANA_COL_NAME,
      DictionaryEntry.ALTKANJI_COL_NAME,
      DictionaryEntry.ALTKANA_COL_NAME,
      DictionaryEntry.ROMAJI_COL_NAME,
      DictionaryEntry.DEFINITION_TABLE_NAME,
      DictionaryEntry.READING_TABLE_NAME
  );


  private static final String QUERY_SQL = String.format(
      "SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s MATCH ? ORDER BY %s DESC",
      DictionaryEntry.ID_COL_NAME,
      DictionaryEntry.JAPANESE_COL_NAME,
      DictionaryEntry.FURIGANA_COL_NAME,
      DictionaryEntry.ENGLISH_COL_NAME,
      DictionaryEntry.ROMAJI_COL_NAME,
      DictionaryEntry.FREQUENCY_COL_NAME,
      DictionaryEntry.ENTRY_TABLE_NAME,
      DictionaryEntry.ENTRY_TABLE_NAME,
      DictionaryEntry.FREQUENCY_COL_NAME
  );

  protected DictionaryHelper dictionaryHelper;

  public DictionaryAdapter(Context context) {
    this.dictionaryHelper = new DictionaryHelper(context);
  }

  public Long getEntryTableCount() {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    long count = DatabaseUtils.queryNumEntries(db, DictionaryEntry.ENTRY_TABLE_NAME);
    return count;
  }

  public List<SearchResult> Search(String q) {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(QUERY_SQL, new String[]{q + "*"});
    List<SearchResult> entryList = this.buildListFromCursor(cursor);
    return entryList;
  }

  public SearchResult getOneSearchResultById(Long id) {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(GET_ONE_RECORD_BY_ID_SQL, new String[]{id.toString()});
    List<SearchResult> entryList = this.buildListFromCursor(cursor);
    return entryList.get(0);
  }

  public List<SearchResult> getRandomData() {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(RANDOM_DATA_SQL, null);
    List<SearchResult> entryList = this.buildListFromCursor(cursor);
    return entryList;
  }

  public DictionaryEntry getOne(Long id) {
    DictionaryEntry entry = new DictionaryEntry(id);

    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(GET_BY_ID_SQL, new String[]{id.toString()});
    for(;cursor.moveToNext();) {
      entry.setJapanese(cursor.getString(cursor.getColumnIndex(DictionaryEntry.JAPANESE_COL_NAME)));
      entry.setFurigana(cursor.getString(cursor.getColumnIndex(DictionaryEntry.FURIGANA_COL_NAME)));
      entry.setAltKanji(cursor.getString(cursor.getColumnIndex(DictionaryEntry.ALTKANJI_COL_NAME)));
      entry.setAltKana(cursor.getString(cursor.getColumnIndex(DictionaryEntry.ALTKANA_COL_NAME)));
      entry.setRomaji(cursor.getString(cursor.getColumnIndex(DictionaryEntry.ROMAJI_COL_NAME)));

      String senseData = cursor.getString(cursor.getColumnIndex(DictionaryEntry.GLOSS_COL_NAME));
      entry.getSenses().add(new Sense(senseData));
    }

    return entry;
  }

  private List<SearchResult> buildListFromCursor(Cursor cursor) {
    List<SearchResult> results = new ArrayList<>();
    for(; cursor.moveToNext(); ) {
      long id = cursor.getLong(cursor.getColumnIndex(DictionaryEntry.ID_COL_NAME));
      String japanese = cursor.getString(cursor.getColumnIndex(DictionaryEntry.JAPANESE_COL_NAME));
      String furigana = cursor.getString(cursor.getColumnIndex(DictionaryEntry.FURIGANA_COL_NAME));
      String english = cursor.getString(cursor.getColumnIndex(DictionaryEntry.ENGLISH_COL_NAME));
      String romaji = cursor.getString(cursor.getColumnIndex(DictionaryEntry.ROMAJI_COL_NAME));

      results.add( new SearchResult(id, japanese, furigana, english, romaji) );
    }

    return results;
  }

}

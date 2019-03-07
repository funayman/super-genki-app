package io.royaloaklabs.supergenki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import io.royaloaklabs.supergenki.domain.Entry;
import io.royaloaklabs.supergenki.domain.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAdapter {
  private static final String RANDOM_DATA_SQL = String.format(
      "SELECT %s, %s, %s, %s, %s, %s FROM %s ORDER BY RANDOM() LIMIT 20",
      Entry.ID_COL_NAME,
      Entry.JAPANESE_COL_NAME,
      Entry.FURIGANA_COL_NAME,
      Entry.ENGLISH_COL_NAME,
      Entry.ROMAJI_COL_NAME,
      Entry.FREQUENCY_COL_NAME,
      Entry.ENTRY_TABLE_NAME
  );

  private static final String GET_BY_ID_SQL = String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s=?",
      Entry.ID_COL_NAME, Entry.JAPANESE_COL_NAME, Entry.FURIGANA_COL_NAME, Entry.ENGLISH_COL_NAME,
      Entry.ROMAJI_COL_NAME, Entry.ENTRY_TABLE_NAME, Entry.ID_COL_NAME);

  private static final String QUERY_SQL = String.format(
      "SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s MATCH ? ORDER BY %s DESC",
      Entry.ID_COL_NAME,
      Entry.JAPANESE_COL_NAME,
      Entry.FURIGANA_COL_NAME,
      Entry.ENGLISH_COL_NAME,
      Entry.ROMAJI_COL_NAME,
      Entry.FREQUENCY_COL_NAME,
      Entry.ENTRY_TABLE_NAME,
      Entry.ENTRY_TABLE_NAME,
      Entry.FREQUENCY_COL_NAME
  );

  protected DictionaryHelper dictionaryHelper;

  public DictionaryAdapter(Context context) {
    this.dictionaryHelper = new DictionaryHelper(context);
  }

  public List<SearchResult> Search(String q) {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(QUERY_SQL, new String[]{q + "*"});
    List<SearchResult> entryList = this.buildListFromCursor(cursor);
    return entryList;
  }


  public List<SearchResult> getRandomData() {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(RANDOM_DATA_SQL, null);
    List<SearchResult> entryList = this.buildListFromCursor(cursor);
    return entryList;
  }

  public Entry getOne(Long id) {
    SQLiteDatabase db = dictionaryHelper.getReadableDatabase();
    // Cursor cursor = db.rawQuery(GET_BY_ID_SQL, new String[]{id.toString()});
    return new Entry.Builder().setId(1518450).build();
  }

  private List<SearchResult> buildListFromCursor(Cursor cursor) {
    List<SearchResult> results = new ArrayList<>();
    for(; cursor.moveToNext(); ) {
      long id = cursor.getLong(cursor.getColumnIndex(Entry.ID_COL_NAME));
      String japanese = cursor.getString(cursor.getColumnIndex(Entry.JAPANESE_COL_NAME));
      String furigana = cursor.getString(cursor.getColumnIndex(Entry.FURIGANA_COL_NAME));
      String english = cursor.getString(cursor.getColumnIndex(Entry.ENGLISH_COL_NAME));
      String romaji = cursor.getString(cursor.getColumnIndex(Entry.ROMAJI_COL_NAME));

      results.add( new SearchResult(id, japanese, furigana, english, romaji) );
    }

    return results;
  }

}

package io.royaloaklabs.supergenki.database.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.adapter.DictionaryJapaneseAdapter;
import io.royaloaklabs.supergenki.adapter.RecyclerViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;

public class QueryDatabaseTask extends AsyncTask<String, Integer, DictionaryJapaneseAdapter> {
  private DictionaryAdapter dictionaryAdapter;

  public QueryDatabaseTask(DictionaryAdapter dictionaryAdapter) {
    this.dictionaryAdapter = dictionaryAdapter;
  }

  @Override
  public DictionaryJapaneseAdapter doInBackground(String... q) {
    return new DictionaryJapaneseAdapter(dictionaryAdapter.Search(q[0]));
  }
}

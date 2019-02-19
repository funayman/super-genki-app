package io.royaloaklabs.supergenki.database.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.adapter.DictionaryViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;

public class QueryDatabaseTask extends AsyncTask<String, Integer, DictionaryViewAdapter> {
  private DictionaryAdapter dictionaryAdapter;

  public QueryDatabaseTask(DictionaryAdapter dictionaryAdapter) {
    this.dictionaryAdapter = dictionaryAdapter;
  }

  @Override
  public DictionaryViewAdapter doInBackground(String... q) {
    return new DictionaryViewAdapter(dictionaryAdapter.Search(q[0]));
  }
}

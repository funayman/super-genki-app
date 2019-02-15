package io.royaloaklabs.supergenki.database.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.adapter.RecyclerViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;

public class QueryDatabaseTask extends AsyncTask<String, Integer, RecyclerViewAdapter> {
  private DictionaryAdapter dictionaryAdapter;

  public QueryDatabaseTask(DictionaryAdapter dictionaryAdapter) {
    this.dictionaryAdapter = dictionaryAdapter;
  }

  @Override
  public RecyclerViewAdapter doInBackground(String... q) {
    return new RecyclerViewAdapter(dictionaryAdapter.Search(q[0]));
  }
}

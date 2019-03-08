package io.royaloaklabs.supergenki.database.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.adapter.DictionaryViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.SearchResult;

import java.util.List;

public class DatabaseQueryTask extends AsyncTask<String, Void, List<SearchResult>> {
  private DictionaryAdapter dictionaryAdapter;
  private DictionaryViewAdapter dictionaryViewAdapter;

  public DatabaseQueryTask(DictionaryAdapter dictionaryAdapter, DictionaryViewAdapter dictionaryViewAdapter) {
    this.dictionaryAdapter = dictionaryAdapter;
    this.dictionaryViewAdapter = dictionaryViewAdapter;
  }

  @Override
  protected List<SearchResult> doInBackground(String... strings) {
    return this.dictionaryAdapter.Search(strings[0]);
  }

  @Override
  protected void onPostExecute(List<SearchResult> entries) {
    super.onPostExecute(entries);
    dictionaryViewAdapter.updateEntries(entries);
  }
}

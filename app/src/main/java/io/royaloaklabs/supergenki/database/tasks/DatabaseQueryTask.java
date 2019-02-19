package io.royaloaklabs.supergenki.database.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.adapter.RecyclerViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.Entry;

import java.util.List;

public class DatabaseQueryTask extends AsyncTask<String, Void, List<Entry>> {
  private RecyclerViewAdapter recyclerViewAdapter;

  public DatabaseQueryTask(RecyclerViewAdapter recyclerViewAdapter, DictionaryAdapter dictionaryAdapter) {
    this.recyclerViewAdapter = recyclerViewAdapter;
    this.dictionaryAdapter = dictionaryAdapter;
  }

  private DictionaryAdapter dictionaryAdapter;

  @Override
  protected List<Entry> doInBackground(String... strings) {
    return this.dictionaryAdapter.Search(strings[0]);
  }

  @Override
  protected void onPostExecute(List<Entry> entries) {
    super.onPostExecute(entries);
    recyclerViewAdapter.update(entries);
  }
}

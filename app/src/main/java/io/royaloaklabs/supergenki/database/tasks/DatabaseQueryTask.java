package io.royaloaklabs.supergenki.database.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.Updater;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.Entry;

import java.util.List;

public class DatabaseQueryTask extends AsyncTask<String, Void, List<Entry>> {
  private Updater updater;

  public DatabaseQueryTask(Updater updater, DictionaryAdapter dictionaryAdapter) {
    this.updater = updater;
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
    this.updater.updateRecyclerListView(entries);
  }
}

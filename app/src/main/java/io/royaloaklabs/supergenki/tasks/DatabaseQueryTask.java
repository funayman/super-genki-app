package io.royaloaklabs.supergenki.tasks;

import android.os.AsyncTask;
import io.royaloaklabs.supergenki.adapter.DictionaryViewAdapter;
import io.royaloaklabs.supergenki.database.DictionaryAdapter;
import io.royaloaklabs.supergenki.domain.SearchResult;

import java.util.List;

public class DatabaseQueryTask extends AsyncTask<String, Void, List<SearchResult>> {
  private DictionaryAdapter dictionaryAdapter;
  private UiUpdater updater;

  public void setUpdater(UiUpdater updater) {
    this.updater = updater;
  }

  public void setDictionaryAdapter(DictionaryAdapter dictionaryAdapter) {
    this.dictionaryAdapter = dictionaryAdapter;
  }

  public DatabaseQueryTask() { }

  public DatabaseQueryTask(DictionaryAdapter dictionaryAdapter) {
    this.dictionaryAdapter = dictionaryAdapter;
  }

  @Override
  protected List<SearchResult> doInBackground(String... strings) {
    return this.dictionaryAdapter.Search(strings[0]);
  }

  @Override
  protected void onPostExecute(List<SearchResult> entries) {
    super.onPostExecute(entries);
    updater.onTaskSuccess(entries);
  }

  public interface UiUpdater {
    void onTaskSuccess(List<SearchResult> searchResults);
  }

}

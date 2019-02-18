package io.royaloaklabs.supergenki;

import io.royaloaklabs.supergenki.domain.Entry;

import java.util.List;

public interface Updater {
  void updateRecyclerListView(List<Entry> entries);
}

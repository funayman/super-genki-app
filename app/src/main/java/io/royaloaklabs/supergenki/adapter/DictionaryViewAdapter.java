package io.royaloaklabs.supergenki.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.activities.DetailedJapaneseActivity;
import io.royaloaklabs.supergenki.domain.Entry;

import java.util.List;

public class DictionaryViewAdapter extends RecyclerView.Adapter<DictionaryViewAdapter.ViewHolder> {

  private List<Entry> entries;

  public DictionaryViewAdapter(List<Entry> entries) {
    this.entries = entries;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.japanese_cardview, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Entry entry = this.entries.get(position);

    if(entry.getKanji().isEmpty()) {
      holder.kanjiText.setText(entry.getKana());
      holder.kanaText.setText(R.string.EMPTY);
    } else {
      holder.kanjiText.setText(entry.getKanji());
      holder.kanaText.setText(entry.getKana());
    }
    holder.englishText.setText(entry.getSensesAsString());
  }

  @Override
  public int getItemCount() {
    return entries.size();
  }

  public void updateEntries(List<Entry> mEntryList) {
    this.entries = mEntryList;
    this.notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView kanjiText;
    TextView kanaText;
    TextView englishText;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      kanjiText = itemView.findViewById(R.id.kanjiText);
      kanaText = itemView.findViewById(R.id.kanaText);
      englishText = itemView.findViewById(R.id.englishText);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Entry entry = entries.get(getAdapterPosition());
          Intent intent = new Intent(v.getContext(), DetailedJapaneseActivity.class);
          intent.putExtra(DetailedJapaneseActivity.ENT_SEQ, entry.getId());
          v.getContext().startActivity(intent);
        }
      });
    }
  }
}

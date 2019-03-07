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
import io.royaloaklabs.supergenki.domain.SearchResult;

import java.util.List;

public class DictionaryViewAdapter extends RecyclerView.Adapter<DictionaryViewAdapter.ViewHolder> {

  private List<SearchResult> entries;

  public DictionaryViewAdapter(List<SearchResult> entries) {
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
    SearchResult entry = this.entries.get(position);

    holder.kanjiText.setText(entry.getJapanese());
    holder.kanaText.setText(entry.getFurigana());
    holder.englishText.setText(entry.getEnglish());
  }

  @Override
  public int getItemCount() {
    return entries.size();
  }

  public void updateEntries(List<SearchResult> results) {
    this.entries = results;
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
          SearchResult entry = entries.get(getAdapterPosition());
          Intent intent = new Intent(v.getContext(), DetailedJapaneseActivity.class);
          intent.putExtra(DetailedJapaneseActivity.ENT_SEQ, entry.getId());
          v.getContext().startActivity(intent);
        }
      });
    }
  }
}

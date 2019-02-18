package io.royaloaklabs.supergenki.adapter;

import android.content.Intent;
import android.util.Log;
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

public class DictionaryJapaneseAdapter extends RecyclerView.Adapter<DictionaryJapaneseAdapter.ViewHolder> {

  private List<Entry> mEntryList;

  public DictionaryJapaneseAdapter(List<Entry> mEntryList) {
    this.mEntryList = mEntryList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new DictionaryJapaneseAdapter.ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.japanese_cardview, parent, Boolean.FALSE));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Entry entry = this.mEntryList.get(position);

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
    return mEntryList.size();
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
          Log.v("DRT", "click enabled");
          Entry entry = mEntryList.get(getAdapterPosition());
          Intent intent = new Intent(v.getContext(), DetailedJapaneseActivity.class);
          intent.putExtra(DetailedJapaneseActivity.ENT_SEQ, entry.getId());
          v.getContext().startActivity(intent);
        }
      });
    }
  }
}

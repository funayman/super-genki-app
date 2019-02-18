package io.royaloaklabs.supergenki.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.MainActivity;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.activities.DetailedJapaneseActivity;
import io.royaloaklabs.supergenki.domain.Entry;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.JapaneseCardViewHolder> {
  List<Entry> japaneseCards;

  public RecyclerViewAdapter(List<Entry> japaneseCards) {
    this.japaneseCards = japaneseCards;
  }

  @NonNull
  @Override
  public JapaneseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.japanese_cardview, parent, false);
    JapaneseCardViewHolder japaneseCardViewHolder = new JapaneseCardViewHolder(v);
    return japaneseCardViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull JapaneseCardViewHolder holder, int position) {
    if(japaneseCards.get(position).getKanji().isEmpty()) {
      holder.kanjiText.setText(japaneseCards.get(position).getKana());
      holder.kanaText.setText(R.string.EMPTY);
    } else {
      holder.kanjiText.setText(japaneseCards.get(position).getKanji());
      holder.kanaText.setText(japaneseCards.get(position).getKana());
    }
    holder.englishText.setText(japaneseCards.get(position).getSensesAsString());

    holder.serializedForm = holder.kanjiText.getText() + "||"
        + japaneseCards.get(position).getSensesAsString()
        + "||" + japaneseCards.get(position).getKana() + "||" + japaneseCards.get(position).getRomaji();
  }

  @NonNull
  @Override
  public int getItemCount() {
    return japaneseCards.size();
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
  }

  public static class JapaneseCardViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView kanjiText;
    TextView kanaText;
    TextView englishText;
    String serializedForm;

    public JapaneseCardViewHolder(View itemView) {
      super(itemView);
      cardView = (CardView) itemView.findViewById(R.id.cardView);
      kanjiText = (TextView) itemView.findViewById(R.id.kanjiText);
      kanaText = (TextView) itemView.findViewById(R.id.kanaText);
      englishText = (TextView) itemView.findViewById(R.id.englishText);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(view.getContext(), DetailedJapaneseActivity.class);
          view.getContext().startActivity(intent);
        }
      });
    }
  }

}


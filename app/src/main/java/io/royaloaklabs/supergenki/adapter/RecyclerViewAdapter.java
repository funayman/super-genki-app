package io.royaloaklabs.supergenki.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.domain.JapaneseCard;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.JapaneseCardViewHolder> {
    List<JapaneseCard> japaneseCards;

    public RecyclerViewAdapter(List<JapaneseCard> japaneseCards){
        this.japaneseCards = japaneseCards;
    }

    @NonNull
    @Override
    public JapaneseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.japanese_cardview, parent, false);
        JapaneseCardViewHolder jcvh = new JapaneseCardViewHolder(v);
        return jcvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JapaneseCardViewHolder holder, int position) {
        holder.kanjiText.setText(japaneseCards.get(position).japaneseText);
        holder.kanaText.setText(japaneseCards.get(position).kanaText);
        holder.englishText.setText(japaneseCards.get(position).englishText);
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
        CardView cv;
        TextView kanjiText;
        TextView kanaText;
        TextView englishText;

        JapaneseCardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            kanjiText = (TextView)itemView.findViewById(R.id.japaneseText);
            kanaText = (TextView)itemView.findViewById(R.id.pronounceView);
            englishText = (TextView)itemView.findViewById(R.id.translationView);
        }
    }

}


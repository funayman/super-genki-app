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
        JapaneseCardViewHolder pvh = new JapaneseCardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull JapaneseCardViewHolder holder, int position) {
        holder.personName.setText(japaneseCards.get(position).japaneseText);
        holder.personAge.setText(japaneseCards.get(position).englishMeaning);
        holder.personPhoto.setText(japaneseCards.get(position).photoId);
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
            TextView personName;
            TextView personAge;
            TextView personPhoto;

            JapaneseCardViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cardView);
                personName = (TextView)itemView.findViewById(R.id.japaneseText);
                personAge = (TextView)itemView.findViewById(R.id.translationView);
                personPhoto = (TextView)itemView.findViewById(R.id.pronounceView);
            }
    }

}


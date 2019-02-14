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
import io.royaloaklabs.supergenki.domain.JapaneseCard;

import java.util.List;


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
        holder.serializedForm = japaneseCards.get(position).japaneseText + "||"
            + japaneseCards.get(position).englishText
            + "||" + japaneseCards.get(position).kanaText;
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
            cardView    = (CardView)itemView.findViewById(R.id.cardView);
            kanjiText   = (TextView)itemView.findViewById(R.id.japaneseText);
            kanaText    = (TextView)itemView.findViewById(R.id.pronounceView);
            englishText = (TextView)itemView.findViewById(R.id.translationView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked");
                    Intent intent = new Intent(view.getContext(), DetailedJapaneseActivity.class);
                    intent.putExtra(MainActivity.MESSAGE, serializedForm);
                    view.getContext().startActivity(intent);

                }
            });
        }
    }

}


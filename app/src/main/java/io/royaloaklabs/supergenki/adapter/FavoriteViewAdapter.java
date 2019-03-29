package io.royaloaklabs.supergenki.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.activities.DetailedJapaneseActivity;
import io.royaloaklabs.supergenki.domain.Favorite;
import sh.drt.supergenkiutil.furiganaview.FuriganaView;

import java.util.Collections;
import java.util.List;

public class FavoriteViewAdapter extends RecyclerView.Adapter<FavoriteViewAdapter.FavoriteViewHolder> {
  class FavoriteViewHolder extends RecyclerView.ViewHolder {
    private final TextView favEnglishTextView;
    private final FuriganaView favFuriganaView;

    public FavoriteViewHolder(@NonNull View itemView) {
      super(itemView);
      favEnglishTextView = (TextView) itemView.findViewById(R.id.favEnglishTextView);
      favFuriganaView = (FuriganaView) itemView.findViewById(R.id.favJapaneseTextView);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Favorite favorite = favoriteList.get(getAdapterPosition());

          Intent intent = new Intent(v.getContext(), DetailedJapaneseActivity.class);
          intent.putExtra(DetailedJapaneseActivity.ENT_SEQ, favorite.getEntryId());
          v.getContext().startActivity(intent);
        }
      });
    }
  }

  private final LayoutInflater inflater;
  private List<Favorite> favoriteList;

  public FavoriteViewAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
    this.favoriteList = Collections.emptyList();
  }

  public FavoriteViewAdapter(Context context, List<Favorite> favorites) {
    this.inflater = LayoutInflater.from(context);
    this.favoriteList = favorites;
  }

  public void setFavoriteList(List<Favorite> favoriteList) {
    this.favoriteList = favoriteList;
    notifyDataSetChanged();
  }

  public Favorite getFavoriteFromList(int position) {
    return this.favoriteList.get(position);
  }

  public void removeFavoriteFromList(int position) {
    this.favoriteList.remove(position);
    notifyItemRemoved(position);
  }

  public void restoreItem(int position, Favorite favorite) {
    this.favoriteList.add(position, favorite);
    notifyItemInserted(position);
  }

  @NonNull
  @Override
  public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.favorite_recyclerview_item, parent, false);
    return new FavoriteViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
    Favorite f = favoriteList.get(position);
    holder.favEnglishTextView.setText(f.getEnglish());

    String japaneseText = (f.getFurigana().isEmpty()) ? f.getJapanese() : String.format("{%s;%s}", f.getJapanese(), f.getFurigana());
    holder.favFuriganaView.setText(japaneseText);
  }

  @Override
  public int getItemCount() {
    return favoriteList.size();
  }

}

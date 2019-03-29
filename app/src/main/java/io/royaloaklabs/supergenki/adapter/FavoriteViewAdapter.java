package io.royaloaklabs.supergenki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
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

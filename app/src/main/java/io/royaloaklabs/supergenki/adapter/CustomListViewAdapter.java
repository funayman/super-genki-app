package io.royaloaklabs.supergenki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.domain.CustomList;

import java.util.Collections;
import java.util.List;

public class CustomListViewAdapter extends RecyclerView.Adapter<CustomListViewAdapter.CustomListViewHolder> {
  class CustomListViewHolder extends RecyclerView.ViewHolder {
    private final TextView listName;
    private final TextView listSize;

    public CustomListViewHolder(@NonNull View itemView) {
      super(itemView);
      listName = (TextView) itemView.findViewById(R.id.listName);
      listSize = (TextView) itemView.findViewById(R.id.listSize);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          /** switch to custom list view
          Favorite favorite = favoriteList.get(getAdapterPosition());

          Intent intent = new Intent(v.getContext(), DetailedJapaneseActivity.class);
          intent.putExtra(DetailedJapaneseActivity.ENT_SEQ, favorite.getEntryId());
          v.getContext().startActivity(intent);
           **/
        }
      });
    }
  }

  private final LayoutInflater inflater;
  private List<CustomList> customList;

  public CustomListViewAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
      this.customList = Collections.emptyList();
  }

  public void setFavoriteList(List<CustomList> favoriteList) {
    this.customList = favoriteList;
    notifyDataSetChanged();
  }

  public CustomList getFavoriteFromList(int position) {
    return this.customList.get(position);
  }

  public void removeCustomList(int position) {
    this.customList.remove(position);
    notifyItemRemoved(position);
  }

  public void restoreItem(int position, CustomList favorite) {
    this.customList.add(position, favorite);
    notifyItemInserted(position);
  }

  @NonNull
  @Override
  public CustomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.customlist_recyclerview_item, parent, false);
    return new CustomListViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull CustomListViewHolder holder, int position) {
    CustomList customListValue = customList.get(position);
    holder.listName.setText(customListValue.getListName());
    holder.listSize.setText("List Size: 0");
  }

  @Override
  public int getItemCount() {
    return customList.size();
  }

}

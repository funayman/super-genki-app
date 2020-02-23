package io.royaloaklabs.supergenki.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import io.royaloaklabs.supergenki.R;
import io.royaloaklabs.supergenki.adapter.CustomListViewAdapter;
import io.royaloaklabs.supergenki.domain.CustomList;
import io.royaloaklabs.supergenki.view.CustomListViewModel;
import io.royaloaklabs.supergenki.view.SwipeToDeleteCallback;

import java.util.List;

public class CustomListViewActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private final Context context = this;
  private FloatingActionButton floatingActionButton;
  private CustomListViewAdapter customListViewAdapter;
  private CustomListViewModel customListViewModel;
  private SwipeToDeleteCallback callback;
  private boolean isFavorite;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    setContentView(R.layout.activity_customlist_view);

    recyclerView = findViewById(R.id.customListRecyclerView);
    customListViewAdapter = new CustomListViewAdapter(this);
    recyclerView.setAdapter(customListViewAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    customListViewModel = ViewModelProviders.of(this).get(CustomListViewModel.class);
    customListViewModel.getAll().observe(this, new Observer<List<CustomList>>() {
      @Override
      public void onChanged(List<CustomList> favorites) {
        customListViewAdapter.setFavoriteList(favorites);
      }
    });

    callback = makeCallback();

    new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
    floatingActionButton = findViewById(R.id.addList);
    floatingActionButton.setOnClickListener(newListButtonOnClickListener);

  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

  private View.OnClickListener newListButtonOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      LayoutInflater li = LayoutInflater.from(context);
      View promptsView = li.inflate(R.layout.prompt_dialog, null);

      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
          context,  R.style.AlertDialogStyle);
      alertDialogBuilder.setView(promptsView);
      final EditText userInput = (EditText) promptsView
          .findViewById(R.id.editTextDialogUserInput);
      // set dialog message
      alertDialogBuilder
          .setCancelable(false)
          .setPositiveButton("OK",
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                  String userInputString = userInput.getText().toString().trim();
                  if(!(userInputString.isEmpty() || userInput.length() == 0)) {
                    CustomList customList = new CustomList();
                    customList.setId(userInputString.toLowerCase());
                    customList.setListName(userInputString);
                    customListViewModel.insert(customList);
                  }
                }
              })
          .setNegativeButton("Cancel",
              new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                  dialog.cancel();
                }
              });

      // create alert dialog
      AlertDialog alertDialog = alertDialogBuilder.create();

      // show it
      alertDialog.show();

    }
  };

  private SwipeToDeleteCallback makeCallback() {
    return new SwipeToDeleteCallback(this) {
      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        final CustomList favorite = customListViewAdapter.getFavoriteFromList(position);

        customListViewAdapter.removeCustomList(position);
        //customListViewAdapter.delete(favorite);

        Snackbar snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.remove_jp_fav, favorite.getListName()),
            Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           // favoriteViewModel.insert(favorite);
          //  favoriteViewAdapter.restoreItem(position, favorite);
          }
        });
        snackbar.show();
      }
    };
  }
}

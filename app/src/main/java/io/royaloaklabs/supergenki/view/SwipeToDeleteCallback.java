package io.royaloaklabs.supergenki.view;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import io.royaloaklabs.supergenki.R;

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.Callback {
  private static final String BACKGROUND_COLOR_HEX = "#B80F0A";

  Context context;
  private Paint clearPaint;
  private ColorDrawable background;
  private Drawable deleteDrawable;
  private int intrinsicWidth;
  private int intrinsicHeight;

  public SwipeToDeleteCallback(Context context) {
    this.context = context;
    background = new ColorDrawable(Color.parseColor(BACKGROUND_COLOR_HEX));
    clearPaint = new Paint();
    clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    deleteDrawable = ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep_black_48dp);
    intrinsicHeight = deleteDrawable.getIntrinsicHeight();
    intrinsicWidth = deleteDrawable.getIntrinsicWidth();
  }

  public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
    return this.makeMovementFlags(0, ItemTouchHelper.LEFT);
  }

  @Override
  public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
    return false;
  }

  @Override
  public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    View itemView = viewHolder.itemView;
    int itemHeight = itemView.getHeight();

    boolean isCancelled = dX == 0 && !isCurrentlyActive;

    if (isCancelled) {
      clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
      return;
    }

    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
    background.draw(c);

    int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
    int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
    int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
    int deleteIconRight = itemView.getRight() - deleteIconMargin;
    int deleteIconBottom = deleteIconTop + intrinsicHeight;


    deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
    deleteDrawable.draw(c);

    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
  }

  private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
    c.drawRect(left, top, right, bottom, clearPaint);
  }

  @Override
  public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
    return 0.7f;
  }

}

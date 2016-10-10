package com.fbi.picturemode.adapter;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 09/10/2016
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
  private GestureDetectorCompat gestureDetectorCompat;
  private RecyclerView recyclerView;

  public OnRecyclerItemClickListener(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    gestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
  }

  @Override
  public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
    gestureDetectorCompat.onTouchEvent(e);
    return false;
  }

  @Override
  public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    gestureDetectorCompat.onTouchEvent(e);
  }

  @Override
  public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
  }

  public abstract void onItemClick(RecyclerView.ViewHolder vh);

  public abstract void onLongClick(RecyclerView.ViewHolder vh);

  private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
      View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

      if (child != null) {
        RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
        onItemClick(vh);
      }
      return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
      super.onLongPress(e);
      View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

      if (child != null) {
        RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
        onLongClick(vh);
      }
    }
  }
}

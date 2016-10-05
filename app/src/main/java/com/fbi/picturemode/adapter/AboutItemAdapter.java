package com.fbi.picturemode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class AboutItemAdapter extends RecyclerView.Adapter<AboutItemAdapter.AboutItemViewHolder> {

  private List<String> contents;

  public AboutItemAdapter(List<String> contents) {
    this.contents = contents;
  }

  @Override
  public AboutItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new AboutItemViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
        .item_about_app, parent, false));
  }

  @Override
  public void onBindViewHolder(AboutItemViewHolder holder, int position) {
    holder.content.setText(contents.get(position));
  }

  @Override
  public int getItemCount() {
    return contents.size();
  }

  class AboutItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_about_content) TextView content;
    public AboutItemViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}

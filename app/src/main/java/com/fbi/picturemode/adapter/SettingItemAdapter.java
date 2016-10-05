package com.fbi.picturemode.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.fbi.picturemode.MyApp;
import com.fbi.picturemode.R;
import com.fbi.picturemode.entity.SettingItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/5/16
 */

public class SettingItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<SettingItem> settingItemList;

  public SettingItemAdapter(List<SettingItem> settingItemList) {
    this.settingItemList = settingItemList;
  }

  @Override
  public int getItemViewType(int position) {
    return settingItemList.get(position).getType();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == SettingItem.TYPE_BLANK) {
      return new BlankViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
          .item_setting_blank_layout, parent, false));
    } else if (viewType == SettingItem.TYPE_SWITCH) {
      return new SwitchViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
          .item_setting_switch_layout, parent, false));
    } else if (viewType == SettingItem.TYPE_NORMAL) {
      return new NormalViewHolder(LayoutInflater.from(MyApp.getContext()).inflate(R.layout
          .item_setting_normal_layout, parent, false));
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case SettingItem.TYPE_BLANK:
        break;
      case SettingItem.TYPE_SWITCH:
        ((SwitchViewHolder) holder).bindData(position);
        break;
      case SettingItem.TYPE_NORMAL:
        ((NormalViewHolder) holder).bindData(position);
        break;
    }
  }

  @Override
  public int getItemCount() {
    return settingItemList.size();
  }

  class BlankViewHolder extends RecyclerView.ViewHolder {

    public BlankViewHolder(View itemView) {
      super(itemView);
    }
  }

  class SwitchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.setting_switch) Switch aSwitch;

    public SwitchViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindData(final int position) {
      title.setText(settingItemList.get(position).getTitle());
      aSwitch.setChecked(settingItemList.get(position).isChecked());
      aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
          if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChange(settingItemList.get(position).getItemId(), b);
          }
        }
      });
    }
  }

  class NormalViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title) TextView title;
    @BindView(R.id.tv_subtitle) TextView subtitle;
    @BindView(R.id.tv_right_content) TextView rightContent;
    @BindView(R.id.iv_right_arrow) ImageView rightArrow;
    @BindView(R.id.root_view) RelativeLayout rootView;

    public NormalViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindData(final int position) {
      showArrow(settingItemList.get(position).isHasArrow());
      showTitle(settingItemList.get(position).getTitle());
      showSubtitle(settingItemList.get(position).getSubTitle());
      showRightContent(settingItemList.get(position).getRightContent());
      if (settingItemList.get(position).isClickable()) {
        rootView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (onItemClickListener != null) {
              onItemClickListener.onItemClick(settingItemList.get(position).getItemId());
            }
          }
        });
      }
    }

    private void showRightContent(String rightContent) {
      if (TextUtils.isEmpty(rightContent)) {
        this.rightContent.setVisibility(View.GONE);
      } else {
        this.rightContent.setVisibility(View.VISIBLE);
        this.rightContent.setText(rightContent);
      }
    }

    private void showTitle(String title) {
      this.title.setText(title);
    }

    private void showSubtitle(String subtitle) {
      if (TextUtils.isEmpty(subtitle)) {
        this.subtitle.setVisibility(View.GONE);
      } else {
        this.subtitle.setVisibility(View.VISIBLE);
        this.subtitle.setText(subtitle);
      }
    }

    private void showArrow(boolean show) {
      if (show) {
        rightArrow.setVisibility(View.VISIBLE);
      } else {
        rightArrow.setVisibility(View.GONE);
      }
    }

  }

  public interface OnCheckedChangeListener {
    void onCheckedChange(int itemId, boolean checked);
  }

  private OnCheckedChangeListener onCheckedChangeListener;

  public void addOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
    this.onCheckedChangeListener = onCheckedChangeListener;
  }

  public interface OnItemClickListener {
    void onItemClick(int itemId);
  }

  private OnItemClickListener onItemClickListener;

  public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}

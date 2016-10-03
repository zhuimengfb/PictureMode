package com.fbi.picturemode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fbi.picturemode.R;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/3/16
 */

public class MeFragment extends BaseFragment {

  public static MeFragment newInstance() {
    MeFragment meFragment = new MeFragment();
    return meFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_me, container, false);

    return view;


  }

  @Override
  public void initPresenter() {

  }
}

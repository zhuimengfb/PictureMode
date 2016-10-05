package com.fbi.picturemode.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.fbi.picturemode.R;
import com.fbi.picturemode.fragment.BaseFragment;
import com.fbi.picturemode.fragment.MyCollectCollectionFragment;
import com.fbi.picturemode.fragment.MyCollectPictureFragment;
import com.fbi.picturemode.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 10/4/16
 */

public class MyCollectionActivity extends BaseActivity {

  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.view_pager) ViewPager viewPager;
  private int currentMode = Constants.MANAGE_COLLECT_MODE_NORMAL;
  private List<BaseFragment> fragments = new ArrayList<>();
  private SectionAdapter sectionAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(getString(R.string.my_collections));
    enableDisplayHomeAsUp();
    initData();
  }

  private void initData() {
    fragments.add(MyCollectPictureFragment.newInstance());
    fragments.add(MyCollectCollectionFragment.newInstance());
    sectionAdapter = new SectionAdapter(getSupportFragmentManager());
    viewPager.setAdapter(sectionAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_my_collections);

  }

  @Override
  public void initPresenter() {

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_manage:
        if (currentMode == Constants.MANAGE_COLLECT_MODE_NORMAL) {
          item.setIcon(R.drawable.manage_success);
          currentMode = Constants.MANAGE_COLLECT_MODE_DELETE;
          ((MyCollectPictureFragment) fragments.get(0)).setMode(currentMode);
          ((MyCollectCollectionFragment) fragments.get(1)).setMode(currentMode);
        } else if (currentMode == Constants.MANAGE_COLLECT_MODE_DELETE) {
          item.setIcon(R.drawable.manage_mode);
          currentMode = Constants.MANAGE_COLLECT_MODE_NORMAL;
          ((MyCollectPictureFragment) fragments.get(0)).setMode(currentMode);
          ((MyCollectCollectionFragment) fragments.get(1)).setMode(currentMode);
        }
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.manage_collect_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  class SectionAdapter extends FragmentStatePagerAdapter {

    public SectionAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0:
          return getString(R.string.photos);
        case 1:
          return getString(R.string.collections);
      }
      return "";
    }
  }
}

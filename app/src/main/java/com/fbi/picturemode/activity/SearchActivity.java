package com.fbi.picturemode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.fbi.picturemode.R;
import com.fbi.picturemode.fragment.BaseFragment;
import com.fbi.picturemode.fragment.SearchCollectionFragment;
import com.fbi.picturemode.fragment.SearchPhotoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: FBi.
 * Email: bofu1993@163.com.
 * Date: 17/10/2016
 */

public class SearchActivity extends BaseActivity {

  @BindView(R.id.container) ViewPager viewPager;
  @BindView(R.id.tab_layout) TabLayout tabLayout;
  private List<BaseFragment> fragments = new ArrayList<>();
  private List<String> titles = new ArrayList<>();
  private SectionPagerAdapter sectionAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    enableDisplayHomeAsUp();
    setTitle("");
    initView();
    initData();
    initEvent();
  }

  private void initEvent() {
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  private void initData() {
    fragments.add(SearchPhotoFragment.newInstance());
    titles.add(getString(R.string.photo));
    fragments.add(SearchCollectionFragment.newInstance());
    titles.add(getString(R.string.collections));
    sectionAdapter = new SectionPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(sectionAdapter);
    tabLayout.setupWithViewPager(viewPager);
//    tabLayout.getTabAt(0).setIcon(R.drawable.tab_explore);
//    tabLayout.getTabAt(1).setIcon(R.drawable.tab_collections);
  }

  private void initView() {

  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_search);
  }

  @Override
  public void initPresenter() {

  }

  @Override
  public void destroyPresenter() {

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.search_menu, menu);
    SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id
        .action_search));
    searchView.setIconified(false);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        ((SearchPhotoFragment) fragments.get(0)).query(query);
        ((SearchCollectionFragment) fragments.get(1)).query(query);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    return super.onCreateOptionsMenu(menu);
  }

  class SectionPagerAdapter extends FragmentStatePagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
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
      return titles.get(position);
    }
  }

  public static void toThisActivity(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, SearchActivity.class);
    context.startActivity(intent);
  }
}

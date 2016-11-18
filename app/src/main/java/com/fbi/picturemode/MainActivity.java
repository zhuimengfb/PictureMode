package com.fbi.picturemode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.fbi.picturemode.activity.BaseActivity;
import com.fbi.picturemode.activity.SearchActivity;
import com.fbi.picturemode.activity.SettingActivity;
import com.fbi.picturemode.fragment.BaseFragment;
import com.fbi.picturemode.fragment.CollectionFragment;
import com.fbi.picturemode.fragment.MeFragment;
import com.fbi.picturemode.fragment.NewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

  private static final int INDEX_ME = 2;
  private static final int INDEX_NEW = 0;
  private static final int INDEX_COLLECTION = 1;
  @BindView(R.id.navigation_bar) BottomNavigationBar navigationBar;
  @BindView(R.id.view_pager) ViewPager viewPager;
  @BindView(R.id.layout_search) RelativeLayout searchLayout;
  private List<BaseFragment> fragments = new ArrayList<>();
  private SectionAdapter sectionAdapter;
  private int currentPage = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initToolbarMenu();
    initNavigationBar();
    initData();
    initEvent();
  }

  private void initToolbarMenu() {
    getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.action_settings:
            SettingActivity.toThisActivity(MainActivity.this);
            break;
        }
        return false;
      }
    });
  }

  @Override
  public void setCustomLayout() {
    setContentView(R.layout.activity_main);
  }

  private void initEvent() {
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        navigationBar.selectTab(position);
        if (position == INDEX_ME) {
          searchLayout.setVisibility(View.GONE);
        } else {
         //TODO 暂时隐藏
//          searchLayout.setVisibility(View.GONE);
          searchLayout.setVisibility(View.VISIBLE);
        }
        currentPage = position;
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
    navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
      @Override
      public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
      }

      @Override
      public void onTabUnselected(int position) {

      }

      @Override
      public void onTabReselected(int position) {

      }
    });
    searchLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SearchActivity.toThisActivity(MainActivity.this);
      }
    });
  }

  private void initData() {
    fragments.add(NewFragment.newInstance());
    fragments.add(CollectionFragment.newInstance());
    fragments.add(MeFragment.newInstance());
    sectionAdapter = new SectionAdapter(getSupportFragmentManager());
    viewPager.setAdapter(sectionAdapter);
    viewPager.setOffscreenPageLimit(3);
  }

  private void initNavigationBar() {
    navigationBar.addItem(new BottomNavigationItem(R.drawable.tab_explore, getString(R.string
        .explore)))
        .addItem(new BottomNavigationItem(R.drawable.tab_collections, getString(R.string
            .collections)))
        .addItem(new BottomNavigationItem(R.drawable.tab_me, getString(R.string.me))).initialise();
  }

  @Override
  public void initPresenter() {

  }

  @Override
  public void destroyPresenter() {

  }

  public static void toMainActivityAndKillSelf(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, MainActivity.class);
    context.startActivity(intent);
    ((BaseActivity) context).finish();
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
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.search_menu,menu);
    return super.onCreateOptionsMenu(menu);
  }

}

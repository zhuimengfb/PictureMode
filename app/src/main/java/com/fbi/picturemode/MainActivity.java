package com.fbi.picturemode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.fbi.picturemode.activity.BaseActivity;
import com.fbi.picturemode.fragment.BaseFragment;
import com.fbi.picturemode.fragment.CollectionFragment;
import com.fbi.picturemode.fragment.MeFragment;
import com.fbi.picturemode.fragment.NewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  @BindView(R.id.navigation_bar) BottomNavigationBar navigationBar;
  @BindView(R.id.view_pager) ViewPager viewPager;

  private List<BaseFragment> fragments = new ArrayList<>();
  private SectionAdapter sectionAdapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initNavigationBar();
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
        navigationBar.selectTab(position);
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
    navigationBar.addItem(new BottomNavigationItem(R.drawable.tab_explore,getString(R.string.explore)))
        .addItem(new BottomNavigationItem(R.drawable.tab_collections,getString(R.string.collections)))
        .addItem(new BottomNavigationItem(R.drawable.tab_me,getString(R.string.me))).initialise();
  }

  @Override
  public void initPresenter() {

  }

  public static void toMainActivityAndKillSelf(Context context) {
    Intent intent = new Intent();
    intent.setClass(context, MainActivity.class);
    context.startActivity(intent);
    ((BaseActivity)context).finish();
  }

  class SectionAdapter extends FragmentStatePagerAdapter{

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
}

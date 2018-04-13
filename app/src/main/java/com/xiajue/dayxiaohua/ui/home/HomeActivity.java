package com.xiajue.dayxiaohua.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.xiajue.dayxiaohua.R;
import com.xiajue.dayxiaohua.app.App;
import com.xiajue.dayxiaohua.base.BaseActivity;
import com.xiajue.dayxiaohua.ui.qt.QtFragment;
import com.xiajue.dayxiaohua.ui.xh.XhFragment;
import com.xiajue.dayxiaohua.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * xiaJue 2018/4/8创建
 */
public class HomeActivity extends BaseActivity implements HomeContract
        .View, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager
        .OnPageChangeListener {
    private Toolbar mToolbar;
    private TextView mTitle;
    private ViewPager mViewPager;
    private BottomNavigationView mNavigationView;
    private List mFragmentList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void bindView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.action_title);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
    }

    @Override
    protected void initialize() {
        App.getInstance().setContext(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mFragmentList = new ArrayList();
        mFragmentList.add(XhFragment.newInstance());
        mFragmentList.add(QtFragment.newInstance());
        //测试是否具有本地缓存

        int position = SPUtils.getInstance(this).getInt("position", 0);
        mViewPager.setCurrentItem(position);
        setTitleText(position);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return (Fragment) mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
        mViewPager.addOnPageChangeListener(this);

        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void setUiData(Object data) {

    }

    @Override
    public void onLoadError(Throwable e) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_xh:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.item_qt:
                mViewPager.setCurrentItem(1);
                break;
        }
        selectMenu(item);
        return false;
    }

    /**
     * 解决点击tag不会切换颜色始终选择第一个的问题
     */
    private void selectMenu(MenuItem item) {
        int size = mNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            mNavigationView.getMenu().getItem(i).setChecked(false);
        }
        item.setChecked(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectMenu(mNavigationView.getMenu().getItem(position));
        setTitleText(position);
    }

    private void setTitleText(int position) {
        switch (position) {
            case 0:
                mTitle.setText(R.string.xh);
                break;
            case 1:
                mTitle.setText(R.string.qt);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtils.getInstance(this).put("position", mViewPager.getCurrentItem());
        mFragmentList=null;
    }
}

package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.ui.fragment.FragmentPersonalEquity;

import butterknife.BindView;

/**
 * 个人中心：GRC 明细
 */
public class PersonalGRBGrcActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalGRBGrcActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    PersonalEquityPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_equity);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_equity));
        String[] stringArray = getResources().getStringArray(R.array.personal_equity);
        initPager(stringArray);
    }

    private void initPager(String[] stringArray) {
        mPagerAdapter = new PersonalEquityPagerAdapter(mActivity, getSupportFragmentManager(), stringArray);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
    }

    class PersonalEquityPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private String[] stringArray;

        public PersonalEquityPagerAdapter(Context mContext, FragmentManager fm, String[] stringArray) {
            super(fm);
            this.mContext = mContext;
            this.stringArray = stringArray;
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentPersonalEquity.newInstance(i == 0 ? 1 : 0);
        }

        @Override
        public int getCount() {
            return null == stringArray ? 0 : stringArray.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArray[position];
        }
    }
}

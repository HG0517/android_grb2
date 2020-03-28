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
import android.view.Menu;
import android.view.MenuItem;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.ui.fragment.FragmentPersonalCirculation;

import butterknife.BindView;

/**
 * 个人中心：GRB 明细
 */
public class PersonalGRBGrbActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalGRBGrbActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    PersonalCirculationPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_circulation);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_circulation));
        String[] stringArray = getResources().getStringArray(R.array.personal_equity);
        initPager(stringArray);
    }

    private void initPager(String[] stringArray) {
        mPagerAdapter = new PersonalCirculationPagerAdapter(mActivity, getSupportFragmentManager(), stringArray);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_circulation:
                PersonalTransferFriendsActivity.start(mActivity);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personal_circulation, menu);
        return true;
    }

    class PersonalCirculationPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private String[] stringArray;

        public PersonalCirculationPagerAdapter(Context mContext, FragmentManager fm, String[] stringArray) {
            super(fm);
            this.mContext = mContext;
            this.stringArray = stringArray;
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentPersonalCirculation.newInstance(i == 0 ? 1 : 0);
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

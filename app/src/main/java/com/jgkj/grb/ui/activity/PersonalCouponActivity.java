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
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.fragment.FragmentPersonalCoupon;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 个人中心：我的优惠券
 */
public class PersonalCouponActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalCouponActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    PersonalCouponPagerAdapter mPagerAdapter;
    boolean isNeedShowClean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_coupon);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_coupon));
        String[] stringArray = getResources().getStringArray(R.array.personal_coupon);
        initPager(stringArray);
    }

    private void initPager(String[] stringArray) {
        mPagerAdapter = new PersonalCouponPagerAdapter(mActivity, getSupportFragmentManager(), stringArray);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                isNeedShowClean = i == 1;
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    class PersonalCouponPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private String[] stringArray;

        public PersonalCouponPagerAdapter(Context mContext, FragmentManager fm, String[] stringArray) {
            super(fm);
            this.mContext = mContext;
            this.stringArray = stringArray;
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentPersonalCoupon.newInstance(i + 1);
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

    private void userCleanCopon() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_CLEANCOPON);
        addSubscription(apiStores().userCleanCopon(token), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", ""));
                    } else {
                        toastShow(result.optString("msg", ""));
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                userCleanCopon();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_clear);
        item.setVisible(isNeedShowClean);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personal_tracks, menu);
        return true;
    }

}

package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.fragment.FragmentLuckDrawMine;
import com.jgkj.grb.ui.mvp.luckdraw.LuckDrawRecordModel;
import com.jgkj.utils.token.GetTokenUtils;

import butterknife.BindView;

/**
 * GRB抽奖：我的奖品
 */
public class LuckDrawMineActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LuckDrawMineActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.centerPanel)
    RadioGroup mCenterPanel;
    @BindView(R.id.luck_draw_mine_0)
    RadioButton mLuckDrawMine0;
    @BindView(R.id.luck_draw_mine_1)
    RadioButton mLuckDrawMine1;

    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;

    FragmentLuckDrawMine mFragmentLeft;
    FragmentLuckDrawMine mFragmentRight;

    LuckDrawRecordModel mLuckDrawRecordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_draw_mine);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_luck_draw_mine));

        mCenterPanel.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.luck_draw_mine_0:
                    changeFragment(0);
                    break;
                case R.id.luck_draw_mine_1:
                    changeFragment(1);
                    break;
                default:
                    break;
            }
        });

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_LOTTERY_MYPRIZE);
        addSubscription(apiStores().lotteryMyPrize(token), new ApiCallback<LuckDrawRecordModel>() {
            @Override
            public void onSuccess(LuckDrawRecordModel model) {
                mLuckDrawRecordModel = model;
                if (model.getCode() == 1) {
                    mCenterPanel.getChildAt(0).performClick();
                } else {
                    toastShow(model.getMsg());
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

    private void changeFragment(int index) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        hideAllFragment(fragmentTransaction);
        showFragment(fragmentTransaction, index);

        fragmentTransaction.commit();
    }

    private void showFragment(FragmentTransaction fragmentTransaction, int index) {
        switch (index) {
            case 0:
                if (null == mFragmentLeft) {
                    mFragmentLeft = FragmentLuckDrawMine.newInstance(mLuckDrawRecordModel.getData().getList1());
                    fragmentTransaction.add(R.id.contentPanel, mFragmentLeft);
                }
                fragmentTransaction.show(mFragmentLeft);
                break;
            case 1:
                if (null == mFragmentRight) {
                    mFragmentRight = FragmentLuckDrawMine.newInstance(mLuckDrawRecordModel.getData().getList2());
                    fragmentTransaction.add(R.id.contentPanel, mFragmentRight);
                }
                fragmentTransaction.show(mFragmentRight);
                break;
            default:
                break;
        }
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (null != mFragmentLeft) {
            fragmentTransaction.hide(mFragmentLeft);
        }
        if (null != mFragmentRight) {
            fragmentTransaction.hide(mFragmentRight);
        }
    }
}

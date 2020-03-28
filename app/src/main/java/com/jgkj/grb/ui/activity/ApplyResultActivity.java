package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;

import butterknife.BindView;

/**
 * 结果界面：
 * 申请退款 0、
 * 申请售后(退货退款 1、换货)
 * 实名认证 2、
 * 账户充值 3、
 * 上传凭证 4、
 */
public class ApplyResultActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ApplyResultActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ApplyResultActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @BindView(R.id.apply_result_icon)
    ImageView mApplyResultIcon;
    @BindView(R.id.apply_result_tip_top)
    TextView mApplyResultTipTop;
    @BindView(R.id.apply_result_tip_bottom)
    TextView mApplyResultTipBottom;

    int mType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_result);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_apply_result));

        Intent intent = getIntent();
        if (intent.hasExtra("type")) {
            mType = intent.getIntExtra("type", -1);
        }
        if (mType < 0) {
            onBackPressed();
            return;
        }

        initView();
    }

    private void initView() {
        switch (mType) {
            case 0:
                mApplyResultTipTop.setText(R.string.apply_result_tip_00);
                mApplyResultTipBottom.setText(R.string.apply_result_tip_01);
                break;
            case 1:
                mApplyResultTipTop.setText(R.string.apply_result_tip_10);
                mApplyResultTipBottom.setText(R.string.apply_result_tip_11);
                break;
            case 2:
                mApplyResultTipTop.setText(R.string.apply_result_tip_20);
                mApplyResultTipBottom.setText(R.string.apply_result_tip_21);
                break;
            case 3:
                mApplyResultTipTop.setText(getString(R.string.apply_result_tip_30));
                mApplyResultTipBottom.setText(getString(R.string.apply_result_tip_31));
                break;
            case 4:
                mApplyResultTipTop.setText(getString(R.string.apply_result_tip_40));
                mApplyResultTipBottom.setText(getString(R.string.apply_result_tip_41));
                break;
            default:
                break;
        }
    }
}

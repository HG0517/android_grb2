package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;

import butterknife.BindView;

/**
 * 谷聚金：成为代理商，资质审核
 */
public class GujujinBecomeAgentQualificationsActivity extends BaseActivity {

    public static void startActivityForResult(Activity context, int level) {
        Intent intent = new Intent(context, GujujinBecomeAgentQualificationsActivity.class);
        intent.putExtra("level", level);
        context.startActivityForResult(intent, 10013);
    }

    @BindView(R.id.become_agent_speed_tv_0)
    TextView mBecomeAgentSpeedTv0;
    @BindView(R.id.become_agent_speed_tv_1)
    TextView mBecomeAgentSpeedTv1;
    @BindView(R.id.become_agent_speed_tv_2)
    TextView mBecomeAgentSpeedTv2;
    @BindView(R.id.become_agent_speed_tv_3)
    TextView mBecomeAgentSpeedTv3;
    @BindView(R.id.become_agent_speed_line_0)
    View mBecomeagentspeedline0;
    @BindView(R.id.become_agent_speed_line_1)
    View mBecomeagentspeedline1;
    @BindView(R.id.become_agent_speed_line_2)
    View mBecomeagentspeedline2;
    @BindView(R.id.become_agent_speed_iv_0)
    ImageView mBecomeAgentSpeedIv0;
    @BindView(R.id.become_agent_speed_iv_1)
    ImageView mBecomeAgentSpeedIv1;
    @BindView(R.id.become_agent_speed_iv_2)
    ImageView mBecomeAgentSpeedIv2;
    @BindView(R.id.become_agent_speed_iv_3)
    ImageView mBecomeAgentSpeedIv3;

    @BindView(R.id.become_agent_qualifications)
    ImageView mQualificationsIv;
    @BindView(R.id.become_agent_qualifications_tip)
    TextView mQualificationsTv;

    @BindView(R.id.submission)
    CardView mSubmission;

    int mLevel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_qualifications);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_gujujin_become_agent_complete);

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            mLevel = intent.getIntExtra("level", -1);
        }
        if (mLevel < 0) {
            toastShow(R.string.gujujin_become_agent_no_level);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mSubmission);
        initSpeed();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.submission:
                GujujinBecomeAgentFirstOperationActivity.startActivityForResult(this, mLevel);
                break;
            default:
                break;
        }
    }

    private void initSpeed() {
        mBecomeAgentSpeedTv0.setSelected(true);
        mBecomeAgentSpeedIv0.setSelected(true);
        mBecomeAgentSpeedTv1.setSelected(true);
        mBecomeAgentSpeedIv1.setSelected(true);
        mBecomeagentspeedline0.setBackgroundColor(Color.parseColor("#F53C5E"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10013) {
                setResult(Activity.RESULT_OK);
                onBackPressed();
            }
        }
    }

}

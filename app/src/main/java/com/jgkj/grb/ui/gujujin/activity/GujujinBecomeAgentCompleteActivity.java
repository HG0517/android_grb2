package com.jgkj.grb.ui.gujujin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
 * 谷聚金：成为代理商，正式成为代理商
 */
public class GujujinBecomeAgentCompleteActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GujujinBecomeAgentCompleteActivity.class);
        context.startActivity(intent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_complete);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_gujujin_become_agent_complete);

        RxView.setOnClickListeners(this, mSubmission);
        initSpeed();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.submission:
                onBackPressed();
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
        mBecomeAgentSpeedTv2.setSelected(true);
        mBecomeAgentSpeedIv2.setSelected(true);
        mBecomeAgentSpeedTv3.setSelected(true);
        mBecomeAgentSpeedIv3.setSelected(true);
        mBecomeagentspeedline0.setBackgroundColor(Color.parseColor("#F53C5E"));
        mBecomeagentspeedline1.setBackgroundColor(Color.parseColor("#F53C5E"));
        mBecomeagentspeedline2.setBackgroundColor(Color.parseColor("#F53C5E"));
    }

}

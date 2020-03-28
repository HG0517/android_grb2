package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;

import butterknife.BindView;

/**
 * 个人中心：GRB，公共市场转让
 */
public class PersonalGRBTransferPublicActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalGRBTransferPublicActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.transfer_public_0)
    CardView mTransferPublic0;
    @BindView(R.id.transfer_public_1)
    CardView mTransferPublic1;
    @BindView(R.id.transfer_public_2)
    CardView mTransferPublic2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_grb_transfer_public);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_grb_transfer_public));

        RxView.setOnClickListeners(this, mTransferPublic0, mTransferPublic1, mTransferPublic2);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.transfer_public_0:
                PersonalGRBTransferPublicSecondActivity.start(this, 0);
                break;
            case R.id.transfer_public_1:
                PersonalGRBTransferPublicSecondActivity.start(this, 1);
                break;
            case R.id.transfer_public_2:
                PersonalGRBTransferPublicSecondActivity.start(this, 2);
                break;
            default:
                break;
        }
    }
}

package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 谷聚金：成为代理商，选择代理级别
 */
public class GujujinBecomeAgentSelectionLevelActivity extends BaseActivity {

    public static void startActivityForResult(Activity context) {
        Intent intent = new Intent(context, GujujinBecomeAgentSelectionLevelActivity.class);
        context.startActivityForResult(intent, 10013);
    }

    @BindView(R.id.selection_level_product)
    FrameLayout mSelectionLevelProduct;
    @BindView(R.id.selection_level_product_iv)
    ImageView mSelectionLevelProductIv;

    @BindView(R.id.selection_level_0)
    FrameLayout mSelectionLevel0;
    @BindView(R.id.selection_level_desc_0)
    TextView mSelectionLevelDesc0;
    @BindView(R.id.selection_level_iv_0)
    ImageView mSelectionLevelIv0;

    @BindView(R.id.selection_level_1)
    FrameLayout mSelectionLevel1;
    @BindView(R.id.selection_level_desc_1)
    TextView mSelectionLevelDesc1;
    @BindView(R.id.selection_level_iv_1)
    ImageView mSelectionLevelIv1;

    @BindView(R.id.selection_level_2)
    FrameLayout mSelectionLevel2;
    @BindView(R.id.selection_level_desc_2)
    TextView mSelectionLevelDesc2;
    @BindView(R.id.selection_level_iv_2)
    ImageView mSelectionLevelIv2;

    @BindView(R.id.selection_level_3)
    FrameLayout mSelectionLevel3;
    @BindView(R.id.selection_level_desc_3)
    TextView mSelectionLevelDesc3;
    @BindView(R.id.selection_level_iv_3)
    ImageView mSelectionLevelIv3;

    @BindView(R.id.submission)
    CardView mSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_selection_level);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_gujujin_become_agent_selection_level);

        RxView.setOnClickListeners(this, mSelectionLevelProduct, mSelectionLevel0,
                mSelectionLevel1, mSelectionLevel2, mSelectionLevel3, mSubmission);

        mSelectionLevelProductIv.setSelected(true);
        mSelectionLevelProduct.setEnabled(false);
        mSelectionLevel0.setEnabled(false);
        mSelectionLevel1.setEnabled(false);
        mSelectionLevel2.setEnabled(false);
        mSelectionLevel3.setEnabled(false);

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_CONTENT);
        addSubscription(apiStores().indexValleyContent(token), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        mSelectionLevel0.setEnabled(true);
                        mSelectionLevel1.setEnabled(true);
                        mSelectionLevel2.setEnabled(true);
                        mSelectionLevel3.setEnabled(true);
                        JSONArray data = result.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            bindView(i, data.getJSONArray(i));
                        }
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

    private void bindView(int index, JSONArray jsonArray) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                sb.append(jsonArray.getString(i));
                sb.append("\n");
            }
        } catch (JSONException e) {
        }

        String s = getString(R.string.gujujin_become_agent_selection_level_tip);
        switch (index) {
            case 0:
                sb.append(s);
                mSelectionLevelDesc0.setText(sb.toString().trim());
                break;
            case 1:
                sb.append(s);
                mSelectionLevelDesc1.setText(sb.toString().trim());
                break;
            case 2:
                sb.append(s);
                mSelectionLevelDesc2.setText(sb.toString().trim());
                break;
            case 3:
                mSelectionLevelDesc3.setText(sb.toString().trim());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.selection_level_product:
                mSelectionLevelProductIv.setSelected(!mSelectionLevelProductIv.isSelected());
                break;
            case R.id.selection_level_0:
                mSelectionLevelIv0.setSelected(!mSelectionLevelIv0.isSelected());
                mSelectionLevelIv1.setSelected(false);
                mSelectionLevelIv2.setSelected(false);
                mSelectionLevelIv3.setSelected(false);
                break;
            case R.id.selection_level_1:
                mSelectionLevelIv0.setSelected(false);
                mSelectionLevelIv1.setSelected(!mSelectionLevelIv1.isSelected());
                mSelectionLevelIv2.setSelected(false);
                mSelectionLevelIv3.setSelected(false);
                break;
            case R.id.selection_level_2:
                mSelectionLevelIv0.setSelected(false);
                mSelectionLevelIv1.setSelected(false);
                mSelectionLevelIv2.setSelected(!mSelectionLevelIv2.isSelected());
                mSelectionLevelIv3.setSelected(false);
                break;
            case R.id.selection_level_3:
                mSelectionLevelIv0.setSelected(false);
                mSelectionLevelIv1.setSelected(false);
                mSelectionLevelIv2.setSelected(false);
                mSelectionLevelIv3.setSelected(!mSelectionLevelIv3.isSelected());
                break;
            case R.id.submission:
                if (!mSelectionLevelProductIv.isSelected()) {
                    toastShow(R.string.gujujin_become_agent_product_tip);
                    return;
                }
                if (!mSelectionLevelIv0.isSelected() && !mSelectionLevelIv1.isSelected()
                        && !mSelectionLevelIv2.isSelected() && !mSelectionLevelIv3.isSelected()) {
                    toastShow(R.string.gujujin_become_agent_no_level);
                    return;
                }
                int level = 0;
                if (mSelectionLevelIv0.isSelected())
                    level = 4;
                else if (mSelectionLevelIv1.isSelected())
                    level = 3;
                else if (mSelectionLevelIv2.isSelected())
                    level = 2;
                else if (mSelectionLevelIv3.isSelected())
                    level = 1;
                GujujinBecomeAgentInformationActivity.startActivityForResult(this, level);
                break;
        }
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

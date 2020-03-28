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
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.PersonalWalletAddressDialog;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 个人中心：GRB，钱包地址
 */
public class PersonalWalletAddressActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalWalletAddressActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.wallet_address_0)
    CardView mWalletAddress0;
    @BindView(R.id.wallet_address_1)
    CardView mWalletAddress1;
    @BindView(R.id.wallet_address_2)
    CardView mWalletAddress2;

    private String mAddress = "";
    private String mQrcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_wallet_address);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_wallet_address));

        RxView.setOnClickListeners(this, mWalletAddress0, mWalletAddress1, mWalletAddress2);

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_ETHADDRESS);
        addSubscription(apiStores().userEthAddress(token), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    // address  ：地址文本      iban   ：二维码文本
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        mAddress = data.optString("address");
                        mQrcode = data.optString("iban");
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
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.wallet_address_0: // getString(R.string.personal_wallet_address_tip_0)
                PersonalWalletAddressDialog addressDialog0 = PersonalWalletAddressDialog.newInstance(0, mAddress, mQrcode);
                addressDialog0.setCancelable(false);
                addressDialog0.showDialog(getSupportFragmentManager());
                break;
            case R.id.wallet_address_1: // getString(R.string.personal_wallet_address_tip_1)
                PersonalWalletAddressDialog addressDialog1 = PersonalWalletAddressDialog.newInstance(1, mAddress, mQrcode);
                addressDialog1.setCancelable(false);
                addressDialog1.showDialog(getSupportFragmentManager());
                break;
            case R.id.wallet_address_2: // getString(R.string.personal_wallet_address_tip_2)
                PersonalWalletAddressDialog addressDialog2 = PersonalWalletAddressDialog.newInstance(2, mAddress, mQrcode);
                addressDialog2.setCancelable(false);
                addressDialog2.showDialog(getSupportFragmentManager());
                break;
            default:
                break;
        }
    }
}

package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.fragment.FragmentOrderManagementPage;

import butterknife.BindView;

/**
 * 搜索：订单
 */
public class SearchOrderResultActivity extends BaseActivity {

    public static void start(Context context, String searchKey, int orderType) {
        Intent intent = new Intent(context, SearchOrderResultActivity.class);
        intent.putExtra("searchKey", searchKey);
        intent.putExtra("orderType", orderType);
        context.startActivity(intent);
    }

    @BindView(R.id.home)
    CardView mBackHome;
    @BindView(R.id.home_icon)
    ImageView mBackIcon;
    @BindView(R.id.search_input_et)
    EditText mSearchInputEt;

    FragmentOrderManagementPage fragmentOrder;
    String mSearchKey = "";
    int mOrderType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order_result);

        Intent intent = getIntent();
        if (intent.hasExtra("searchKey")) {
            mSearchKey = intent.getStringExtra("searchKey");
        }
        if (intent.hasExtra("orderType")) {
            mOrderType = intent.getIntExtra("orderType", 0);
        }
        if (TextUtils.isEmpty(mSearchKey)) {
            toastShow(R.string.search_input_empty);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mBackHome);
        initSearchInput();

        fragmentOrder = FragmentOrderManagementPage.newInstance(mSearchKey, mOrderType, 8);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contentPanel, fragmentOrder);
        fragmentTransaction.commit();
    }

    private void initSearchInput() {
        if (!TextUtils.isEmpty(mSearchKey)) {
            mSearchInputEt.setText(mSearchKey);
            gotoSearch(mSearchInputEt.getText().toString());
        }
        mSearchInputEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // do something
                if (event != null && event.getAction() == KeyEvent.ACTION_UP) {
                    gotoSearch(mSearchInputEt.getText().toString());
                }
                return true;
            }
            return false;
        });
    }

    private void gotoSearch(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            toastShow(R.string.search_input_empty);
            return;
        }
        hideInputSoft();
        // 接口，对 fragmentOrder 进行数据更新
        if (null != fragmentOrder)
            fragmentOrder.onRefreshSearch(searchKey, mOrderType, 8);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}

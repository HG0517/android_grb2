package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalBalanceAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBCashModel;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.grb.view.datepicker.DatePickerSingleMonth;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 个人中心：账户余额
 */
public class PersonalBalanceActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalBalanceActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.personal_balance)
    TextView mPersonalBalance;
    @BindView(R.id.personal_balance_select)
    FrameLayout mPersonalBalanceSelect;
    @BindView(R.id.personal_balance_month)
    TextView mPersonalBalanceMonth;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    PersonalBalanceAdapter mAdapter;
    List<PersonalGRBCashModel.GRBCashBean> mList = new ArrayList<>();
    int page = 1;
    int size = 10;
    String year = "";
    String month = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_balance);

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_balance));
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        setTitleTextColor(toolbar, Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);

        initSmartRefreshLayout();
        initRecyclerView();

        RxView.setOnClickListeners(this, mPersonalBalanceSelect);

        analysisDate(System.currentTimeMillis());
        mPersonalBalanceMonth.setText(String.format(getString(R.string.personal_balance_month), month));
        onLazyLoad();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            page = 1;
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PersonalBalanceAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.personal_balance_select:
                showDatePickerSingleMonth();
                break;
            default:
                break;
        }
    }

    private void showDatePickerSingleMonth() {
        DatePickerSingleMonth matePicker = new DatePickerSingleMonth(this, timestamp -> {
            // timestamp <= 0 不选择时间
            if (timestamp > 0) {
                analysisDate(timestamp);
                mPersonalBalanceMonth.setText(String.format(getString(R.string.personal_balance_month), month));
                mSmartRefreshLayout.autoRefresh();
            }
        }, "2000-01-01 00:00", "2119-12-31 23:59");
        // 点击屏幕或物理返回键关闭
        matePicker.setCancelable(true);
        // 滚动动画
        matePicker.setCanShowAnim(true);
        // 显示时和分
        matePicker.setCanShowPreciseTime(false);
        matePicker.setCanShowDay(false);
        matePicker.setCanShowMonth(true);
        // 循环滚动
        matePicker.setScrollLoop(true);
        matePicker.show(System.currentTimeMillis());
    }

    private void onLazyLoad() {
        // year ：年     month ：月   type:1：grc，2：grb，3：账户余额明细，4 gra   page:第几页   status：0 支出，1 收入
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYGRBCASH);
        Map<String, Object> body = new HashMap<>();
        body.put("type", 3);
        body.put("year", year);
        body.put("month", month);
        body.put("page", page);
        body.put("size", size);
        addSubscription(apiStores().userGRBCash(token, body), new ApiCallback<PersonalGRBCashModel>() {
            @Override
            public void onSuccess(PersonalGRBCashModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;
                    mPersonalBalance.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    if (null != model.getData().getList() && model.getData().getList().size() > 0) {
                        mList.addAll(model.getData().getList());
                        mSmartRefreshLayout.finishLoadMore(1, true, model.getData().getList().size() < size);
                    } else {
                        mSmartRefreshLayout.finishLoadMore(1, true, true);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    toastShow(model.getMsg());
                    mSmartRefreshLayout.finishLoadMore(1, true, true);
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
                mSmartRefreshLayout.finishLoadMore(1, true, true);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
                mSmartRefreshLayout.finishRefresh(1, true);
            }
        });
    }

    private void analysisDate(long millisecond) {
        String long2Str = DateFormatUtils.long2Str(millisecond, DateFormatUtils.DATE_FORMAT_PATTERN_YMD);
        String[] split = long2Str.split("-");
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                year = split[i];
                continue;
            }
            if (i == 1) {
                month = split[i];
                break;
            }
        }
    }

}

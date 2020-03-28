package com.jgkj.grb.ui.gujujin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.gujujin.adapter.GujujinIncomeDetailAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBCashModel;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.grb.view.datepicker.DatePickerSingleMonth;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 谷聚金：奖金明细
 */
public class GujujinIncomeDetailActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GujujinIncomeDetailActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.personal_equity_date)
    CardView mPersonalEquityDate;
    @BindView(R.id.personal_equity_date_tv)
    TextView mPersonalEquityDateTv;
    @BindView(R.id.personal_equity_total)
    TextView mPersonalEquityTotal;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    GujujinIncomeDetailAdapter mAdapter;
    List<PersonalGRBCashModel.GRBCashBean> mList = new ArrayList<>();
    PersonalGRBCashModel mPersonalGRBCashModel;

    int page = 1;
    int size = 10;
    int status = 1;
    String year = "";
    String month = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_income_detail);
        Toolbar toolbar = initToolBar(getString(R.string.gujvjin_index_50));

        initSmartRefreshLayout();
        initRecyclerView();

        analysisDate(System.currentTimeMillis());
        mPersonalEquityDateTv.setText(String.format(getString(R.string.personal_balance_year_month), year, month));

        RxView.setOnClickListeners(this, mPersonalEquityDate);

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
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapter = new GujujinIncomeDetailAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.personal_equity_date:
                showDatePickerSingleMonth();
                break;
            default:
                break;
        }
    }

    private void showDatePickerSingleMonth() {
        DatePickerSingleMonth matePicker = new DatePickerSingleMonth(mActivity, timestamp -> {
            // timestamp <= 0 不选择时间
            if (timestamp > 0) {
                analysisDate(timestamp);
                mPersonalEquityDateTv.setText(String.format(getString(R.string.personal_balance_year_month), year, month));
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

    protected void onLazyLoad() {
        // year ：年     month ：月   type:1：grc，2：grb，3：账户余额明细，4 gra   page:第几页   status：0 支出，1 收入
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYGRBCASH);
        Map<String, Object> body = new HashMap<>();
        body.put("type", 3);
        body.put("type2", 12);
        body.put("year", year);
        body.put("month", month);
        body.put("page", page);
        body.put("size", size);
        body.put("status", status);
        addSubscription(apiStores().userGRBCash(token, body), new ApiCallback<PersonalGRBCashModel>() {
            @Override
            public void onSuccess(PersonalGRBCashModel model) {
                mPersonalGRBCashModel = model;
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;
                    mPersonalEquityTotal.setText(String.format(getString(R.string.gujujin_income_total), model.getData().getTotal()));
                    if (null != model.getData().getList() && model.getData().getList().size() > 0) {
                        mList.addAll(model.getData().getList());

                        mSmartRefreshLayout.finishRefresh(1, true);
                        mSmartRefreshLayout.finishLoadMore(1, true, model.getData().getList().size() < size);
                    } else {
                        mSmartRefreshLayout.finishRefresh(1, true);
                        mSmartRefreshLayout.finishLoadMore(1, true, true);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    toastShow(model.getMsg());
                    mSmartRefreshLayout.finishRefresh(1, true);
                    mSmartRefreshLayout.finishLoadMore(1, true, true);
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
                mSmartRefreshLayout.finishRefresh(1, true);
                mSmartRefreshLayout.finishLoadMore(1, true, true);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
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

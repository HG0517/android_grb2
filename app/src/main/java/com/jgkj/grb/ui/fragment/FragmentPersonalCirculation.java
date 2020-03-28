package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalCirculationAdapter;
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
 * 个人中心：GRB，GRB 明细
 * Created by brightpoplar@163.com on 2019/8/27.
 */
public class FragmentPersonalCirculation extends BaseFragment {

    public static FragmentPersonalCirculation newInstance(int status) {
        FragmentPersonalCirculation fragmentPersonalCirculation = new FragmentPersonalCirculation();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragmentPersonalCirculation.setArguments(bundle);
        return fragmentPersonalCirculation;
    }

    @BindView(R.id.personal_circulation_date)
    CardView mPersonalCirculationDate;
    @BindView(R.id.personal_circulation_date_tv)
    TextView mPersonalCirculationDateTv;
    @BindView(R.id.personal_circulation_total)
    TextView mPersonalCirculationTotal;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    PersonalCirculationAdapter mAdapter;
    List<PersonalGRBCashModel.GRBCashBean> mList = new ArrayList<>();
    PersonalGRBCashModel mPersonalGRBCashModel;

    int page = 1;
    int size = 10;
    int status = 1;
    String year = "";
    String month = "";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_personal_circulation;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments == null)
            return;
        status = arguments.getInt("status", 0);

        initSmartRefreshLayout();
        initRecyclerView();

        analysisDate(System.currentTimeMillis());
        mPersonalCirculationDateTv.setText(String.format(getString(R.string.personal_balance_year_month), year, month));

        RxView.setOnClickListeners(this, mPersonalCirculationDate);
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            mList.clear();
            page = 1;
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapter = new PersonalCirculationAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.personal_circulation_date:
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
                mPersonalCirculationDateTv.setText(String.format(getString(R.string.personal_balance_year_month), year, month));
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

    @Override
    protected void onLazyLoad() {
        // year ：年     month ：月   type:1：grc，2：grb，3：账户余额明细，4 gra   page:第几页   status：0 支出，1 收入
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYGRBCASH);
        Map<String, Object> body = new HashMap<>();
        body.put("type", 2);
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
                    mPersonalCirculationTotal.setText(String.format(getString(R.string.personal_circulation_total_grb), status == 0
                            ? getString(R.string.personal_circulation_type_0) : getString(R.string.personal_circulation_type_1), model.getData().getTotal()));
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

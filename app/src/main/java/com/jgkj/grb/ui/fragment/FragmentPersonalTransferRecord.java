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
import com.jgkj.grb.ui.adapter.PersonalTransferRecordAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalTransferRecordModel;
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
 * 个人中心：GRB，转让记录
 * Created by brightpoplar@163.com on 2019/8/27.
 */
public class FragmentPersonalTransferRecord extends BaseFragment {

    public static FragmentPersonalTransferRecord newInstance(int type) {
        FragmentPersonalTransferRecord fragmentPersonalTransferRecord = new FragmentPersonalTransferRecord();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragmentPersonalTransferRecord.setArguments(bundle);
        return fragmentPersonalTransferRecord;
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

    PersonalTransferRecordAdapter mAdapter;
    List<PersonalTransferRecordModel.DataBean.ListBean> mList = new ArrayList<>();
    PersonalTransferRecordModel mPersonalTransferRecordModel;

    int page = 1;
    int size = 10;
    int type = 1;
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
        type = arguments.getInt("type", 1);

        initSmartRefreshLayout();
        initRecyclerView();

        analysisDate(System.currentTimeMillis());
        mPersonalCirculationDateTv.setText(String.format(getString(R.string.personal_balance_year_month), year, month));

        RxView.setOnClickListeners(this, mPersonalCirculationDate);

        mPersonalCirculationTotal.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
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
        mAdapter = new PersonalTransferRecordAdapter(mActivity, mList);
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
        // year ：年     month ：月   type:1：好友转让 2：公共市场转让   page:第几页   status：0 支出，1 收入
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYGRBTURN);
        Map<String, Object> body = new HashMap<>();
        body.put("type", type);
        body.put("year", year);
        body.put("month", month);
        body.put("page", page);
        body.put("size", size);
        addSubscription(apiStores().userGRBTurn(token, body), new ApiCallback<PersonalTransferRecordModel>() {
            @Override
            public void onSuccess(PersonalTransferRecordModel model) {
                page++;
                mPersonalTransferRecordModel = model;
                if (model.getCode() == 1) {
                    if (type != 1) {
                        mPersonalCirculationTotal.setText(String.format(getString(R.string.personal_grb_transfer_record_tip_1), model.getData().getTotal()));
                    }
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

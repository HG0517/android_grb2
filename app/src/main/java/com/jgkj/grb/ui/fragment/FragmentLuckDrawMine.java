package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.LuckDrawMineAdapter;
import com.jgkj.grb.ui.dialog.LuckDrawReceiveDialog;
import com.jgkj.grb.ui.mvp.luckdraw.LuckDrawRecordBean;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * GRB抽奖：我的奖品
 * Created by brightpoplar@163.com on 2019/8/17.
 */
public class FragmentLuckDrawMine extends BaseFragment {

    public static FragmentLuckDrawMine newInstance(List<LuckDrawRecordBean> list) {
        FragmentLuckDrawMine fragmentLuckDrawMine = new FragmentLuckDrawMine();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        fragmentLuckDrawMine.setArguments(bundle);
        return fragmentLuckDrawMine;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    LuckDrawMineAdapter mAdapter;
    List<LuckDrawRecordBean> mList = new ArrayList<>();
    int page = 1;
    int size = 10;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_luck_draw_mine;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initSmartRefreshLayout();
        initRecyclerView();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapter = new LuckDrawMineAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            LuckDrawReceiveDialog dialog = new LuckDrawReceiveDialog(mActivity);
            dialog.show();
            dialog.setOnActionClickListener(new LuckDrawReceiveDialog.OnActionClickListener() {
                @Override
                public void onCancel() {
                }

                @Override
                public void onSure(String name, String phone, String address) {
                    lotteryReceive(mList.get(position), name, phone, address);
                }
            });
        });
    }

    private void lotteryReceive(LuckDrawRecordBean luckDrawRecordBean, String name, String phone, String address) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_LOTTERY_RECEIVE);
        addSubscription(apiStores().lotteryReceive(token, luckDrawRecordBean.getId(), name, phone, address), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", getString(R.string.luck_draw_mine_receive_success)));
                        mList.remove(luckDrawRecordBean);
                        mAdapter.notifyDataSetChanged();
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
    protected void onLazyLoad() {
        Bundle arguments = getArguments();
        if (null == arguments)
            return;

        List<LuckDrawRecordBean> list = arguments.getParcelableArrayList("list");
        if (null != list) {
            mList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }

}

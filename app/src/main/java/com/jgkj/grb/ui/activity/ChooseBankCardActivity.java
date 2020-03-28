package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.ui.adapter.ChooseBankCardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择银行卡
 */
public class ChooseBankCardActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ChooseBankCardActivity.class);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, int choose) {
        Intent intent = new Intent(activity, ChooseBankCardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("choose", choose);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, 10003);
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    List<BankCardBean> mList;
    ChooseBankCardAdapter mAdapter;

    private int choose = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_card);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_choose_bank_card));

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (null != extras) {
            choose = extras.getInt("choose");
        }

        mList = new ArrayList<>();
        mAdapter = new ChooseBankCardAdapter(mActivity, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (choose > 0 && choose < mList.size()) {
                mList.get(choose).setChoose(0);
            }
            choose = position;
            mList.get(choose).setChoose(1);
            mAdapter.notifyDataSetChanged();

            Bundle bundle = new Bundle();
            bundle.putInt("choose", choose);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
        });

        for (int i = 0; i < 20; i++) {
            mList.add(new BankCardBean(choose == i ? 1 : 0));
        }
    }

    public static class BankCardBean {
        int choose = 0;

        public BankCardBean() {
        }

        public BankCardBean(int choose) {
            this.choose = choose;
        }

        public int getChoose() {
            return choose;
        }

        public void setChoose(int choose) {
            this.choose = choose;
        }
    }
}

package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.AddressManagementAdapter;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.utils.token.GetTokenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 地址管理
 */
public class AddressManagementActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressManagementActivity.class);
        intent.putExtra("choose", false);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, AddressManagementActivity.class);
        intent.putExtra("choose", true);
        activity.startActivityForResult(intent, 10007);
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    List<AddresManagementModel.DataBean> mList = new ArrayList<>();
    AddressManagementAdapter mAdapter;

    boolean isChoose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_management);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_address_manager));

        Intent intent = getIntent();
        if (intent.hasExtra("choose")) {
            isChoose = intent.getBooleanExtra("choose", false);
        }

        initRecyclerView();
        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_MYADDR);
        addSubscription(apiStores().userAddr(token), new ApiCallback<AddresManagementModel>() {
            @Override
            public void onSuccess(AddresManagementModel model) {
                if (model.getCode() == 1) {
                    mList.clear();
                    mList.addAll(model.getData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    toastShow(model.getMsg());
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

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        mAdapter = new AddressManagementAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AddressManagementAdapter.OnItemActionClickListener() {
            @Override
            public void onEditItemClick(View v, int position) {
                AddressAdditionActivity.startActivityForResult(mActivity, mList.get(position), position);
            }

            @Override
            public void onItemClick(View view, int position) {
                if (isChoose) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addr", mList.get(position));
                    intent.putExtra("bundle", bundle);
                    setResult(Activity.RESULT_OK, intent);
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_address_add:
                AddressAdditionActivity.startActivityForResult(this, null, -1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address_management, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10007) {
                if (null == data) { // 添加，刷新？
                    onLazyLoad();
                } else { // 编辑、删除，刷新
                    Bundle extras = data.getBundleExtra("bundle");
                    if (null != extras) { // 编辑
                        AddresManagementModel.DataBean dataBean = (AddresManagementModel.DataBean) extras.getSerializable("result");
                        int position = extras.getInt("position", -1);
                        int delete = extras.getInt("delete", -1);
                        if (null != dataBean && position >= 0) {
                            mList.get(position).setAddr_receiver(dataBean.getAddr_receiver());
                            mList.get(position).setAddr_tel(dataBean.getAddr_tel());
                            mList.get(position).setProvince(dataBean.getProvince());
                            mList.get(position).setCity(dataBean.getCity());
                            mList.get(position).setTown(dataBean.getTown());
                            mList.get(position).setAddr_detail(dataBean.getAddr_detail());
                            mList.get(position).setAddr_addr(dataBean.getAddr_addr());
                            mList.get(position).setAddr_default(dataBean.getAddr_default());
                            mAdapter.notifyDataSetChanged();
                        } else if (delete == 1 && position >= 0) { // 删除
                            mList.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }
}

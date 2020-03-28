package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.ui.fragment.FragmentOrderManagementPage;

/**
 * 云仓库
 */
public class CloudWarehouseActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, CloudWarehouseActivity.class);
        context.startActivity(intent);
    }

    FragmentOrderManagementPage mOrderPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_warehouse);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_cloud_warehouse));

        mOrderPage = FragmentOrderManagementPage.newInstance(6, 0);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contentPanel, mOrderPage);
        fragmentTransaction.show(mOrderPage);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cloud_record:
                CloudWarehouseRecordActivity.start(this);
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
        getMenuInflater().inflate(R.menu.menu_cloud_warehouse, menu);
        return true;
    }
}

package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.ui.fragment.FragmentShoppingCart;

/**
 * 购物车
 */
public class ShoppingCartActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShoppingCartActivity.class);
        context.startActivity(intent);
    }

    FragmentShoppingCart mShopCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Toolbar toolbar = initToolBar(getString(R.string.main_navi_3));

        mShopCart = (FragmentShoppingCart) getSupportFragmentManager().findFragmentById(R.id.shop_cart);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editmode:
                mShopCart.isEditMode = !mShopCart.isEditMode;
                mShopCart.initEditMode();
                invalidateOptionsMenu();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mShopCart.isEditMode) {
            menu.findItem(R.id.menu_editmode).setTitle(R.string.shop_cart_top_right_1);
        } else {
            menu.findItem(R.id.menu_editmode).setTitle(R.string.shop_cart_top_right_0);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_cart, menu);
        return true;
    }

}

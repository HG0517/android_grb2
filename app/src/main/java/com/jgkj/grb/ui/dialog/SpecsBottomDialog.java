package com.jgkj.grb.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.bean.SpecsModel;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.flowtag.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 购物车：规格，选择、修改
 * Created by brightpoplar@163.com on 2019/7/31.
 */
public class SpecsBottomDialog extends DialogFragment implements RxView.Action1 {

    private View mRootView;
    private BottomDialogListener mListener;
    private boolean mIsDismiss = false;

    private ImageView close;
    private ImageView shopIv;
    private TextView shopName;
    private TextView totalTop;
    private TextView totalBottom;
    private LinearLayout buySelectLl;
    private FrameLayout totalNumCut;
    private FrameLayout totalNumAdd;
    private TextView totalNum;
    private FrameLayout selectOk;

    private int mCancleResId;
    private SpecsModel mSpecsModel;
    private List<FlowTagLayoutViewHolder> listFlowTagLayout = new ArrayList<>();
    private Map<Integer, Integer> selectedMap = new TreeMap<>();
    private int selectNumber = 1;
    private ProductDetailsModel.DataBean mDataBean;

    public static SpecsBottomDialog newInstance(int cancleResId, SpecsModel specsModel, ProductDetailsModel.DataBean dataBean) {
        SpecsBottomDialog dialog = new SpecsBottomDialog();
        // Supply as arguments.
        Bundle args = new Bundle();
        args.putInt("cancle", cancleResId);
        args.putSerializable("specs", specsModel);
        args.putParcelable("dataBean", dataBean);
        dialog.setArguments(args);
        return dialog;
    }

    public void setTotalTop(String totalTop) {
        this.totalTop.setText(String.format(getString(R.string.total_top_text), totalTop));
    }

    public void setTotalBottom(String totalBottom) {
        this.totalBottom.setText(totalBottom);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCancleResId = getArguments().getInt("cancle");
        mSpecsModel = (SpecsModel) getArguments().getSerializable("specs");
        mDataBean = getArguments().getParcelable("dataBean");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_specs_bottom_dialog, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        close = view.findViewById(R.id.dialog_close);
        shopIv = view.findViewById(R.id.cart_shop_iv);
        shopName = view.findViewById(R.id.cart_shop_name);
        totalTop = view.findViewById(R.id.total_top);
        totalBottom = view.findViewById(R.id.total_bottom);
        buySelectLl = view.findViewById(R.id.specs_ll);
        totalNumCut = view.findViewById(R.id.total_num_cut);
        totalNumAdd = view.findViewById(R.id.total_num_add);
        totalNum = view.findViewById(R.id.total_num);
        selectOk = view.findViewById(R.id.select_ok);

        if (mCancleResId != 0)
            close.setImageResource(mCancleResId);

        RxView.setOnClickListeners(this, close, selectOk, totalNumAdd, totalNumCut);

        if (null != mSpecsModel) {
            setSelectSpecify(mSpecsModel.getList());
            setTotalTop(mDataBean.getPd_price());
            setTotalBottom(mDataBean.getPd_pv());
        }
        if (null != mDataBean) {
            initInfo(mDataBean);
        }
    }

    private void initInfo(ProductDetailsModel.DataBean dataBean) {
        if (null != dataBean.getPd_pic() && dataBean.getPd_pic().size() > 0) {
            if (!TextUtils.isEmpty(dataBean.getPd_pic().get(0)))
                GlideApp.with(getContext())
                        .load(dataBean.getPd_pic().get(0).startsWith("http:") || dataBean.getPd_pic().get(0).startsWith("https:")
                                ? dataBean.getPd_pic().get(0)
                                : ApiStores.API_SERVER_URL + dataBean.getPd_pic().get(0))
                        .into(shopIv);
        }
        shopName.setText(dataBean.getPd_name());
        totalTop.setText(String.format(getString(R.string.total_top_text), dataBean.getPd_total()));
        totalBottom.setText(getTotalBottom(dataBean));
    }

    public String getTotalBottom(ProductDetailsModel.DataBean model) {
        // "paytype2": 是否有grc支付；
        // "paytype1": 是否有grb支付；,
        // "paytype": 是否有现金支付,
        String payType = "(可抵扣:";
        /*if (model.getPaytype() == 1) {
            payType += model.getPd_price() + "现金";
        }*/
        if (model.getPaytype1() == 1) {
            payType += /*"\n" + */model.getPd_grb() + "份GRB";
        }
        if (model.getPaytype2() == 1) {
            payType += (model.getPaytype1() == 1 ? "\n" : "") + model.getPd_grc() + "份GRC";
        }
        return payType + ")";
    }

    public String getTotalBottom(ProductDetailsModel.DataBean model, ProductDetailsModel.DataBean.SkusBean modelSkus) {
        // "paytype2": 是否有grc支付；
        // "paytype1": 是否有grb支付；,
        // "paytype": 是否有现金支付,
        String payType = "(可抵扣:";
        /*if (model.getPaytype() == 1) {
            payType += modelSkus.getSku_price() + "现金";
        }*/
        if (model.getPaytype1() == 1) {
            payType += /*"\n" + */modelSkus.getSku_grb() + "份GRB";
        }
        if (model.getPaytype2() == 1) {
            payType += (model.getPaytype1() == 1 ? "\n" : "") + modelSkus.getSku_grc() + "份GRC";
        }
        return payType + ")";
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.dialog_close:
                dismiss();
                if (mListener != null) {
                    mListener.onCancel();
                }
                break;
            case R.id.select_ok: // 购买
                dismiss();
                if (mListener != null) {
                    mListener.onBuyClickListener();
                }
                break;
            case R.id.total_num_add: // 数量加
                totalNum.setText(String.valueOf(++selectNumber));
                if (null != mListener) {
                    mListener.onNumChangedListener(selectNumber);
                }
                break;
            case R.id.total_num_cut: // 数量减
                totalNum.setText(String.valueOf(selectNumber <= 1 ? 1 : --selectNumber));
                if (null != mListener) {
                    mListener.onNumChangedListener(selectNumber);
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //params.height = ScreenUtils.getScreenWidth((Activity) getContext());
        window.setAttributes(params);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnClickListener(v -> dismiss());
    }

    /**
     * 弹出对话框
     *
     * @param fragmentManager FragmentManager
     */
    public void showDialog(FragmentManager fragmentManager) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(getTag());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        show(ft, getTag());
    }

    /**
     * 对话框消失
     */
    @Override
    public void dismiss() {
        if (mIsDismiss) {
            return;
        }
        mIsDismiss = true;

        SpecsBottomDialog.super.dismiss();
    }

    public interface BottomDialogListener {
        void onCancel();

        void onBuyClickListener();

        void onNumChangedListener(int count);

        void OnSelectedClickListener(View view, int pIndex, int cIndex);
    }

    public void setOnBottomDialogListener(BottomDialogListener listener) {
        mListener = listener;
    }

    // 设置规格：单个或多个
    public void setSelectSpecify(final List<SpecsModel.SpecsBean> list) {
        if (null != list && list.size() > 0) {
            int size = list.size();
            FlowTagLayoutViewHolder viewHolder = null;
            View itemView = null;
            for (int i = 0; i < size; i++) {
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_specs_select_holder, buySelectLl, false);
                buySelectLl.addView(itemView);
                viewHolder = new FlowTagLayoutViewHolder(itemView);
                final SpecsModel.SpecsBean specsBean = list.get(i);
                viewHolder.categoryName.setText(specsBean.getName());
                SpecsFlowTagAdapter selectColorAdapter = new SpecsFlowTagAdapter<>(getContext());
                viewHolder.buySelectFtl.setAdapter(selectColorAdapter);
                viewHolder.buySelectFtl.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                viewHolder.buySelectFtl.setOnTagSelectListener((parent, selectedList) -> {
                    if (null != selectedList && selectedList.size() > 0) {
                        int size1 = selectedList.size();
                        for (int j = 0; j < size1; j++) {
                            Logger.i("TAG", "selectType = " + specsBean.getList().get(selectedList.get(j)).getSku_name());
                            selectedMap.put(specsBean.getId(), selectedList.get(j));
                            if (null != mListener) {
                                mListener.OnSelectedClickListener(parent, specsBean.getId(), selectedList.get(j));
                            }
                        }
                    } else {
                        selectedMap.remove(specsBean.getId());
                        if (null != mListener) {
                            mListener.OnSelectedClickListener(parent, specsBean.getId(), -1);
                        }
                    }
                });
                selectColorAdapter.onlyAddAll(specsBean.getList());
                listFlowTagLayout.add(viewHolder);
            }
        }
    }

    class FlowTagLayoutViewHolder {
        View itemView;
        TextView categoryName;
        FlowTagLayout buySelectFtl;

        public FlowTagLayoutViewHolder(View itemView) {
            this.itemView = itemView;
            this.categoryName = itemView.findViewById(R.id.buy_select_tv);
            this.buySelectFtl = itemView.findViewById(R.id.buy_select_ftl);
        }
    }
}

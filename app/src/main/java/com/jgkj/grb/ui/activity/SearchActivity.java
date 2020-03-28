package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.adapter.SearchHistoryFlowTagAdapter;
import com.jgkj.grb.view.flowtag.FlowTagLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity {

    public static void start(Context context, String index, int orderType) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("orderType", orderType);
        context.startActivity(intent);
    }

    @BindView(R.id.search_input_et)
    EditText mSearchInputEt;
    @BindView(R.id.search_action_cv)
    CardView mSearchActionCv;
    @BindView(R.id.search_action_tv)
    TextView mSearchActionTv;
    int mSearchAction = 0;

    @BindView(R.id.search_history)
    ConstraintLayout mSearchHistory;
    @BindView(R.id.search_delete_cv)
    CardView mSearchDeleteCv;
    @BindView(R.id.flowTagLayout)
    FlowTagLayout mFlowTagLayout;

    List<String> mHistoryItem = new ArrayList<>();
    SearchHistoryFlowTagAdapter mSearchHistoryAdapter;

    String mSearchIndex = "";
    int mOrderType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        if (intent.hasExtra("index")) {
            mSearchIndex = intent.getStringExtra("index");
        }
        if (intent.hasExtra("orderType")) {
            mOrderType = intent.getIntExtra("orderType", 0);
        }
        if (TextUtils.isEmpty(mSearchIndex)) {
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mSearchActionCv, mSearchDeleteCv);
        initSearchInput();
        initSearchAction();
        initSearchHistory();

        if (TextUtils.equals("index", mSearchIndex)) {
            mSearchHistory.setVisibility(View.VISIBLE);
            mSearchInputEt.setHint(R.string.search_input_hint_index);
        } else if (TextUtils.equals("order", mSearchIndex)) {
            mSearchHistory.setVisibility(View.GONE);
            mSearchInputEt.setHint(R.string.search_input_hint_order);
        } else if (TextUtils.equals("collect", mSearchIndex)) {
            mSearchHistory.setVisibility(View.GONE);
            mSearchInputEt.setHint(R.string.search_input_hint_collect);
        }
    }

    private void initSearchAction() {
        if (0 == mSearchAction) {
            mSearchActionTv.setText(R.string.search_cancel);
        } else if (1 == mSearchAction) {
            mSearchActionTv.setText(R.string.search_sure);
        }
    }

    private void initSearchInput() {
        mSearchInputEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // do something
                if (event != null && event.getAction() == KeyEvent.ACTION_UP) {
                    gotoSearch(mSearchInputEt.getText().toString());
                }
                return true;
            }
            return false;
        });
        mSearchInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())
                        && mSearchAction != 0) {
                    mSearchAction = 0;
                    initSearchAction();
                } else if (mSearchAction != 1) {
                    mSearchAction = 1;
                    initSearchAction();
                }
            }
        });
    }

    private void initSearchHistory() {
        String searchHistory = sharedPreferencesConfig.getSharedPreference("search_history", "").toString();
        if (!TextUtils.isEmpty(searchHistory)) {
            mHistoryItem = new Gson().fromJson(searchHistory, new TypeToken<List<String>>() {
            }.getType());
        }

        mSearchHistoryAdapter = new SearchHistoryFlowTagAdapter(this);
        mFlowTagLayout.setAdapter(mSearchHistoryAdapter);
        mFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mFlowTagLayout.setOnTagSelectListener((parent, selectedList) -> {
            if (null != selectedList && selectedList.size() > 0) {
                int size1 = selectedList.size();
                for (int i = 0; i < size1; i++) {
                    gotoSearch(mHistoryItem.get(selectedList.get(i)));
                }
            } else {
            }
        });
        mSearchHistoryAdapter.clearAndAddAll(mHistoryItem);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.search_action_cv:
                if (0 == mSearchAction)
                    onBackPressed();
                else if (1 == mSearchAction)
                    gotoSearch(mSearchInputEt.getText().toString());
                break;
            case R.id.search_delete_cv:
                mHistoryItem.clear();
                sharedPreferencesConfig.putApply("search_history", new Gson().toJson(mHistoryItem));
                mSearchHistoryAdapter.clearAndAddAll(mHistoryItem);
                break;
            default:
                break;
        }
    }

    private void gotoSearch(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            toastShow(R.string.search_input_empty);
            return;
        }
        hideInputSoft();
        switch (mSearchIndex) {
            case "index":
                updateHistory(searchKey);
                SearchIndexResultActivity.start(mActivity, searchKey);
                break;
            case "order":
                SearchOrderResultActivity.start(mActivity, searchKey, mOrderType);
                break;
            case "collect":
                SearchCollectResultActivity.start(mActivity, searchKey);
                break;
            default:
                break;
        }
        onBackPressed();
    }

    private void updateHistory(String searchKey) {
        // 保存最新的 10 条搜索记录
        List<String> list = new ArrayList<>(mHistoryItem);
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equalsIgnoreCase(searchKey)) {
                iterator.remove();
            }
        }
        mHistoryItem.clear();
        mHistoryItem.add(0, searchKey);
        mHistoryItem.addAll(list);
        if (mHistoryItem.size() >= 10) {
            list.clear();
            list.addAll(mHistoryItem.subList(0, 10));
            mHistoryItem.clear();
            mHistoryItem.addAll(list);
        }
        sharedPreferencesConfig.putApply("search_history", new Gson().toJson(mHistoryItem));
        mSearchHistoryAdapter.clearAndAddAll(mHistoryItem);
    }
}

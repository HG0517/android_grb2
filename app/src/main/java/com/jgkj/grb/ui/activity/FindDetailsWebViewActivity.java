package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.FindDetailsCommentAdapter;
import com.jgkj.grb.ui.mvp.find.FindDetailsModel;
import com.jgkj.grb.view.webview.WebViewTool;
import com.jgkj.utils.token.GetTokenUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;

/**
 * 发现：列表数据详情
 */
public class FindDetailsWebViewActivity extends BaseActivity {

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, FindDetailsWebViewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.find_details_title)
    TextView mFindDetailsTitle;
    @BindView(R.id.find_detail_author)
    TextView mFindDetailsAuthor;
    @BindView(R.id.find_detail_time)
    TextView mFindDetailsTime;
    @BindView(R.id.find_detail_read)
    TextView mFindDetailsRead;
    WebView mWebView;
    @BindView(R.id.content)
    FrameLayout mContentPanel;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.post_comment_input)
    EditText mPostCommentInput;
    @BindView(R.id.post_comment_cv)
    CardView mPostCommentCv;

    FindDetailsCommentAdapter mAdapter;
    FindDetailsModel mFindDetailsModel;

    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_details);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            mId = intent.getStringExtra("id");
        }
        if (TextUtils.isEmpty(mId)) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        Toolbar toolbar = initToolBar("");

        RxView.setOnClickListeners(this, mPostCommentCv);

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_CONSULTATION_DETAILNEWS);
        addSubscription(apiStores().consultationDetailNews(token, mId), new ApiCallback<FindDetailsModel>() {
            @Override
            public void onSuccess(FindDetailsModel model) {
                mFindDetailsModel = model;
                if (model.getCode() == 1) {
                    showDetail();
                    initRecyclerView();
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

    private void showDetail() {
        if (null != mFindDetailsModel.getData().getNews() && mFindDetailsModel.getData().getNews().size() > 0) {
            mFindDetailsTitle.setText(mFindDetailsModel.getData().getNews().get(0).getTitle());
            mFindDetailsAuthor.setText(String.format(getString(R.string.find_details_author), mFindDetailsModel.getData().getNews().get(0).getAuthor()));
            mFindDetailsTime.setText(mFindDetailsModel.getData().getNews().get(0).getAdd_time());
            mFindDetailsRead.setText(String.format(Locale.getDefault(), getString(R.string.find_details_read), mFindDetailsModel.getData().getNews().get(0).getRead()));

            initWebView(mFindDetailsModel.getData().getNews().get(0).getContent());
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FindDetailsCommentAdapter(this, mFindDetailsModel.getData().getAdvisory());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnActionClickListener(new FindDetailsCommentAdapter.OnActionClickListener() {
            @Override
            public void onClickGroup(int position) {
                if (isLogin())
                    userThumbP(mFindDetailsModel.getData().getAdvisory().get(position));
            }

            @Override
            public void onClickChild(int position) {
                if (isLogin())
                    userThumbC(mFindDetailsModel.getData().getAdvisory().get(position).getSecend());
            }
        });
    }

    private void initWebView(String content) {
        mWebView = new WebView(this);
        mContentPanel.removeAllViews();
        mContentPanel.addView(mWebView);
        WebViewTool.webSettings(this, mWebView);
        mWebView.loadDataWithBaseURL(null, content
                        .replaceAll("<img", "<img style=\"width:100%;height:auto;\"")
                        .replaceAll("<video", "<video style=\"width:100%;height:auto;\""),
                "text/html", "UTF-8", null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.setDownloadListener((s0, s1, s2, s3, l) -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(s0));
            startActivity(intent);
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != mWebView && mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.post_comment_cv:
                if (isLogin()) {
                    if (TextUtils.isEmpty(mPostCommentInput.getText().toString().trim())) {
                        toastShow("请输入内容...");
                        return;
                    }
                    userAddComment(mId, mPostCommentInput.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加资讯评论
     */
    private void userAddComment(String mId, String content) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_ADDCOMMENT);
        addSubscription(apiStores().userAddComment(token, mId, content), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", ""));
                        mPostCommentInput.setText("");
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

    /**
     * 资讯评论：取消/点赞
     */
    private void userThumbP(FindDetailsModel.DataBean.AdvisoryBean advisoryBean) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_THUMB);
        addSubscription(apiStores().userThumb(token, advisoryBean.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        String userId = getUserId();
                        if (advisoryBean.itsMe(userId)) {
                            advisoryBean.getUsers().remove(userId);
                            advisoryBean.setThumbs(advisoryBean.getThumbs() - 1);
                            toastShow("取消点赞成功");
                        } else {
                            advisoryBean.getUsers().add(userId);
                            advisoryBean.setThumbs(advisoryBean.getThumbs() + 1);
                            toastShow("点赞成功");
                        }
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

    /**
     * 资讯评论平台回复：取消/点赞
     */
    private void userThumbC(FindDetailsModel.DataBean.AdvisoryBean advisoryBean) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_THUMB);
        addSubscription(apiStores().userThumb(token, advisoryBean.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        String userId = getUserId();
                        if (advisoryBean.itsMe(userId)) {
                            advisoryBean.getUsers().remove(userId);
                            advisoryBean.setThumbs(advisoryBean.getThumbs() - 1);
                            toastShow("取消点赞成功");
                        } else {
                            advisoryBean.getUsers().add(userId);
                            advisoryBean.setThumbs(advisoryBean.getThumbs() + 1);
                            toastShow("点赞成功");
                        }
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

    // 登录状态变化
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(RefreshUserInfo event) {
        if (null != mAdapter)
            mAdapter.notifyDataSetChanged();
    }

    protected String getUserId() {
        try {
            // is_realname  实名情况：0:未认证  1认证中 2已认证
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            return user.optString("id", null);
        } catch (JSONException e) {
            return null;
        }
    }

}

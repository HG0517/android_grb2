package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.finddreams.languagelib.LanguageType;
import com.finddreams.languagelib.MultiLanguageUtil;
import com.finddreams.languagelib.RefreshType;
import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 多语言设置
 * Created by jugekeji on 2019/8/10.
 */

public class SettingLanguageDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // 根布局方式左右边距 dp
    private int mSideMargin = 48; // view 布局方式左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private FrameLayout mLanguageAuto;
    private ImageView mLanguageAutoIv;
    private FrameLayout mLanguageSimplifiedChinese;
    private ImageView mLanguageSimplifiedChineseIv;
    private FrameLayout mLanguageEnglish;
    private ImageView mLanguageEnglishIv;
    private FrameLayout mLanguageKorean;
    private ImageView mLanguageKoreanIv;

    private OnLanguageChangeListener mOnLanguageChangeListener;

    private int savedLanguageType; // 保存的正在使用的语言
    private int mRefreshType = RefreshType.REFRESH_ACTIVITY_CLEAR; // 切换语言刷新界面模式

    public void setOnLanguageChangeListener(OnLanguageChangeListener mOnLanguageChangeListener) {
        this.mOnLanguageChangeListener = mOnLanguageChangeListener;
    }

    public int getRefreshType() {
        return mRefreshType;
    }

    public void setRefreshType(int mRefreshType) {
        this.mRefreshType = mRefreshType;
    }

    public SettingLanguageDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public SettingLanguageDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public SettingLanguageDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public SettingLanguageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public SettingLanguageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public SettingLanguageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_setting_language, null);
        Window window = getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(dp2px(mContext, mDecorViewPadding), 0, dp2px(mContext, mDecorViewPadding), 0);
        window.setContentView(contentView);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = 0 == mSideMargin ? WindowManager.LayoutParams.MATCH_PARENT : ScreenUtils.getScreenWidth((Activity) mContext) - dp2px(mContext, mSideMargin) * 2;
        layoutParams.height = 0 == height ? WindowManager.LayoutParams.WRAP_CONTENT : dp2px(mContext, height);
        window.setAttributes(layoutParams);

        initView(contentView);
        savedLanguageType = MultiLanguageUtil.getInstance().getLanguageType();
        initLanguageType();
    }

    private void initView(View contentView) {
        mDialogTitle = contentView.findViewById(R.id.dialog_title);
        mLanguageAuto = contentView.findViewById(R.id.language_auto);
        mLanguageAutoIv = contentView.findViewById(R.id.language_auto_iv);
        mLanguageSimplifiedChinese = contentView.findViewById(R.id.language_simplified_chinese);
        mLanguageSimplifiedChineseIv = contentView.findViewById(R.id.language_simplified_chinese_iv);
        mLanguageEnglish = contentView.findViewById(R.id.language_english);
        mLanguageEnglishIv = contentView.findViewById(R.id.language_english_iv);
        mLanguageKorean = contentView.findViewById(R.id.language_korean);
        mLanguageKoreanIv = contentView.findViewById(R.id.language_korean_iv);

        RxView.setOnClickListeners(this, mLanguageAuto, mLanguageSimplifiedChinese, mLanguageEnglish, mLanguageKorean);
    }

    private void initLanguageType() {
        if (savedLanguageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            setFollowSytemVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_KOREAN) {
            setKoreanVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_EN) {
            setEnglishVisible();
        } else if (savedLanguageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            setSimplifiedVisible();
        } else {
            setFollowSytemVisible();
        }
    }

    // 简体中文
    private void setSimplifiedVisible() {
        mLanguageAutoIv.setVisibility(View.GONE);
        mLanguageSimplifiedChineseIv.setVisibility(View.VISIBLE);
        mLanguageEnglishIv.setVisibility(View.GONE);
        mLanguageKoreanIv.setVisibility(View.GONE);
    }

    // 英语
    private void setEnglishVisible() {
        mLanguageAutoIv.setVisibility(View.GONE);
        mLanguageSimplifiedChineseIv.setVisibility(View.GONE);
        mLanguageEnglishIv.setVisibility(View.VISIBLE);
        mLanguageKoreanIv.setVisibility(View.GONE);
    }

    // 韩语
    private void setKoreanVisible() {
        mLanguageAutoIv.setVisibility(View.GONE);
        mLanguageSimplifiedChineseIv.setVisibility(View.GONE);
        mLanguageEnglishIv.setVisibility(View.GONE);
        mLanguageKoreanIv.setVisibility(View.VISIBLE);
    }

    // 跟随系统
    private void setFollowSytemVisible() {
        mLanguageAutoIv.setVisibility(View.VISIBLE);
        mLanguageSimplifiedChineseIv.setVisibility(View.GONE);
        mLanguageEnglishIv.setVisibility(View.GONE);
        mLanguageKoreanIv.setVisibility(View.GONE);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        int selectedLanguage = LanguageType.LANGUAGE_FOLLOW_SYSTEM;
        switch (v.getId()) {
            case R.id.language_auto:
                setFollowSytemVisible();
                selectedLanguage = LanguageType.LANGUAGE_FOLLOW_SYSTEM;
                break;
            case R.id.language_simplified_chinese:
                setSimplifiedVisible();
                selectedLanguage = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                break;
            case R.id.language_english:
                setEnglishVisible();
                selectedLanguage = LanguageType.LANGUAGE_EN;
                break;
            case R.id.language_korean:
                setKoreanVisible();
                selectedLanguage = LanguageType.LANGUAGE_KOREAN;
                break;
            default:
                break;
        }
        if (selectedLanguage != MultiLanguageUtil.getInstance().getLanguageType()) {
            MultiLanguageUtil.getInstance().updateLanguage(selectedLanguage, mRefreshType);
            if (null != mOnLanguageChangeListener) {
                mOnLanguageChangeListener.onLanguageChange();
            }
        }
        cancel();
    }

    public interface OnLanguageChangeListener {
        void onLanguageChange();
    }
}

package com.jgkj.grb.view.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jgkj.grb.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 说明：自定义时间选择器：适合简短选择，年月
 */
public class DatePickerSingleMonth implements View.OnClickListener, PickerView.OnSelectListener {

    private Context mContext;
    private Callback mCallback;
    private Dialog mPickerDialog;

    private boolean mCanDialogShow;
    private boolean mCanShowPreciseTime;
    private int mScrollUnits = SCROLL_UNIT_HOUR + SCROLL_UNIT_MINUTE;

    private DecimalFormat mDecimalFormat = new DecimalFormat("00");

    private Calendar mBeginTimeFrom, mEndTimeFrom, mSelectedTimeFrom;
    private PickerView mDpvYearFrom, mDpvMonthFrom, mDpvDayFrom, mDpvHourFrom, mDpvMinuteFrom;
    private TextView mTvMonthUnitFrom, mTvDayUnitFrom, mTvHourUnitFrom, mTvMinuteUnitFrom;

    private int mBeginYearFrom, mBeginMonthFrom, mBeginDayFrom, mBeginHourFrom, mBeginMinuteFrom,
            mEndYearFrom, mEndMonthFrom, mEndDayFrom, mEndHourFrom, mEndMinuteFrom;
    private List<String> mYearUnitsFrom = new ArrayList<>(), mMonthUnitsFrom = new ArrayList<>(), mDayUnitsFrom = new ArrayList<>(),
            mHourUnitsFrom = new ArrayList<>(), mMinuteUnitsFrom = new ArrayList<>();

    /**
     * 时间单位：时、分
     */
    private static final int SCROLL_UNIT_HOUR = 0b1;
    private static final int SCROLL_UNIT_MINUTE = 0b10;

    /**
     * 时间单位的最大显示值
     */
    private static final int MAX_MINUTE_UNIT = 59;
    private static final int MAX_HOUR_UNIT = 23;
    private static final int MAX_MONTH_UNIT = 12;

    /**
     * 级联滚动延迟时间
     */
    private static final long LINKAGE_DELAY_DEFAULT = 100L;

    /**
     * 时间选择结果回调接口
     */
    public interface Callback {
        void onTimeSelected(long timestamp);
    }

    /**
     * 通过日期字符串初始换时间选择器
     *
     * @param context      Activity Context
     * @param callback     选择结果回调
     * @param beginDateStr 日期字符串，格式为 yyyy-MM-dd HH:mm
     * @param endDateStr   日期字符串，格式为 yyyy-MM-dd HH:mm
     */
    public DatePickerSingleMonth(Context context, Callback callback, String beginDateStr, String endDateStr) {
        this(context, callback,
                DateFormatUtils.str2Long(beginDateStr, true), DateFormatUtils.str2Long(endDateStr, true));
    }

    /**
     * 通过时间戳初始换时间选择器，毫秒级别
     *
     * @param context        Activity Context
     * @param callback       选择结果回调
     * @param beginTimestamp 毫秒级时间戳
     * @param endTimestamp   毫秒级时间戳
     */
    public DatePickerSingleMonth(Context context, Callback callback, long beginTimestamp, long endTimestamp) {
        if (context == null || callback == null || beginTimestamp <= 0 || beginTimestamp >= endTimestamp) {
            mCanDialogShow = false;
            return;
        }

        mContext = context;
        mCallback = callback;
        mBeginTimeFrom = Calendar.getInstance();
        mBeginTimeFrom.setTimeInMillis(beginTimestamp);
        mEndTimeFrom = Calendar.getInstance();
        mEndTimeFrom.setTimeInMillis(endTimestamp);
        mSelectedTimeFrom = Calendar.getInstance();

        initView();
        initData();
        mCanDialogShow = true;
    }

    private void initView() {
        mPickerDialog = new Dialog(mContext/*, R.style.DatePickerDialog*/);
        Window window = mPickerDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.BOTTOM);
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setContentView(R.layout.layout_dialog_date_picker_single_month);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }

        mPickerDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        mPickerDialog.findViewById(R.id.tv_confirm).setOnClickListener(this);

        mDpvYearFrom = mPickerDialog.findViewById(R.id.dpv_year_from);
        mDpvYearFrom.setOnSelectListener(this);
        mDpvMonthFrom = mPickerDialog.findViewById(R.id.dpv_month_from);
        mDpvMonthFrom.setOnSelectListener(this);
        mDpvDayFrom = mPickerDialog.findViewById(R.id.dpv_day_from);
        mDpvDayFrom.setOnSelectListener(this);
        mDpvHourFrom = mPickerDialog.findViewById(R.id.dpv_hour_from);
        mDpvHourFrom.setOnSelectListener(this);
        mDpvMinuteFrom = mPickerDialog.findViewById(R.id.dpv_minute_from);
        mDpvMinuteFrom.setOnSelectListener(this);
        mTvMonthUnitFrom = mPickerDialog.findViewById(R.id.tv_month_unit_from);
        mTvDayUnitFrom = mPickerDialog.findViewById(R.id.tv_day_unit_from);
        mTvHourUnitFrom = mPickerDialog.findViewById(R.id.tv_hour_unit_from);
        mTvMinuteUnitFrom = mPickerDialog.findViewById(R.id.tv_minute_unit_from);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.tv_confirm:
                if (mCallback != null) {
                    mCallback.onTimeSelected(mSelectedTimeFrom.getTimeInMillis());
                }
                break;
        }

        if (mPickerDialog != null && mPickerDialog.isShowing()) {
            mPickerDialog.cancel();
        }
    }

    @Override
    public void onSelect(View view, String selected) {
        if (view == null || TextUtils.isEmpty(selected)) return;

        int timeUnit;
        try {
            timeUnit = Integer.parseInt(selected);
        } catch (Throwable ignored) {
            return;
        }

        switch (view.getId()) {
            case R.id.dpv_year_from:
                mSelectedTimeFrom.set(Calendar.YEAR, timeUnit);
                linkageMonthUnitFrom(true, LINKAGE_DELAY_DEFAULT);
                break;
            case R.id.dpv_month_from:
                // 防止类似 2018/12/31 滚动到11月时因溢出变成 2018/12/01
                int lastSelectedMonthFrom = mSelectedTimeFrom.get(Calendar.MONTH) + 1;
                mSelectedTimeFrom.add(Calendar.MONTH, timeUnit - lastSelectedMonthFrom);
                linkageDayUnitFrom(true, LINKAGE_DELAY_DEFAULT);
                break;
            case R.id.dpv_day_from:
                mSelectedTimeFrom.set(Calendar.DAY_OF_MONTH, timeUnit);
                linkageHourUnitFrom(true, LINKAGE_DELAY_DEFAULT);
                break;
            case R.id.dpv_hour_from:
                mSelectedTimeFrom.set(Calendar.HOUR_OF_DAY, timeUnit);
                linkageMinuteUnitFrom(true);
                break;
            case R.id.dpv_minute_from:
                mSelectedTimeFrom.set(Calendar.MINUTE, timeUnit);
                break;
        }
    }

    private void initData() {
        mSelectedTimeFrom.setTimeInMillis(mBeginTimeFrom.getTimeInMillis());

        mBeginYearFrom = mBeginTimeFrom.get(Calendar.YEAR);
        // Calendar.MONTH 值为 0-11
        mBeginMonthFrom = mBeginTimeFrom.get(Calendar.MONTH) + 1;
        mBeginDayFrom = mBeginTimeFrom.get(Calendar.DAY_OF_MONTH);
        mBeginHourFrom = mBeginTimeFrom.get(Calendar.HOUR_OF_DAY);
        mBeginMinuteFrom = mBeginTimeFrom.get(Calendar.MINUTE);

        mEndYearFrom = mEndTimeFrom.get(Calendar.YEAR);
        mEndMonthFrom = mEndTimeFrom.get(Calendar.MONTH) + 1;
        mEndDayFrom = mEndTimeFrom.get(Calendar.DAY_OF_MONTH);
        mEndHourFrom = mEndTimeFrom.get(Calendar.HOUR_OF_DAY);
        mEndMinuteFrom = mEndTimeFrom.get(Calendar.MINUTE);

        boolean canSpanYearFrom = mBeginYearFrom != mEndYearFrom;
        boolean canSpanMonFrom = !canSpanYearFrom && mBeginMonthFrom != mEndMonthFrom;
        boolean canSpanDayFrom = !canSpanMonFrom && mBeginDayFrom != mEndDayFrom;
        boolean canSpanHourFrom = !canSpanDayFrom && mBeginHourFrom != mEndHourFrom;
        boolean canSpanMinuteFrom = !canSpanHourFrom && mBeginMinuteFrom != mEndMinuteFrom;
        if (canSpanYearFrom) {
            initDateUnitsFrom(MAX_MONTH_UNIT, mBeginTimeFrom.getActualMaximum(Calendar.DAY_OF_MONTH), MAX_HOUR_UNIT, MAX_MINUTE_UNIT);
        } else if (canSpanMonFrom) {
            initDateUnitsFrom(mEndMonthFrom, mBeginTimeFrom.getActualMaximum(Calendar.DAY_OF_MONTH), MAX_HOUR_UNIT, MAX_MINUTE_UNIT);
        } else if (canSpanDayFrom) {
            initDateUnitsFrom(mEndMonthFrom, mEndDayFrom, MAX_HOUR_UNIT, MAX_MINUTE_UNIT);
        } else if (canSpanHourFrom) {
            initDateUnitsFrom(mEndMonthFrom, mEndDayFrom, mEndHourFrom, MAX_MINUTE_UNIT);
        } else if (canSpanMinuteFrom) {
            initDateUnitsFrom(mEndMonthFrom, mEndDayFrom, mEndHourFrom, mEndMinuteFrom);
        }
    }

    private void initDateUnitsFrom(int endMonth, int endDay, int endHour, int endMinute) {
        for (int i = mBeginYearFrom; i <= mEndYearFrom; i++) {
            mYearUnitsFrom.add(String.valueOf(i));
        }
        for (int i = mBeginMonthFrom; i <= endMonth; i++) {
            mMonthUnitsFrom.add(mDecimalFormat.format(i));
        }
        for (int i = mBeginDayFrom; i <= endDay; i++) {
            mDayUnitsFrom.add(mDecimalFormat.format(i));
        }
        if ((mScrollUnits & SCROLL_UNIT_HOUR) != SCROLL_UNIT_HOUR) {
            mHourUnitsFrom.add(mDecimalFormat.format(mBeginHourFrom));
        } else {
            for (int i = mBeginHourFrom; i <= endHour; i++) {
                mHourUnitsFrom.add(mDecimalFormat.format(i));
            }
        }
        if ((mScrollUnits & SCROLL_UNIT_MINUTE) != SCROLL_UNIT_MINUTE) {
            mMinuteUnitsFrom.add(mDecimalFormat.format(mBeginMinuteFrom));
        } else {
            for (int i = mBeginMinuteFrom; i <= endMinute; i++) {
                mMinuteUnitsFrom.add(mDecimalFormat.format(i));
            }
        }
        mDpvYearFrom.setDataList(mYearUnitsFrom);
        mDpvYearFrom.setSelected(0);
        mDpvMonthFrom.setDataList(mMonthUnitsFrom);
        mDpvMonthFrom.setSelected(0);
        mDpvDayFrom.setDataList(mDayUnitsFrom);
        mDpvDayFrom.setSelected(0);
        mDpvHourFrom.setDataList(mHourUnitsFrom);
        mDpvHourFrom.setSelected(0);
        mDpvMinuteFrom.setDataList(mMinuteUnitsFrom);
        mDpvMinuteFrom.setSelected(0);

        setCanScroll();
    }

    private void setCanScroll() {
        mDpvYearFrom.setCanScroll(mYearUnitsFrom.size() > 1);
        mDpvMonthFrom.setCanScroll(mMonthUnitsFrom.size() > 1);
        mDpvDayFrom.setCanScroll(mDayUnitsFrom.size() > 1);
        mDpvHourFrom.setCanScroll(mHourUnitsFrom.size() > 1 && (mScrollUnits & SCROLL_UNIT_HOUR) == SCROLL_UNIT_HOUR);
        mDpvMinuteFrom.setCanScroll(mMinuteUnitsFrom.size() > 1 && (mScrollUnits & SCROLL_UNIT_MINUTE) == SCROLL_UNIT_MINUTE);
    }

    /**
     * 联动“月”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageMonthUnitFrom(final boolean showAnim, final long delay) {
        int minMonth;
        int maxMonth;
        int selectedYear = mSelectedTimeFrom.get(Calendar.YEAR);
        if (mBeginYearFrom == mEndYearFrom) {
            minMonth = mBeginMonthFrom;
            maxMonth = mEndMonthFrom;
        } else if (selectedYear == mBeginYearFrom) {
            minMonth = mBeginMonthFrom;
            maxMonth = MAX_MONTH_UNIT;
        } else if (selectedYear == mEndYearFrom) {
            minMonth = 1;
            maxMonth = mEndMonthFrom;
        } else {
            minMonth = 1;
            maxMonth = MAX_MONTH_UNIT;
        }

        // 重新初始化时间单元容器
        mMonthUnitsFrom.clear();
        for (int i = minMonth; i <= maxMonth; i++) {
            mMonthUnitsFrom.add(mDecimalFormat.format(i));
        }
        mDpvMonthFrom.setDataList(mMonthUnitsFrom);

        // 确保联动时不会溢出或改变关联选中值
        int selectedMonth = getValueInRange(mSelectedTimeFrom.get(Calendar.MONTH) + 1, minMonth, maxMonth);
        mSelectedTimeFrom.set(Calendar.MONTH, selectedMonth - 1);
        mDpvMonthFrom.setSelected(selectedMonth - minMonth);
        if (showAnim) {
            mDpvMonthFrom.startAnim();
        }

        // 联动“日”变化
        mDpvMonthFrom.postDelayed(new Runnable() {
            @Override
            public void run() {
                linkageDayUnitFrom(showAnim, delay);
            }
        }, delay);
    }

    /**
     * 联动“日”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageDayUnitFrom(final boolean showAnim, final long delay) {
        int minDay;
        int maxDay;
        int selectedYear = mSelectedTimeFrom.get(Calendar.YEAR);
        int selectedMonth = mSelectedTimeFrom.get(Calendar.MONTH) + 1;
        if (mBeginYearFrom == mEndYearFrom && mBeginMonthFrom == mEndMonthFrom) {
            minDay = mBeginDayFrom;
            maxDay = mEndDayFrom;
        } else if (selectedYear == mBeginYearFrom && selectedMonth == mBeginMonthFrom) {
            minDay = mBeginDayFrom;
            maxDay = mSelectedTimeFrom.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (selectedYear == mEndYearFrom && selectedMonth == mEndMonthFrom) {
            minDay = 1;
            maxDay = mEndDayFrom;
        } else {
            minDay = 1;
            maxDay = mSelectedTimeFrom.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        mDayUnitsFrom.clear();
        for (int i = minDay; i <= maxDay; i++) {
            mDayUnitsFrom.add(mDecimalFormat.format(i));
        }
        mDpvDayFrom.setDataList(mDayUnitsFrom);

        int selectedDay = getValueInRange(mSelectedTimeFrom.get(Calendar.DAY_OF_MONTH), minDay, maxDay);
        mSelectedTimeFrom.set(Calendar.DAY_OF_MONTH, selectedDay);
        mDpvDayFrom.setSelected(selectedDay - minDay);
        if (showAnim) {
            mDpvDayFrom.startAnim();
        }

        mDpvDayFrom.postDelayed(new Runnable() {
            @Override
            public void run() {
                linkageHourUnitFrom(showAnim, delay);
            }
        }, delay);
    }

    /**
     * 联动“时”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageHourUnitFrom(final boolean showAnim, final long delay) {
        if ((mScrollUnits & SCROLL_UNIT_HOUR) == SCROLL_UNIT_HOUR) {
            int minHour;
            int maxHour;
            int selectedYear = mSelectedTimeFrom.get(Calendar.YEAR);
            int selectedMonth = mSelectedTimeFrom.get(Calendar.MONTH) + 1;
            int selectedDay = mSelectedTimeFrom.get(Calendar.DAY_OF_MONTH);
            if (mBeginYearFrom == mEndYearFrom && mBeginMonthFrom == mEndMonthFrom && mBeginDayFrom == mEndDayFrom) {
                minHour = mBeginHourFrom;
                maxHour = mEndHourFrom;
            } else if (selectedYear == mBeginYearFrom && selectedMonth == mBeginMonthFrom && selectedDay == mBeginDayFrom) {
                minHour = mBeginHourFrom;
                maxHour = MAX_HOUR_UNIT;
            } else if (selectedYear == mEndYearFrom && selectedMonth == mEndMonthFrom && selectedDay == mEndDayFrom) {
                minHour = 0;
                maxHour = mEndHourFrom;
            } else {
                minHour = 0;
                maxHour = MAX_HOUR_UNIT;
            }

            mHourUnitsFrom.clear();
            for (int i = minHour; i <= maxHour; i++) {
                mHourUnitsFrom.add(mDecimalFormat.format(i));
            }
            mDpvHourFrom.setDataList(mHourUnitsFrom);

            int selectedHour = getValueInRange(mSelectedTimeFrom.get(Calendar.HOUR_OF_DAY), minHour, maxHour);
            mSelectedTimeFrom.set(Calendar.HOUR_OF_DAY, selectedHour);
            mDpvHourFrom.setSelected(selectedHour - minHour);
            if (showAnim) {
                mDpvHourFrom.startAnim();
            }
        }

        mDpvHourFrom.postDelayed(new Runnable() {
            @Override
            public void run() {
                linkageMinuteUnitFrom(showAnim);
            }
        }, delay);
    }

    /**
     * 联动“分”变化
     *
     * @param showAnim 是否展示滚动动画
     */
    private void linkageMinuteUnitFrom(final boolean showAnim) {
        if ((mScrollUnits & SCROLL_UNIT_MINUTE) == SCROLL_UNIT_MINUTE) {
            int minMinute;
            int maxMinute;
            int selectedYear = mSelectedTimeFrom.get(Calendar.YEAR);
            int selectedMonth = mSelectedTimeFrom.get(Calendar.MONTH) + 1;
            int selectedDay = mSelectedTimeFrom.get(Calendar.DAY_OF_MONTH);
            int selectedHour = mSelectedTimeFrom.get(Calendar.HOUR_OF_DAY);
            if (mBeginYearFrom == mEndYearFrom && mBeginMonthFrom == mEndMonthFrom && mBeginDayFrom == mEndDayFrom && mBeginHourFrom == mEndHourFrom) {
                minMinute = mBeginMinuteFrom;
                maxMinute = mEndMinuteFrom;
            } else if (selectedYear == mBeginYearFrom && selectedMonth == mBeginMonthFrom && selectedDay == mBeginDayFrom && selectedHour == mBeginHourFrom) {
                minMinute = mBeginMinuteFrom;
                maxMinute = MAX_MINUTE_UNIT;
            } else if (selectedYear == mEndYearFrom && selectedMonth == mEndMonthFrom && selectedDay == mEndDayFrom && selectedHour == mEndHourFrom) {
                minMinute = 0;
                maxMinute = mEndMinuteFrom;
            } else {
                minMinute = 0;
                maxMinute = MAX_MINUTE_UNIT;
            }

            mMinuteUnitsFrom.clear();
            for (int i = minMinute; i <= maxMinute; i++) {
                mMinuteUnitsFrom.add(mDecimalFormat.format(i));
            }
            mDpvMinuteFrom.setDataList(mMinuteUnitsFrom);

            int selectedMinute = getValueInRange(mSelectedTimeFrom.get(Calendar.MINUTE), minMinute, maxMinute);
            mSelectedTimeFrom.set(Calendar.MINUTE, selectedMinute);
            mDpvMinuteFrom.setSelected(selectedMinute - minMinute);
            if (showAnim) {
                mDpvMinuteFrom.startAnim();
            }
        }

        setCanScroll();
    }

    private int getValueInRange(int value, int minValue, int maxValue) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        } else {
            return value;
        }
    }

    /**
     * 展示时间选择器
     *
     * @param dateStr 日期字符串，格式为 yyyy-MM-dd 或 yyyy-MM-dd HH:mm
     */
    public void show(String dateStr) {
        if (!canShow() || TextUtils.isEmpty(dateStr)) return;

        // 弹窗时，考虑用户体验，不展示滚动动画
        if (setSelectedTime(dateStr, false)) {
            mPickerDialog.show();
        }
    }

    private boolean canShow() {
        return mCanDialogShow && mPickerDialog != null;
    }

    /**
     * 设置日期选择器的选中时间
     *
     * @param dateStrFrom 日期字符串
     * @param showAnim    是否展示动画
     * @return 是否设置成功
     */
    public boolean setSelectedTime(String dateStrFrom, boolean showAnim) {
        return canShow() && !TextUtils.isEmpty(dateStrFrom)
                && setSelectedTime(DateFormatUtils.str2Long(dateStrFrom, mCanShowPreciseTime), showAnim);
    }

    /**
     * 展示时间选择器
     *
     * @param timeFrom 时间戳，毫秒级别
     */
    public void show(long timeFrom) {
        if (!canShow()) return;

        if (setSelectedTime(timeFrom, false)) {
            mPickerDialog.show();
        }
    }

    /**
     * 设置日期选择器的选中时间
     *
     * @param timeFrom 毫秒级时间戳
     * @param showAnim 是否展示动画
     * @return 是否设置成功
     */
    public boolean setSelectedTime(long timeFrom, boolean showAnim) {
        if (!canShow()) return false;

        if (timeFrom < mBeginTimeFrom.getTimeInMillis()) {
            timeFrom = mBeginTimeFrom.getTimeInMillis();
        } else if (timeFrom > mEndTimeFrom.getTimeInMillis()) {
            timeFrom = mEndTimeFrom.getTimeInMillis();
        }
        mSelectedTimeFrom.setTimeInMillis(timeFrom);

        mYearUnitsFrom.clear();
        for (int i = mBeginYearFrom; i <= mEndYearFrom; i++) {
            mYearUnitsFrom.add(String.valueOf(i));
        }
        mDpvYearFrom.setDataList(mYearUnitsFrom);
        mDpvYearFrom.setSelected(mSelectedTimeFrom.get(Calendar.YEAR) - mBeginYearFrom);
        linkageMonthUnitFrom(showAnim, showAnim ? LINKAGE_DELAY_DEFAULT : 0);
        return true;
    }

    /**
     * 设置是否允许点击屏幕或物理返回键关闭
     */
    public void setCancelable(boolean cancelable) {
        if (!canShow()) return;

        mPickerDialog.setCancelable(cancelable);
    }

    /**
     * 设置日期控件是否显示时和分
     */
    public void setCanShowPreciseTime(boolean canShowPreciseTime) {
        if (!canShow()) return;

        if (canShowPreciseTime) {
            initScrollUnit();
            mDpvHourFrom.setVisibility(View.VISIBLE);
            mTvHourUnitFrom.setVisibility(View.VISIBLE);
            mDpvMinuteFrom.setVisibility(View.VISIBLE);
            mTvMinuteUnitFrom.setVisibility(View.VISIBLE);
        } else {
            initScrollUnit(SCROLL_UNIT_HOUR, SCROLL_UNIT_MINUTE);
            mDpvHourFrom.setVisibility(View.GONE);
            mTvHourUnitFrom.setVisibility(View.GONE);
            mDpvMinuteFrom.setVisibility(View.GONE);
            mTvMinuteUnitFrom.setVisibility(View.GONE);
        }
        mCanShowPreciseTime = canShowPreciseTime;
    }

    /**
     * 设置日期控件是否显示日
     */
    public void setCanShowDay(boolean canShowDay) {
        if (!canShow()) return;

        if (canShowDay) {
            mDpvDayFrom.setVisibility(View.VISIBLE);
            mTvDayUnitFrom.setVisibility(View.VISIBLE);
        } else {
            mDpvDayFrom.setVisibility(View.GONE);
            mTvDayUnitFrom.setVisibility(View.GONE);
        }
    }

    /**
     * 设置日期控件是否显示月
     */
    public void setCanShowMonth(boolean canShowMonth) {
        if (!canShow()) return;

        if (canShowMonth) {
            mDpvMonthFrom.setVisibility(View.VISIBLE);
            mTvMonthUnitFrom.setVisibility(View.VISIBLE);
        } else {
            mDpvMonthFrom.setVisibility(View.GONE);
            mTvMonthUnitFrom.setVisibility(View.GONE);
        }
    }

    private void initScrollUnit(Integer... units) {
        if (units == null || units.length == 0) {
            mScrollUnits = SCROLL_UNIT_HOUR + SCROLL_UNIT_MINUTE;
        } else {
            for (int unit : units) {
                mScrollUnits ^= unit;
            }
        }
    }

    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setScrollLoop(boolean canLoop) {
        if (!canShow()) return;

        mDpvYearFrom.setCanScrollLoop(canLoop);
        mDpvMonthFrom.setCanScrollLoop(canLoop);
        mDpvDayFrom.setCanScrollLoop(canLoop);
        mDpvHourFrom.setCanScrollLoop(canLoop);
        mDpvMinuteFrom.setCanScrollLoop(canLoop);
    }

    /**
     * 设置日期控件是否展示滚动动画
     */
    public void setCanShowAnim(boolean canShowAnim) {
        if (!canShow()) return;

        mDpvYearFrom.setCanShowAnim(canShowAnim);
        mDpvMonthFrom.setCanShowAnim(canShowAnim);
        mDpvDayFrom.setCanShowAnim(canShowAnim);
        mDpvHourFrom.setCanShowAnim(canShowAnim);
        mDpvMinuteFrom.setCanShowAnim(canShowAnim);
    }

    /**
     * 销毁弹窗
     */
    public void onDestroy() {
        if (mPickerDialog != null) {
            mPickerDialog.dismiss();
            mPickerDialog = null;

            mDpvYearFrom.onDestroy();
            mDpvMonthFrom.onDestroy();
            mDpvDayFrom.onDestroy();
            mDpvHourFrom.onDestroy();
            mDpvMinuteFrom.onDestroy();
        }
    }

}

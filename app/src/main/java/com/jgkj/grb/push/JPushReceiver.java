package com.jgkj.grb.push;

import android.content.Context;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 自定义 JPush message 接收器,包括操作 tag/alias 的结果返回(仅仅包含 tag/alias 新接口部分)
 */
public class JPushReceiver extends JPushMessageReceiver {

    @Override
    public void onRegister(Context context, String extras) {
        JTagAliasOperatorHelper.getInstance().onRegister(context, extras);
        super.onRegister(context, extras);
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        JTagAliasOperatorHelper.getInstance().onCustomMessage(context, customMessage);
        super.onMessage(context, customMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        JTagAliasOperatorHelper.getInstance().onNotifyMessageOpened(context, notificationMessage);
        super.onNotifyMessageOpened(context, notificationMessage);
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        JTagAliasOperatorHelper.getInstance().onNotifyMessageArrived(context, notificationMessage);
        super.onNotifyMessageArrived(context, notificationMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        JTagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        JTagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        JTagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        JTagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }
}

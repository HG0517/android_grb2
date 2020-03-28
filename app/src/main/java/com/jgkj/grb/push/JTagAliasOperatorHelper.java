package com.jgkj.grb.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgkj.grb.base.Constants;
import com.jgkj.grb.eventbus.RefreshPushMsg;
import com.jgkj.grb.ui.activity.MainActivity;
import com.jgkj.grb.ui.activity.SplashActivity;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.utils.sp.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;

/**
 * 处理tagalias相关的逻辑
 */
public class JTagAliasOperatorHelper {
    private static final String TAG = "TAG_JTagAliasHelper";
    public static int sequence = 1;
    /**
     * 增加
     */
    public static final int ACTION_ADD = 1;
    /**
     * 覆盖
     */
    public static final int ACTION_SET = 2;
    /**
     * 删除部分
     */
    public static final int ACTION_DELETE = 3;
    /**
     * 删除所有
     */
    public static final int ACTION_CLEAN = 4;
    /**
     * 查询
     */
    public static final int ACTION_GET = 5;

    public static final int ACTION_CHECK = 6;

    public static final int DELAY_SEND_ACTION = 1;

    public static final int DELAY_SET_MOBILE_NUMBER_ACTION = 2;

    private Context context;

    private static JTagAliasOperatorHelper mInstance;

    private SharedPreferencesHelper sharedPreferencesConfig;
    private SharedPreferencesHelper sharedPreferencesHelper;

    private JTagAliasOperatorHelper() {
    }

    public static JTagAliasOperatorHelper getInstance() {
        if (mInstance == null) {
            synchronized (JTagAliasOperatorHelper.class) {
                if (mInstance == null) {
                    mInstance = new JTagAliasOperatorHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
        }
    }

    private SparseArray<Object> setActionCache = new SparseArray<Object>();

    public Object get(int sequence) {
        return setActionCache.get(sequence);
    }

    public Object remove(int sequence) {
        return setActionCache.get(sequence);
    }

    public void put(int sequence, Object tagAliasBean) {
        setActionCache.put(sequence, tagAliasBean);
    }

    @SuppressLint("HandlerLeak")
    private Handler delaySendHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELAY_SEND_ACTION:
                    if (msg.obj != null && msg.obj instanceof TagAliasBean) {
                        Logger.i(TAG, "on delay time");
                        sequence++;
                        TagAliasBean tagAliasBean = (TagAliasBean) msg.obj;
                        setActionCache.put(sequence, tagAliasBean);
                        if (context != null) {
                            handleAction(context, sequence, tagAliasBean);
                        } else {
                            Logger.e(TAG, "#unexcepted - context was null");
                        }
                    } else {
                        Logger.w(TAG, "#unexcepted - msg obj was incorrect");
                    }
                    break;
                case DELAY_SET_MOBILE_NUMBER_ACTION:
                    if (msg.obj != null && msg.obj instanceof String) {
                        Logger.i(TAG, "retry set mobile number");
                        sequence++;
                        String mobileNumber = (String) msg.obj;
                        setActionCache.put(sequence, mobileNumber);
                        if (context != null) {
                            handleAction(context, sequence, mobileNumber);
                        } else {
                            Logger.e(TAG, "#unexcepted - context was null");
                        }
                    } else {
                        Logger.w(TAG, "#unexcepted - msg obj was incorrect");
                    }
                    break;
            }
        }
    };

    public void handleAction(Context context, int sequence, String mobileNumber) {
        put(sequence, mobileNumber);
        Logger.d(TAG, "sequence:" + sequence + ",mobileNumber:" + mobileNumber);
        JPushInterface.setMobileNumber(context, sequence, mobileNumber);
    }

    /**
     * 处理设置tag
     */
    public void handleAction(Context context, int sequence, TagAliasBean tagAliasBean) {
        init(context);
        if (tagAliasBean == null) {
            Logger.w(TAG, "tagAliasBean was null");
            return;
        }
        put(sequence, tagAliasBean);
        if (tagAliasBean.isAliasAction) {
            switch (tagAliasBean.action) {
                case ACTION_GET:
                    JPushInterface.getAlias(context, sequence);
                    break;
                case ACTION_DELETE:
                    JPushInterface.deleteAlias(context, sequence);
                    break;
                case ACTION_SET:
                    JPushInterface.setAlias(context, sequence, tagAliasBean.alias);
                    break;
                default:
                    Logger.w(TAG, "unsupport alias action type");
                    return;
            }
        } else {
            switch (tagAliasBean.action) {
                case ACTION_ADD:
                    JPushInterface.addTags(context, sequence, tagAliasBean.tags);
                    break;
                case ACTION_SET:
                    JPushInterface.setTags(context, sequence, tagAliasBean.tags);
                    break;
                case ACTION_DELETE:
                    JPushInterface.deleteTags(context, sequence, tagAliasBean.tags);
                    break;
                case ACTION_CHECK:
                    //一次只能check一个tag
                    String tag = (String) tagAliasBean.tags.toArray()[0];
                    JPushInterface.checkTagBindState(context, sequence, tag);
                    break;
                case ACTION_GET:
                    JPushInterface.getAllTags(context, sequence);
                    break;
                case ACTION_CLEAN:
                    JPushInterface.cleanTags(context, sequence);
                    break;
                default:
                    Logger.w(TAG, "unsupport tag action type");
                    return;
            }
        }
    }

    private boolean retryActionIfNeeded(int errorCode, TagAliasBean tagAliasBean) {
        if (!JExampleUtil.isConnected(context)) {
            Logger.w(TAG, "no network");
            return false;
        }
        //返回的错误码为6002 超时,6014 服务器繁忙,都建议延迟重试
        if (errorCode == 6002 || errorCode == 6014) {
            Logger.d(TAG, "need retry");
            if (tagAliasBean != null) {
                Message message = new Message();
                message.what = DELAY_SEND_ACTION;
                message.obj = tagAliasBean;
                delaySendHandler.sendMessageDelayed(message, 1000 * 60);
                String logs = getRetryStr(tagAliasBean.isAliasAction, tagAliasBean.action, errorCode);
                JExampleUtil.showToast(logs, context);
                return true;
            }
        }
        return false;
    }

    private boolean retrySetMObileNumberActionIfNeeded(int errorCode, String mobileNumber) {
        if (!JExampleUtil.isConnected(context)) {
            Logger.w(TAG, "no network");
            return false;
        }
        //返回的错误码为6002 超时,6024 服务器内部错误,建议稍后重试
        if (errorCode == 6002 || errorCode == 6024) {
            Logger.d(TAG, "need retry");
            Message message = new Message();
            message.what = DELAY_SET_MOBILE_NUMBER_ACTION;
            message.obj = mobileNumber;
            delaySendHandler.sendMessageDelayed(message, 1000 * 60);
            String str = "Failed to set mobile number due to %s. Try again after 60s.";
            str = String.format(Locale.ENGLISH, str, (errorCode == 6002 ? "timeout" : "server internal error”"));
            JExampleUtil.showToast(str, context);
            return true;
        }
        return false;

    }

    private String getRetryStr(boolean isAliasAction, int actionType, int errorCode) {
        String str = "Failed to %s %s due to %s. Try again after 60s.";
        str = String.format(Locale.ENGLISH, str, getActionStr(actionType), (isAliasAction ? "alias" : " tags"), (errorCode == 6002 ? "timeout" : "server too busy"));
        return str;
    }

    private String getActionStr(int actionType) {
        switch (actionType) {
            case ACTION_ADD:
                return "add";
            case ACTION_SET:
                return "set";
            case ACTION_DELETE:
                return "delete";
            case ACTION_GET:
                return "get";
            case ACTION_CLEAN:
                return "clean";
            case ACTION_CHECK:
                return "check";
        }
        return "unkonw operation";
    }

    /**
     * 设置标签回调
     */
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG, "action - onTagOperatorResult, sequence:" + sequence + ",tags:" + jPushMessage.getTags());
        Logger.i(TAG, "tags size:" + jPushMessage.getTags().size());
        init(context);
        //根据sequence从之前操作缓存中获取缓存记录
        TagAliasBean tagAliasBean = (TagAliasBean) setActionCache.get(sequence);
        if (tagAliasBean == null) {
            JExampleUtil.showToast("onTag：获取缓存记录失败", context);
            return;
        }
        if (jPushMessage.getErrorCode() == 0) {
            Logger.i(TAG, "action - modify tag Success,sequence:" + sequence);
            setActionCache.remove(sequence);
            String logs = getActionStr(tagAliasBean.action) + " tags success";
            Logger.i(TAG, logs);
            JExampleUtil.showToast(logs, context);
        } else {
            String logs = "Failed to " + getActionStr(tagAliasBean.action) + " tags";
            if (jPushMessage.getErrorCode() == 6018) {
                //tag数量超过限制,需要先清除一部分再add
                logs += ", tags is exceed limit need to clean";
            }
            logs += ", errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if (!retryActionIfNeeded(jPushMessage.getErrorCode(), tagAliasBean)) {
                JExampleUtil.showToast(logs, context);
            }
        }
    }

    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG, "action - onCheckTagOperatorResult, sequence:" + sequence + ",checktag:" + jPushMessage.getCheckTag());
        init(context);
        //根据sequence从之前操作缓存中获取缓存记录
        TagAliasBean tagAliasBean = (TagAliasBean) setActionCache.get(sequence);
        if (tagAliasBean == null) {
            JExampleUtil.showToast("CheckTag：获取缓存记录失败", context);
            return;
        }
        if (jPushMessage.getErrorCode() == 0) {
            Logger.i(TAG, "tagBean:" + tagAliasBean);
            setActionCache.remove(sequence);
            String logs = getActionStr(tagAliasBean.action) + " tag " + jPushMessage.getCheckTag() + " bind state success,state:" + jPushMessage.getTagCheckStateResult();
            Logger.i(TAG, logs);
            JExampleUtil.showToast(logs, context);
        } else {
            String logs = "Failed to " + getActionStr(tagAliasBean.action) + " tags, errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if (!retryActionIfNeeded(jPushMessage.getErrorCode(), tagAliasBean)) {
                JExampleUtil.showToast(logs, context);
            }
        }
    }

    /**
     * 设置别名回调
     */
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG, "action - onAliasOperatorResult, sequence:" + sequence + ",alias:" + jPushMessage.getAlias());
        init(context);
        //根据sequence从之前操作缓存中获取缓存记录
        TagAliasBean tagAliasBean = (TagAliasBean) setActionCache.get(sequence);
        if (tagAliasBean == null) {
            JExampleUtil.showToast("Alias：获取缓存记录失败", context);
            return;
        }
        if (jPushMessage.getErrorCode() == 0) {
            Logger.i(TAG, "action - modify alias Success,sequence:" + sequence);
            setActionCache.remove(sequence);
            String logs = getActionStr(tagAliasBean.action) + " alias success";
            Logger.i(TAG, logs);
            JExampleUtil.showToast(logs, context);
        } else {
            String logs = "Failed to " + getActionStr(tagAliasBean.action) + " alias, errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if (!retryActionIfNeeded(jPushMessage.getErrorCode(), tagAliasBean)) {
                JExampleUtil.showToast(logs, context);
            }
        }
    }

    /**
     * 设置手机号码回调
     */
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        Logger.i(TAG, "action - onMobileNumberOperatorResult, sequence:" + sequence + ",mobileNumber:" + jPushMessage.getMobileNumber());
        init(context);
        if (jPushMessage.getErrorCode() == 0) {
            Logger.i(TAG, "action - set mobile number Success,sequence:" + sequence);
            setActionCache.remove(sequence);
        } else {
            String logs = "Failed to set mobile number, errorCode:" + jPushMessage.getErrorCode();
            Logger.e(TAG, logs);
            if (!retrySetMObileNumberActionIfNeeded(jPushMessage.getErrorCode(), jPushMessage.getMobileNumber())) {
                JExampleUtil.showToast(logs, context);
            }
        }
    }

    public static class TagAliasBean {
        int action;
        Set<String> tags;
        String alias;
        boolean isAliasAction;

        @Override
        public String toString() {
            return "TagAliasBean{" +
                    "action=" + action +
                    ", tags=" + tags +
                    ", alias='" + alias + '\'' +
                    ", isAliasAction=" + isAliasAction +
                    '}';
        }
    }

    /**
     * 保存注册 ID
     */
    public void onRegister(Context context, String extras) {
        Logger.i(TAG, "JPush 用户注册成功：");
        if (null == sharedPreferencesConfig) {
            sharedPreferencesConfig = new SharedPreferencesHelper(context, Constants.SP_KEY_CONFIG);
        }
        sharedPreferencesConfig.putApply(JExampleUtil.KEY_REGISTRATION_ID, extras);
    }

    /**
     * 自定义消息
     */
    public void onCustomMessage(Context context, CustomMessage customMessage) {
        Logger.i(TAG, "JPush 接受到推送下来的自定义消息：" + customMessage.toString());
        if (null == sharedPreferencesConfig) {
            sharedPreferencesConfig = new SharedPreferencesHelper(context, Constants.SP_KEY_CONFIG);
        }
        Gson gson = new Gson();

        // 推送下来的消息
        Map<String, Integer> extraNew = gson.fromJson(customMessage.extra, new TypeToken<Map<String, Integer>>() {
        }.getType());
        if (null == extraNew || extraNew.isEmpty()) return;

        // 已经保存的消息
        Map<String, Integer> extra = gson.fromJson(sharedPreferencesConfig.getSharedPreference(JExampleUtil.KEY_CUSTOM_EXTRA, "").toString(),
                new TypeToken<Map<String, Integer>>() {
                }.getType());
        if (null == extra) extra = new HashMap<>();

        // 遍历新数据，对比保存的数据，已存在把数量累加并更新，不存在直接添加
        // key：1：系统消息，2：商城活动，3：代理消息，4，资讯消息
        // {"type":1,"num":1}
        Integer type = extraNew.get("type");
        Integer num = extraNew.get("num");
        if (extra.containsKey(String.valueOf(type))) {
            Integer value = extra.get(String.valueOf(type));
            value += num;
            extra.put(String.valueOf(type), value);
        } else {
            extra.put(String.valueOf(type), num);
        }

        // 更新界面
        EventBus.getDefault().post(new RefreshPushMsg(extra));

        // 更新保存数据
        String extraUpdate = gson.toJson(extra);
        sharedPreferencesConfig.putApply(JExampleUtil.KEY_CUSTOM_EXTRA, extraUpdate);
    }

    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        Logger.i(TAG, "JPush 接受到推送下来的通知：" + notificationMessage.toString());
    }

    /**
     * 用户点击了通知
     */
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        Logger.i(TAG, "JPush 用户点击打开了通知：");
        // Launcher Activity
        Intent launcherIntent = new Intent(context, SplashActivity.class);
        launcherIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Open MainActivity，可以不添加
        // getOpenActivityIntent(context, MainActivity.class);

        // Open 其他 Activity，打开指定的界面
        // getOpenActivityIntent(context, MessageManagementActivity.class);

        startActivities(context, launcherIntent);
    }

    /**
     * 指定打开的界面
     */
    private Intent getOpenActivityIntent(Context context, Class<?> cls) {
        Intent openActivityIntent = new Intent(context, cls);

        // 如果是 MainActivity 添加 FLAG_ACTIVITY_CLEAR_TOP
        if (cls == MainActivity.class) {
            openActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        return openActivityIntent;
    }

    /**
     * 用户点击了通知，打开指定界面
     */
    private void startActivities(Context context, Intent... intents) {
        context.startActivities(intents);
    }

}

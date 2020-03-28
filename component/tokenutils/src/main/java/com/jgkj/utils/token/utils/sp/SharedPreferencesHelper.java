package com.jgkj.utils.token.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Map;

/**
 * Created by jugekeji on 2018/9/20.
 * <p>
 * 这两个方法的区别在于： 
 * 1. apply 没有返回值，而 commit 返回 boolean 表明修改是否提交成功 
 * 2. apply 是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而 commit 是同步的提交到硬件磁盘，
 * 因此，在多个并发的提交 commit 的时候，他们会等待正在处理的 commit 保存到磁盘后再操作，从而降低了效率。
 * 而apply 只是原子的提交到内容，后面有调用 apply 的函数的将会直接覆盖前面的内存数据，这样从一定程度上提高了很多效率。 
 * 3. apply 方法不会提示任何失败的提示。由于在一个进程中，sharedPreference 是单实例，一般不会出现并发冲突
 * <p>
 * 4.如果对提交的结果不关心的话，建议使用 apply，当然需要确保提交成功且有后续操作的话，还是需要用 commit 的。
 * 5.在不涉及到跨进程通过 SharedPreferences 共享数据的情况，就永远使用 apply，apply 方法执行后可以立即在任意地方读取到更新后对应的 key 值，不会出现因为 apply 是异步就需要等待一会儿，再读取的情况。
 * <p>
 * 6.理解 Android SharedPreferences 的 commit 与 apply ： https://www.jianshu.com/p/3b2ac6201b33
 */

public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public SharedPreferencesHelper(Context context, String username) {
        if (TextUtils.isEmpty(username)) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            sharedPreferences = context.getApplicationContext().getSharedPreferences(username + "_sp", Context.MODE_PRIVATE);
        }

        editor = sharedPreferences.edit();
    }

    /**
     * 存储 Commit 方式
     */
    public boolean putCommit(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        return editor.commit();
    }

    /**
     * 存储 Apply 方式
     */
    public void putApply(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 获取保存的数据
     */
    public Object getSharedPreference(String key, Object defaultObject) {
        if (!contain(key)) return defaultObject;

        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    /**
     * 移除某个 key 值已经对应的值
     */
    public boolean removeCommit(String key) {
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 移除某个 key 值已经对应的值
     */
    public void removeApply(String key) {
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public boolean clearCommit() {
        editor.clear();
        return editor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clearApply() {
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个 key 是否存在
     */
    public Boolean contain(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

}

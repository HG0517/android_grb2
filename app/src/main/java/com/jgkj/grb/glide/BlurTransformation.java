package com.jgkj.grb.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Glide 实现高斯模糊
 * Created by Hh on 2017/1/5.
 */

public class BlurTransformation extends BitmapTransformation {
    private static final String ID = "com.jgkj.videoplayer.glide.BlurTransformation";
    private static final byte[] ID_BYTES = ID.getBytes();

    private int radius = 25;
    private Context context;

    public BlurTransformation(Context context) {
        this(context, 25);
    }

    public BlurTransformation(Context context, @IntRange(from = 1, to = 25) int radius) {
        this.context = context;
        this.radius = radius;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return gaussianBlur(pool, toTransform);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap gaussianBlur(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        // 创建高斯模糊 先创建一个空的 bitmap
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        // 初始化Renderscript，这个类提供了RenderScript context，在创建其他RS类之前必须要先创建这个类，他控制RenderScript的初始化，资源管理，释放
        RenderScript renderScript = RenderScript.create(context);
        // 创建高斯模糊对象
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        // 创建Allocations，此类是将数据传递给RenderScript内核的主要方法，并制定一个后备类型存储给定类型
        Allocation input = Allocation.createFromBitmap(renderScript, source);
        Allocation output = Allocation.createFromBitmap(renderScript, result);

        // 设定模糊度
        scriptIntrinsicBlur.setRadius(radius);
        // 执行渲染脚本
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);

        output.copyTo(result);

        source.recycle();
        renderScript.destroy();

        return result;
    }

    //  -------------  以下必须实现  -------------
    @Override
    public boolean equals(Object o) {
        return o instanceof BlurTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
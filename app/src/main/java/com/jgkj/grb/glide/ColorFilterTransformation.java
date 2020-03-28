package com.jgkj.grb.glide;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Glide 滤色器
 * Created by jugekeji on 2018/10/12.
 */

public class ColorFilterTransformation extends BitmapTransformation {
    private static final String ID = "com.jgkj.videoplayer.glide.ColorFilterTransformation";
    private static final byte[] ID_BYTES = ID.getBytes();
    private int color;

    public ColorFilterTransformation(int color) {
        this.color = color;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap.Config config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(toTransform, 0, 0, paint);

        return bitmap;
    }

    @Override
    public String toString() {
        return "ColorFilterTransformation(color=" + color + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ColorFilterTransformation &&
                ((ColorFilterTransformation) o).color == color;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + color * 10;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + color).getBytes(CHARSET));
    }
}

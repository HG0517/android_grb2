package com.jgkj.grb.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

/**
 * Glide Transformation Utils
 * Created by jugekeji on 2019/1/24.
 */

public class TransformationUtils {

    /**
     * 创建圆角
     *
     * @param bitmap
     * @param radius
     * @return
     */
    public static Bitmap createRound(Bitmap bitmap, float radius) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    /**
     * 创建倒影
     *
     * @param context
     * @param originalImage
     * @param radius
     * @param heightReflection
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap createReflected(Context context, Bitmap originalImage, float radius, int heightReflection) {
        // 倒影高度：最小是 1
        if (heightReflection <= 0) {
            return originalImage;
        }

        // 原图与倒影之间的间隙
        final int reflectionGap = 0;

        // 获得图片的长宽
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // 实现图片的反转
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // 创建模糊图片Bitmap对象
        Bitmap reflectionImage = createGaussianBlur(context, originalImage, radius);
        // 创建反转后的图片Bitmap对象，图片高是 heightReflection
        reflectionImage = Bitmap.createBitmap(reflectionImage, 0,
                height - heightReflection, width, heightReflection, matrix, false);

        // 创建标准的Bitmap对象，宽和原图一致，高是原图的高 + heightReflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + heightReflection), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        // 创建画布对象，将原图画于画布，起点是原点位置
        canvas.drawBitmap(originalImage, 0, 0, null);

//        Paint defaultPaint = new Paint();
//        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

        // 将反转后的图片画到画布中，形成倒影，起点是原图的底
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        // 创建线性渐变LinearGradient对象
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap,
                0x70ffffff, 0x00ffffff,
                Shader.TileMode.MIRROR);
        // 绘制渐变
        paint.setShader(shader);

        // 倒影遮罩效果
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        // 画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果
        canvas.drawRect(0, height, width,
                bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 创建高斯模糊
     *
     * @param context
     * @param source
     * @param radius
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap createGaussianBlur(Context context, Bitmap source, float radius) {
        if (source == null) return null;

        // 创建高斯模糊 先创建一个空的 bitmap
        Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

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

        renderScript.destroy();

        return result;
    }
}

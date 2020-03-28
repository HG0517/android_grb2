package com.jgkj.grb.view.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by brightpoplar@163.com on 2019/10/20.
 */
@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    private boolean adjustTopForAscent = true;
    private Paint.FontMetricsInt fontMetricsInt;
    private Rect minRect;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getPaint().setFakeBoldText(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (fontMetricsInt == null) {
            // fontMetricsInt 包含的是 text 文字四条线的 距离，
            // 此四条线距离也是以 text 文字 baseline 为基准的
            fontMetricsInt = new Paint.FontMetricsInt();
        }
        getPaint().getFontMetricsInt(fontMetricsInt);
        if (minRect == null) {
            // minRect 用来获取文字实际显示的时候的左上角和右下角坐标
            // 该坐标是以 text 文字 baseline 为基准的
            minRect = new Rect();
        }
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), minRect);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        lp.topMargin = -(fontMetricsInt.bottom - minRect.bottom) + (fontMetricsInt.top - minRect.top);
        //lp.rightMargin = -(minRect.left + (getMeasuredWidth() - minRect.right));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adjustTopForAscent) {
            if (fontMetricsInt == null) {
                // fontMetricsInt 包含的是 text 文字四条线的 距离，
                //此四条线距离也是以 text 文字 baseline 为基准的
                fontMetricsInt = new Paint.FontMetricsInt();
            }
            getPaint().getFontMetricsInt(fontMetricsInt);
            if (minRect == null) {
                // minRect 用来获取文字实际显示的时候的左上角和右下角坐标
                // 该坐标是以 text 文字 baseline 为基准的
                minRect = new Rect();
            }
            getPaint().getTextBounds(getText().toString(), 0, getText().length(), minRect);
            canvas.translate(-minRect.left+1, fontMetricsInt.bottom - minRect.bottom);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        this.requestLayout();
    }
}

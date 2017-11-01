package me.stefan.library.mu5viewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gjm on 2017/8/25.
 * Func:
 */

public class SamView extends View {
    //设置画笔
    Paint forePaint, bgPaint;
    Bitmap bgBitmap;
    Bitmap foreBitmap;
    private float curLeft;
    private long STEP_DURATION = 1;//保证粒度相当小
    private float STEP = 0.12f;
    private boolean isStart;

    public SamView(Context context) {
        super(context);
        init();
    }

    public SamView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        forePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sam_bg);
        foreBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sam_foreground);

        //view加载完成时回调
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                starMove();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(foreBitmap.getWidth(), foreBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(createRoundConerImage(bgBitmap), 0, 0, bgPaint);

        canvas.drawBitmap(foreBitmap, 0, 0, forePaint);
    }

    /**
     * 根据原图添加圆角
     *
     * @param source
     * @return
     */
    private Bitmap createRoundConerImage(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int sourceHeight = source.getHeight();
        Bitmap target = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(target);
        int leftMargin = Utils.dip2px(getContext(), 4.8f);
        RectF rect = new RectF(leftMargin, (getHeight() - sourceHeight) / 2,
                leftMargin + source.getWidth(), getHeight() / 2 + sourceHeight / 2);
        canvas.drawRoundRect(rect, sourceHeight / 2, sourceHeight / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, curLeft, (getHeight() - sourceHeight) / 2, paint);
        return target;
    }

    private int ORight = 2;
    private int OLeft = 1;
    private int orientation = OLeft;//默认先朝右移动
    private Timer timer;

    public void starMove() {
        if (isStart) return;

        isStart = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 0, STEP_DURATION);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (orientation == ORight) {//向右移动
                curLeft += STEP;
                if (curLeft < 0) {

                } else {
                    curLeft = 0;
                    orientation = OLeft;
                }
            } else {
                if(bgBitmap==null)return;
                int leftBounder = -(bgBitmap.getWidth() - getWidth());
                curLeft -= STEP;
                if (curLeft > leftBounder) {

                } else {
                    curLeft = leftBounder;
                    orientation = ORight;
                }
            }
            postInvalidate();
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (foreBitmap != null) {
            foreBitmap.recycle();
            foreBitmap = null;
        }
        if (bgBitmap != null) {
            bgBitmap.recycle();
            bgBitmap = null;
        }
    }
}

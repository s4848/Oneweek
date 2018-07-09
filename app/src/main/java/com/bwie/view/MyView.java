package com.bwie.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.caolina0709.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MyView extends android.support.v7.widget.AppCompatTextView {
   private Path mpath;//路径
    private Paint mpaint;//画笔
    private Canvas mcanvas;//画布

    private Bitmap mbitmap;//zhaopian

    //记录坐标
    private int mLastX;
    private int mLastY;
    //要擦掉的图片
    private Bitmap cbitmap;
    public MyView(Context context) {
        super(context);
        init();
    }



    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得控件的宽高
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化bitmap
       mbitmap= Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mcanvas=new Canvas(mbitmap);

        //设置画笔属性
        setoutPaint();
        mcanvas.drawColor(Color.parseColor("#c0c0c0"));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(cbitmap,0,0,null);
        canvas.drawBitmap(mbitmap,0,0,null);
           mcanvas.drawPath(mpath,mpaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action= event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastX=x;
                mLastY=y;
                mpath.moveTo(mLastX,mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx>3 || dy>3){
                    mpath.lineTo(x,y);
                }
                mLastX=x;
                mLastY=y;
        }
        invalidate();//刷新
        return  true;
    }

    private void setoutPaint() {
        mpaint.setColor(Color.RED);
        mpaint.setAntiAlias(true);
        mpaint.setDither(true);
        mpaint.setStrokeJoin(Paint.Join.ROUND);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setStrokeWidth(60);

    }

    private void init() {
        mpaint=new Paint();
        mpath=new Path();
        cbitmap= BitmapFactory.decodeResource(getResources(), R.drawable.kjusgsvg);
    }


}

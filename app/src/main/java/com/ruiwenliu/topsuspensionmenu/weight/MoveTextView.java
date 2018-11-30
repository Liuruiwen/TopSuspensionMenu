package com.ruiwenliu.topsuspensionmenu.weight;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;

/**
 * Created by ruiwen
 * Data:2018/9/30 0030
 * Email:1054750389@qq.com
 * Desc:
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MoveTextView extends AppCompatTextView {
    private float lastX;
    private float lastY;
    private OverScroller mScroller;
    private float startX;
    private float startY;
    private int pWidth;

    public MoveTextView(Context context) {
        super(context);
    }

    public MoveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        pWidth = dm.widthPixels;

        init();
    }

    public MoveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mScroller = new OverScroller(this.getContext(),new BounceInterpolator());
        this.setEnabled(true);
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }
    float s_x;
    float s_y;
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = event.getRawX();
                lastY = event.getRawY();
                s_x = lastX;
                s_y = lastY;
                Log.i("m=xxxx====r","完成了"+lastX+"==="+lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float disX = event.getRawX() - lastX;
                float disY = event.getRawY() - lastY;
//                s_x = disX;
//                s_y = disY;
//                if(disX>0&& disX<pWidth){
                    offsetLeftAndRight((int) disX);
                    offsetTopAndBottom((int) disY);
                    lastX = event.getRawX();
                    lastY = event.getRawY();

//                }

//                layout(getLeft() + (int)disX, getTop() + (int)disY, getRight() + (int)disX, getBottom() + (int)disY);

                break;
            case MotionEvent.ACTION_UP:

//                if(lastX<0|| lastX>pWidth) {
//                    float height=Math.abs( s_y-lastY-this.getHeight());
//                    float width=Math.abs(s_x-lastX-this.getWidth());
//
//                    layout(getLeft() + (int)width, getTop() + (int)height, getRight() + (int)width, getBottom() + (int)height);
////                    offsetLeftAndRight((int) s_x);
////                    offsetTopAndBottom((int) s_y);
//                }
                invalidate();
//                if(lastX<0|| lastX>pWidth){
//                    Log.i("txxxx====",""+s_x);
//                    Log.i("tyyyy====",""+s_y);
//                    offsetLeftAndRight((int) s_x);
//                    offsetTopAndBottom((int) s_y);
//                    invalidate();
//                }else {
//                    Log.i("xxxx====",""+lastX);
//                    Log.i("yyyy====",""+lastY);
//                    smoothScrollBy((int)lastX,(int)lastY);
//                }


//                invalidate();
                break;
        }
        return true;
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.i("xxxx====s","完成了"+this.getX()+"==="+this.getY());

//        invalidate();
        if(mScroller.computeScrollOffset()){
            setX(mScroller.getCurrX());
            setY(mScroller.getCurrY());
            scrollTo((int)mScroller.getCurrX(),(int)mScroller.getCurrY());
            invalidate();

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("xxxx====s","变了");
        startY = this.getY();
        startX= this.getX();
    }
}

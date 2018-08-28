package com.ruiwenliu.topsuspensionmenu.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ruiwenliu.topsuspensionmenu.R;

/**
 * Created by ruiwen
 * Data:2018/8/27 0027
 * Email:1054750389@qq.com
 * Desc:
 */

public class CardAnimManager<T extends ViewGroup> {
    private static CardAnimManager mAnimManager = null;
    private Context mContext;
    private float[] mCurrentPosition = new float[2];
    private ImageView animView=null;
    public static int actionTime=500;//动画执行时间
    public static CardAnimManager getInstance(Context context) {
        if (mAnimManager == null) {
            mAnimManager = new CardAnimManager(context);
        }

        return mAnimManager;
    }

    public CardAnimManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void addGoodToCar(final T viewStart, View scrollView, View endView, AnimatorInter animatorInter) {
        if(endView==null){
            return;
        }
//        final ImageView view = new ImageView(mContext);
//        view.setImageResource(R.drawable.add);
//        view.setImageDrawable(scrollView.getDrawable());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(40, 40);
        viewStart.addView(getAnimView(), layoutParams);

        //二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLoc = new int[2];
        viewStart.getLocationInWindow(parentLoc);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        scrollView.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        endView.getLocationInWindow(endLoc);

        float startX = startLoc[0] - parentLoc[0] + scrollView.getWidth() / 2;
        float startY = startLoc[1] - parentLoc[1] + scrollView.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLoc[0] + endView.getWidth() / 5;
        float toY = endLoc[1] - parentLoc[1];

        //开始绘制贝塞尔曲线
        Path path = new Path();
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        //属性动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(actionTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                getAnimView().setTranslationX(mCurrentPosition[0]);
                getAnimView().setTranslationY(mCurrentPosition[1]);
            }
        });
        if(animatorInter!=null){
            valueAnimator.addListener(animatorInter);
        }else {
            valueAnimator.addListener(new AnimatorInter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    viewStart.removeView(getAnimView());
                }
            });
        }

        valueAnimator.start();
    }

    public ImageView getAnimView(){
        if(animView==null){
            animView=new ImageView(mContext);
            animView.setImageResource(R.drawable.add);
            return animView;
        }

        return animView;
    }


}

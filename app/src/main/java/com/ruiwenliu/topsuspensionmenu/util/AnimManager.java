package com.ruiwenliu.topsuspensionmenu.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruiwenliu.topsuspensionmenu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/27 0027
 * Email:1054750389@qq.com
 * Desc:
 */

public class AnimManager<T extends ViewGroup> {
    private static AnimManager mAnimManager = null;
    private Context mContext;
    private float[] mCurrentPosition = new float[2];
    public static AnimManager getInstance(Context context) {
        if (mAnimManager == null) {
            mAnimManager = new AnimManager(context);
        }

        return mAnimManager;
    }

    public AnimManager(Context context) {
        this.mContext = context.getApplicationContext();
    }


    //    ,ImageView imageView
    public void addGoodToCar(T viewStart, View scrollView, View endView, AnimatorInter animatorInter) {
        final ImageView view = new ImageView(mContext);
//        view.setImageDrawable(scrollView.getDrawable());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        viewStart.addView(view, layoutParams);

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
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                view.setTranslationX(mCurrentPosition[0]);
                view.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.addListener(animatorInter);
        valueAnimator.start();
//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // 购物车的数量加1
//                mCount++;
//                mCountTv.setText(String.valueOf(mCount));
//                // 把移动的图片imageview从父布局里移除
//                viewStart.removeView(view);
//
//                //shopImg 开始一个放大动画
//                Animation scaleAnim = AnimationUtils.loadAnimation(GoodsListActivity.this, R.anim.shop_car_scale);
//                endView.startAnimation(scaleAnim);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        valueAnimator.start();
    }


}

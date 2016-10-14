package com.deeny.test.mylistviewdemo;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by deeny on 2016/10/14.
 */
public class MyAnimation extends Animation {

    //当前高度是100，目标高度是50  0.5*（100-50）
    private int currentHeight;//空间当前的高度
    private int targetHeight;//目标高度
    private View targetView;//想要变化高度的控件

    public MyAnimation( int targetHeight, View targetView) {
        this.currentHeight = targetView.getHeight();
        this.targetHeight = targetHeight;
        this.targetView = targetView;
        setDuration(500);//设置动画持续时间
    }

    /**
     * 0-1之间的一个数值
     * @param interpolatedTime
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //根据动画的完成度来计算当前控件应该有多高
        int height = (int) (currentHeight - (currentHeight-targetHeight)*interpolatedTime);
        targetView.getLayoutParams().height = height;
        targetView.requestLayout();
        super.applyTransformation(interpolatedTime, t);
    }
}

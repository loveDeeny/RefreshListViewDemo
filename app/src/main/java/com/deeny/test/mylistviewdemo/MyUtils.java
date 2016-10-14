package com.deeny.test.mylistviewdemo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by deeny on 2016/10/14.
 */
public class MyUtils {

    public static int[] getWindowDis(Context context){
        int[] ints = new int[2];
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();//用于存储屏幕宽高度的对象
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);//将获取到的屏幕宽高设置到DisplayMetrics对象中
        ints[0] = displayMetrics.widthPixels;
        ints[1] = displayMetrics.heightPixels;
        return ints;
    }
}

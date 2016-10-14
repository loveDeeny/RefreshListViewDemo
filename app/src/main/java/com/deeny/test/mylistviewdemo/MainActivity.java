package com.deeny.test.mylistviewdemo;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

public class MainActivity extends Activity {

    private MyListview listview;
    /**
     * 首先是一个自定义的listview，顶部是一个headview，是一个图片控件
     * 随着滑动，图片控件越来越大，显示的图片内容越来越多
     * 松手后，加载数据，加载完成图片回到初始位置
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (MyListview) findViewById(R.id.myListview);
        final View headView = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        listview.addHeaderView(headView);
        listview.setAdapter(new MyAdapter(MainActivity.this));
        //监听某个view所在的xml文件全部加载完成并且绘制了位置之后的监听
        headView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //这行代码应该在测量之后执行
                listview.setHeadView(headView);
                //因为随着控件的变化，这个监听会多次调用，所以加载完成之后一定要手动移除这个监听
                headView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        listview.setOnLoadListener(new MyListview.OnLoadListener() {
            @Override
            public void onLoading() {
                //在此处请求网络，加载数据
                handler.sendEmptyMessageDelayed(1,3000);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listview.onComplete();//通知请求完成，刷新imageview
        }
    };
}

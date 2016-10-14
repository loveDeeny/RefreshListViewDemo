package com.deeny.test.mylistviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by deeny on 2016/10/13.
 */
public class MyListview extends ListView {
    private View headView;
    private ImageView imageView;
    private int maxOverY;//下拉可拖动的最大高度，应该为图片的高度
    private int initHeight;//初始高度，用于回弹回去用
    private OnLoadListener onLoadListener;
    private int totalY;//滑动的总距离，用于判断是否应该刷新，只有滑动超出一定距离后才可以
    private int loadingHeight=100;//当totalY大于这个高度的时候才可以进行刷新数据
    public MyListview(Context context) {
        this(context,null);
    }

    public MyListview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadingHeight = MyUtils.getWindowDis(context)[1]/4;
        Log.e("loading高度是：",loadingHeight+"");
    }


    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    /**
     * 这个方法需要在布局加载完成的时候执行，否则拿不到布局里边的图片，就会导致bitmap为空
     * @param headView
     */
    public void setHeadView(View headView) {
        this.headView = headView;//将view设置为指定的headview，然后add进去
        imageView = (ImageView) headView.findViewById(R.id.iv);
        Log.e("listviewdemo",imageView.getHeight()+"");
        initHeight = imageView.getHeight();//将图片控件的初始高度设置为默认的初始值，用于回弹
        imageView.setDrawingCacheEnabled(true);
//        Bitmap bitmap = imageView.getDrawingCache();//这里一直是空，是因为还没有绘制完成，所以拿不到这个图片
//        if(bitmap != null){
//            maxOverY = bitmap.getHeight();//最大滑动高度为图片的高度
//        }
        maxOverY = imageView.getDrawable().getIntrinsicHeight();//获取imageview上面图片的高度
        imageView.setDrawingCacheEnabled(false);
        //addHeaderView(headView);
    }

    /**
     * 用于内部子空间超出控件本身范围滑动的监听
     * @param deltaX x轴滑动的距离
     * @param deltaY y轴滑动的距离。顶部超出的时候为负数，底部超出的时候为正数
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY
     * @param isTouchEvent
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(deltaY < 0 && isTouchEvent){//如果是手指拖动到达顶部并且下拉
            int currentHeight = imageView.getHeight() - deltaY/2;//imageview变化后的高度,除2是为了下拉的时候往下滑动的比较少点
            totalY -= deltaY;//计算出滑动总距离
            if(currentHeight < maxOverY){//如果当前控件的高度小于可滑动的最大值
                imageView.getLayoutParams().height = currentHeight;
                //一定要加上这句，否则高度设置不上
                imageView.requestLayout();//请求重绘位置
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                //在此应该加载数据，按照规范，在这应该提供一个接口暴露出来
                if(onLoadListener != null && totalY > loadingHeight){//判断不为空，并且滑动距离大于20才执行加载数据
                    onLoadListener.onLoading();
                    totalY = 0;
                }else{
                    totalY = 0;
                    onComplete();//请求重新测量imageview的高度
                }
                //此处注意，如果要集成上拉和下拉两个功能，此处应判断当前按是上拉还是下拉，然后执行不同的操作
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 完成请求数据，刷新imageview高度
     */
    public void onComplete(){
        //创建动画，目标高度为初始高度，目标控件为imageview
        MyAnimation myAnimation = new MyAnimation(initHeight,imageView);
        startAnimation(myAnimation);
    }

    /**
     * 下拉刷新的接口
     */
    public interface OnLoadListener{
        void onLoading();
    }
}

package com.deeny.test.mylistviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by deeny on 2016/10/13.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv);
        textView.setText("我是第几："+i);
        return convertView;
    }
}

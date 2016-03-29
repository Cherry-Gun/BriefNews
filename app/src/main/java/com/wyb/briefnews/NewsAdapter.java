package com.wyb.briefnews;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter{
    private List<News> news;
    private Context context;
    private LayoutInflater inflater;

    public NewsAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void setData(java.util.List<News> news) {
        this.news = news;
    }

    @Override
    public int getCount() {
        if(news == null)
            return 0;
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        if(news == null)
            return null;
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取每一项的布局
        if(convertView == null){
            convertView = (ViewGroup) inflater.inflate(R.layout.item_news, null);

            //找到每一项
            Holder hod = new Holder();
            hod.bt = (TextView) convertView.findViewById(R.id.bt);
            hod.nr = (TextView) convertView.findViewById(R.id.nr);
            hod.rq = (TextView) convertView.findViewById(R.id.rq);
            convertView.setTag(hod);
        }

        //给每一项不同的值
        Holder hold = (Holder) convertView.getTag();
        News ns = news.get(position);
        hold.bt.setText(ns.title);
        hold.nr.setText(ns.description);
        hold.rq.setText(ns.pubDate);

        //双色选择
        convertView.setBackgroundColor(position % 2 == 0? Color.WHITE : Color.LTGRAY);

//		//多色选择
//		int color = 0;
//		switch(position % 3) {
//			case 0:  color = Color.YELLOW;  break;
//			case 1:  color = Color.BLUE;    break;
//			case 2:  color = Color.MAGENTA; break;
//		}
//		convertView.setBackgroundColor(color);

        return convertView;
    }

    class Holder {
        public TextView bt;
        public TextView nr;
        public TextView rq;
    }
}

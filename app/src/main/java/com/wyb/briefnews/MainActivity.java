package com.wyb.briefnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ViewGroup sv;
    private ViewGroup down;
    private TextView num;
    private TextView LastNav = null;
    private TextView LastNav2 = null;
    private ViewGroup layoutNews = null;
    private ListView lstNews;
    private List<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //标题--头条 要问 经济...
        sv = (ViewGroup) findViewById(R.id.sv);
        for (int i = 0; i < sv.getChildCount(); i++) {
            num = (TextView) sv.getChildAt(i);
            num.setOnClickListener(this);
        }
        //底部翻页--餐单 图片 视频...
        down = (ViewGroup) findViewById(R.id.down);
        for (int i = 0; i < down.getChildCount(); i++) {
            num = (TextView) down.getChildAt(i);
            num.setOnClickListener(this);
        }
        //获取数据
        lstNews = (ListView) findViewById(R.id.lisNews);
        try {
            news = parseNewses();
            NewsAdapter adapter = new NewsAdapter(this);
            adapter.setData(news);
            lstNews.setAdapter(adapter);

            lstNews.setOnItemClickListener(this);
            lstNews.setOnItemLongClickListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    private List<News> parseNewses() throws IOException, XmlPullParserException {
        List<News> news = null;
        News ns = null;

        InputStream is = this.getAssets().open("rss_newstop.xml");

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(is, "UTF-8");
        int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                news = new ArrayList<News>();
            }else if(eventType == XmlPullParser.START_TAG) {
                if("item".equals(xpp.getName())) {
                    ns = new News();
                    news.add(ns);
                }else if("title".equals(xpp.getName())) {
                    if(ns != null) ns.title = xpp.nextText();
                }else if("link".equals(xpp.getName())) {
                    if(ns != null) ns.link = xpp.nextText();
                }else if("description".equals(xpp.getName())) {
                    if(ns != null) ns.description = xpp.nextText();
                }else if("pubDate".equals(xpp.getName())) {
                    if(ns != null) ns.pubDate = xpp.nextText();
                }else if("guid".equals(xpp.getName())) {
                    if(ns != null) ns.guid = xpp.nextText();
                }
            }
            eventType = xpp.next();
        }
        return news;
    }


    @Override
    public void onClick(View v) {
        //导航标题
        TextView choose = (TextView) v;

        if(LastNav != null)
            LastNav.setSelected(false);

        choose.setSelected(true);
        LastNav = choose;

        //下面五个餐单
        TextView choose2 = (TextView) v;
        if(LastNav2 != null)
            LastNav2.setSelected(false);
        choose.setSelected(true);
        LastNav2 = choose2;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News ns = news.get(position);
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.setAction(ns.guid);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        News ns = news.get(position);
        Toast.makeText(this, ns.guid, Toast.LENGTH_SHORT).show();
        return false;
    }
}

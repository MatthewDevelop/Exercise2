package com.foxconn.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.test.R;
import com.foxconn.test.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-27 上午9:11
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description: 自动分批加载
 */
public class TestListViewLoadMoreActivity2 extends Activity  implements
        AbsListView.OnScrollListener {
    private ListView mListview = null;
    private View mFooterView;
    private PaginationAdapter mAdapter;
    private Handler handler=new Handler();
    private boolean isLoading;//表示是否正在加载
    private final int MAX_COUNT=50;//表示服务器上总共有MAX_COUNT条数据
    private final int EACH_COUNT=10;//表示每次加载的条数
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mFooterView = getLayoutInflater().inflate(R.layout.loadmore_for_testactivity2, null);
        mListview = (ListView) findViewById(R.id.listview);
        mListview.addFooterView(mFooterView);// 设置列表底部视图

        List<News> news=new ArrayList<News>();
        mAdapter = new PaginationAdapter(news);;
        mListview.setAdapter(mAdapter);
        //设置setOnScrollListener会自动调用onscroll方法。
        mListview.setOnScrollListener(this);

    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (firstVisibleItem+visibleItemCount==totalItemCount&&!isLoading) {
            //isLoading = true 表示正在加载，加载完毕设置isLoading =false；
            Log.i("onScroll", "firstVisibleItem"+firstVisibleItem+" visibleItemCount"+visibleItemCount+" totalItemCount"+totalItemCount);
            isLoading = true;
            //如果服务端还有数据，则继续加载更多，否则隐藏底部的加载更多
            if (totalItemCount<=MAX_COUNT) {
                //等待2秒之后才加载，模拟网络等待时间为2s
                handler.postDelayed(new Runnable() {

                    public void run() {
                        loadMoreData();
                    }
                },2000);
            }else{
                if (mListview.getFooterViewsCount()!=0) {
                    mListview.removeFooterView(mFooterView);
                }
            }

        }

    }

    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        Log.i("onScrollStateChanged", arg1+"");
    }

    private void loadMoreData(){
        int count = mAdapter.getCount();
        for (int i = 0; i < EACH_COUNT; i++) {
            if (count+i<MAX_COUNT) {
                News item = new News();
                item.setTitle("Title" + (count+i));
                item.setContent("This is News Content" + (count+i));
                mAdapter.addNewsItem(item);
            }else{
                mListview.removeFooterView(mFooterView);
            }
        }
        mAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    class PaginationAdapter extends BaseAdapter {

        List<News> newsItems;

        public PaginationAdapter(List<News> newsitems) {
            this.newsItems = newsitems;
        }

        public int getCount() {
            return newsItems==null?0:newsItems.size();
        }

        public Object getItem(int position) {
            return newsItems.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public void addNewsItem(News newsitem) {
            newsItems.add(newsitem);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_fortest2,
                        null);
            }
            // 新闻标题
            TextView tvTitle = (TextView) convertView
                    .findViewById(R.id.newstitle);
            tvTitle.setText(newsItems.get(position).getTitle());
            // 新闻内容
            TextView tvContent = (TextView) convertView
                    .findViewById(R.id.newscontent);
            tvContent.setText(newsItems.get(position).getContent());

            return convertView;
        }
    }
}
package com.foxconn.test.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.foxconn.test.R;
import com.foxconn.test.sql.Dao;

import java.util.ArrayList;
import java.util.List;
/**********************************************************
 *Function Name : 分批加载
 *Author : LiXueLong 李雪龙
 *Modify Date : 17-6-27 上午10:07
 *Feature :  N/A
 *
 *Input Parameter & Range : N/A
 *
 *Output Parameter & Range : N/A
 *
 *Return Value : N/A
 *
 *********************************************************/
public class TestListViewLoadMoreActivity extends Activity {
    /**
     * 进度条
     */
    private LinearLayout llProgress;

    private ListView lvLoad;

    /**
     * 操作数据库
     */
    private Dao dao;

    /**
     * 适配器的数据源
     */
    private List<String> mDataList;

    /**
     * 下一批数据
     */
    private List<String> mMoreData;

    /**
     * 下一批数据开始的位置
     */
    private int mStartIndex = 0;

    /**
     * 下一批数据的数量
     */
    private int mMaxCount = 20;

    /**
     * 数据总数
     */
    private int mTotalCount = -1;

    /**
     * 适配器
     */
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 实例化控件
        llProgress = (LinearLayout) findViewById(R.id.ll_progress);
        lvLoad = (ListView) findViewById(R.id.lv_load);

        // 设置滑动监听
        lvLoad.setOnScrollListener(new AbsListView.OnScrollListener() {
            // 滑动状态发生改变时回调
            // SCROLL_STATE_IDLE 闲置状态，此时没有滑动
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        // 获取屏幕上可见的最后一项
                        int position = lvLoad.getLastVisiblePosition();
                        // 如果屏幕上可见的最后一项是当前适配器数据源的最后一项，
                        // 并且数据还没有加载完，就加载下一批数据。
                        if (position == mDataList.size() - 1 && position != mTotalCount - 1) {
                            mStartIndex += mMaxCount;
                            Log.e("mStartIndex ： ",mStartIndex + "");
                            // 加载下一批数据
                            new LoadDataTask().execute();
                        } else if (position == mDataList.size() - 1 && position == mTotalCount -
                                1) {
                            Toast.makeText(TestListViewLoadMoreActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        dao = new Dao(TestListViewLoadMoreActivity.this);
        mDataList = new ArrayList<>();
        mMoreData = new ArrayList<>();

        // 加载第一批数据
        new LoadDataTask().execute();
    }

    class LoadDataTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected void onPreExecute() {
            // 显示进度条
            llProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            // 模拟耗时
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 查询一共有多少数据
            if (mTotalCount == -1) {
                mTotalCount = dao.getTotalCount();
            }
            Log.e("mTotalCount : ",mTotalCount + "");
            // 分批加载
            mMoreData = dao.loadMore(mStartIndex, mMaxCount);
            Log.e("mMoreData : ",mMoreData.size() + "");
            return mMoreData;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            // 隐藏进度条
            llProgress.setVisibility(View.GONE);
            // 将新增数据追加到适配器的数据源中
            mDataList.addAll(mMoreData);
            if (mAdapter == null) {
                mAdapter = new ArrayAdapter<>(TestListViewLoadMoreActivity.this, android.R
                        .layout.simple_list_item_1, mDataList);
                lvLoad.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}

package com.foxconn.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.test.R;
import com.foxconn.test.model.PersonInfo;
import com.foxconn.test.sql.ListViewDAO;

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
public class TestListViewLoadMoreActivity3 extends Activity {
    private ListView lv_callsms_safe;
    private List<PersonInfo> infos;
    private ListViewDAO dao;
    private ListViewAdapter adapter;
    private LinearLayout ll_loading;
    private int offset = 0;
    private int maxnumber = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);

        lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);

        dao = new ListViewDAO(TestListViewLoadMoreActivity3.this);
        // 里面没有数据就插入一些数据
        if (dao.findAll() == null) {
            for (int i = 0; i < 100; i++) {
                dao.add("zhangsan" + i, "nan" + 1, 1 + i);
            }
        }
        infos = dao.findPart(offset, maxnumber);
//      adapter = new ListViewAdapter();
//      lv_callsms_safe.setAdapter(adapter);
        findblacknumber();

        // 注册一个滚动时间的监听器
        lv_callsms_safe.setOnScrollListener(new AbsListView.OnScrollListener() {
            // 当滚动状态放生改变时调用
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 空闲状态
                        // 判断当前listview滚动的位置
                        // 获取最后一条可见条目在集合里面的位置
                        int lastVisiblePosition = lv_callsms_safe
                                .getLastVisiblePosition();
                        System.out.println("最后一个可见条目的位置---" + lastVisiblePosition);
                        // 到了最后一个可见位置后继续查找
                        if (lastVisiblePosition == infos.size() - 1) {
                            offset += maxnumber;
                            findblacknumber();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: // 触摸状态

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 惯性滑行状态

                        break;

                    default:
                        break;
                }
            }

            // 滚动时调用
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void findblacknumber() {
        ll_loading.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (infos == null) {
                    infos = dao.findPart(offset, maxnumber);
                } else {
                    infos.addAll(dao.findPart(offset, maxnumber));
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        ll_loading.setVisibility(View.INVISIBLE);
                        if (adapter == null) {
                            adapter = new ListViewAdapter();
                            lv_callsms_safe.setAdapter(adapter);
                        } else {
                            // adapter存在的话，通知更新
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(),
                        R.layout.list_item_fortest3, null);
                holder = new ViewHolder();
                holder.tv_item_name = (TextView) view
                        .findViewById(R.id.tv_item_name);
                holder.tv_item_gender = (TextView) view
                        .findViewById(R.id.tv_item_gender);
                holder.tv_item_age = (TextView) view
                        .findViewById(R.id.tv_item_age);
                // 设置一个标示，当孩子生出来的时候找到他们的引用，存放在记事本里面，放在父亲的口袋
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();// 5%的效率提升
            }
            holder.tv_item_name.setText(infos.get(position).getName());
            holder.tv_item_gender.setText(infos.get(position).getGender());
            holder.tv_item_age.setText(infos.get(position).getAge() + "");
            return view;
        }

    }

    /**
     * view对象的容器，相当于一个记事本
     *
     * @author Administrator 静态的字节码只加载了一次
     */
    static class ViewHolder {
        TextView tv_item_name;
        TextView tv_item_gender;
        TextView tv_item_age;
    }
}
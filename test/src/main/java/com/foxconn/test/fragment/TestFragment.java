package com.foxconn.test.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.foxconn.test.MainActivity;
import com.foxconn.test.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by：LiXueLong 李雪龙 on 17-3-28
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TestFragment extends Fragment implements Animator.AnimatorListener {
    protected View mMainView;
    protected static ArrayList<Map<String, Object>> mlistItems;
    protected Context mContext;
    protected ListView mListView;

    private View mLayout;
    private View mSearchlayout;
    private MainActivity.MyOnTouchListener myOnTouchListener;
    private boolean mIsTitleHide = false;
    private boolean mIsAnim = false;
    private float lastX = 0;
    private float lastY = 0;

    private boolean isDown = false;
    private boolean isUp = false;

    static {
        mlistItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "name#" + i);
            map.put("sex", i % 2 == 0 ? "male" : "female");
            mlistItems.add(map);
        }
    }

    public TestFragment() {
        super();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_test, container, false);
        mListView = (ListView) mMainView.findViewById(R.id.list);
        mLayout = mMainView.findViewById(R.id.layout);
        mSearchlayout = mMainView.findViewById(R.id.search_layout);

        return mMainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(mContext, mlistItems,
                R.layout.listview_item_for_testfragment,
                new String[]{"name", "sex"},
                new int[]{R.id.name, R.id.download});
        mListView.setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        myOnTouchListener = new MainActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((MainActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }

    public void setMarginTop(int page) {
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        layoutParam.setMargins(0, page, 0, 0);
        mListView.setLayoutParams(layoutParam);
        mListView.invalidate();
    }

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
                isUp = dX < 8 && dY > 8 && !mIsTitleHide && !down;
                isDown = dX < 8 && dY > 8 && mIsTitleHide && down;
                if (isUp) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = 0.0F;
                    f[1] = -mSearchlayout.getHeight();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.setDuration(200);
                    animator1.start();
                    animator1.addListener(this);
                    setMarginTop(mLayout.getHeight() - mSearchlayout.getHeight());
                } else if (isDown) {
                    View view = this.mLayout;
                    float[] f = new float[2];
                    f[0] = -mSearchlayout.getHeight();
                    f[1] = 0F;
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
                    animator1.setDuration(200);
                    animator1.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator1.start();
                    animator1.addListener(this);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                break;
            default:
                return false;
        }
        return false;

    }

    @Override
    public void onAnimationCancel(Animator arg0) {

    }

    @Override
    public void onAnimationEnd(Animator arg0) {
        if (isDown) {
            setMarginTop(mLayout.getHeight());
        }
        mIsAnim = false;
    }

    @Override
    public void onAnimationRepeat(Animator arg0) {

    }

    @Override
    public void onAnimationStart(Animator arg0) {

    }

}

package com.foxconn.matthew.networkdemo;

/**
 * Created by Matthew on 2017/9/28.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}

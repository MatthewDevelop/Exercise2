package com.foxconn.matthew.bestservicepractice;

/**
 * Created by Matthew on 2017/9/29.
 */

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPause();

    void onCancel();
}

package com.foxconn.matthew.mediademo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class AudioAndVedio extends AppCompatActivity {

    private static final String TAG = "AudioAndVedio";

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_and_vedio);
        videoView = (VideoView) findViewById(R.id.videoView);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initAudio();
            initVideo();
        }
    }

    private void initVideo() {
        File file = new File(Environment.getExternalStorageDirectory(), "Clip1080.mp4");
        Log.e(TAG, "initVideo: " + file.getPath());
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAudio();
                    initVideo();
                } else {
                    Toast.makeText(this, "You denied the permission,bye!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void initAudio() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "08PurplePassion.mp3");
            Log.e(TAG, "initAudio: " + file.getPath());
            mediaPlayer.setDataSource(file.getPath());
//            mediaPlayer.setDataSource("/sdcard/08PurplePassion.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.audio_play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.audio_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.audio_stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    initAudio();
                }
                break;
            case R.id.video_play:
                if (!videoView.isPlaying())
                    videoView.start();
                break;
            case R.id.video_pause:
                if (videoView.isPlaying())
                    videoView.pause();
                break;
            case R.id.video_replay:
                if (videoView.isPlaying())
                    videoView.resume();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (videoView != null) {
            videoView.suspend();
        }
    }
}

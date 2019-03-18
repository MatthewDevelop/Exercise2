package com.foxconn.matthew.mediademo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageView picture;
    Uri imageUrl;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picture = (ImageView) findViewById(R.id.iv_photo);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notification:
                Intent intent = new Intent(this, AnotherActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(this)
                        .setContentTitle("MediaDemo")
                        .setContentText("Test Message")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        //.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))//设置声音
                        //.setVibrate(new long[]{0, 1000, 1000, 1000})//设置震动
                        //.setLights(Color.GREEN, 1000, 1000)//设置灯光提醒
                        /*.setStyle(new NotificationCompat.BigTextStyle().bigText("This is a text long message!" + "\n" +
                                "This is a text long message!" + "\n" +
                                "This is a text long message!" + "\n" +
                                "This is a text long message!" + "\n" +
                                "This is a text long message!" + "\n" +
                                "This is a text long message!" + "\n" +
                                "This is a text long message!"))*///设置长文本
                        /*.setStyle(new NotificationCompat//设置图片
                                .BigPictureStyle()
                                .bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round)))*/
                        .setPriority(NotificationCompat.PRIORITY_MAX)//设置通知的优先级
                        .build();
                manager.notify(1, notification);
                break;
            case R.id.take_photo:
                File outputImage = new File(getExternalCacheDir(), "pic.jpg");
                try {
                    if (outputImage.exists())
                        outputImage.delete();
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT > 24)
                    imageUrl = FileProvider.getUriForFile(this, "com.example.cameraalbumtest.provider", outputImage);
                else
                    imageUrl = Uri.fromFile(outputImage);
                Intent photoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                startActivityForResult(photoIntent, TAKE_PHOTO);
                break;
            case R.id.choose_from_album:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    openAblum();
                }
                break;
            case R.id.audio_and_video:
                Intent toTestAudioAndVideo = new Intent(this, AudioAndVedio.class);
                startActivity(toTestAudioAndVideo);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAblum();
                } else {
                    Toast.makeText(this, "You deny the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void openAblum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19)
                        handleImageOnKitKat(data);
                    else
                        handleImageBeforeKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Log.e(TAG, "handleImageBeforeKitKat: ");
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        Log.e(TAG, "handleImageOnKitKat: ");
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            Log.e(TAG, "handleImageOnKitKat: " + "DocumentType");
            //document类型的uri，则通过document id处理。
            String docId = DocumentsContract.getDocumentId(uri);
            Log.e(TAG, "handleImageOnKitKat: " + uri.getAuthority());
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                Log.e(TAG, "handleImageOnKitKat: " + "media.documents");
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Log.e(TAG, "handleImageOnKitKat: " + "downloads.documents");
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            Log.e(TAG, "handleImageOnKitKat: " + "contentType");
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            Log.e(TAG, "handleImageOnKitKat: " + "fileType");
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Failed to get image!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}

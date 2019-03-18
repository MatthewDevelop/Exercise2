package com.foxconn.test.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.foxconn.test.FileUtil;
import com.foxconn.test.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;

/**
 * Created by：LiXueLong 李雪龙 on 2017/11/1 14:04
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class SocketActivity extends AppCompatActivity {

    private static final String TAG = "SocketActivity";

    String hostIp = null;
    int hostPort;
    String reqDlInfo = null;
    String vmId = null;
    String typeId = null;

    InputStream is;
    PrintWriter out;
    BufferedReader reader = null;// 添加BufferedRead 2015-10-22

    File file = null;
    Socket client = null;

    String reqAnswer = null;
    String fileName = null;
    String fileMD5 = null;

    String savePath = null;

    boolean isTrue = true;

    Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        initData();

        mBtn = (Button) findViewById(R.id.btn_socket);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            File downLoadFolder = new File(FileUtil.getSDcardPath() + "/skylark");
                            if (!downLoadFolder.exists()) {
                                if (!downLoadFolder.mkdir()) {
                                }
                            }

                            client = new Socket(hostIp, hostPort);
                            is = client.getInputStream();
                            out = new PrintWriter((new OutputStreamWriter(
                                    client.getOutputStream())), true);
                            out.println(reqDlInfo);
                            Log.e(TAG, "run: 向服务器发送的指令为：" + reqDlInfo);
                            // 添加读取server回复的信息 为 fileName|fileMD5
                            reader = new BufferedReader(new InputStreamReader(
                                    client.getInputStream()));

                            reqAnswer = reader.readLine();
                            Log.e(TAG, "run: 服务器返回的消息为：" + reqAnswer);
                            // 读取Server端传过来的回复信息
                            if (reqAnswer != null) {
                                String[] info = reqAnswer.split("\\|");
                                fileName = info[0];
                                fileMD5 = info[1];

                                Log.e(TAG, "run: savePath: " + savePath);

                                file = new File(savePath);
                                if (!file.exists()) {
                                    if (!file.createNewFile())
                                        throw new FileNotFoundException();
                                }
                                Log.e(TAG, "run: GetFile: start");
//                                Thread t = new Thread(new GetFile(is,
//                                        new FileOutputStream(file), typeId));
//                                t.start();

                                byte[] buffer = new byte[1024];
                                int len = -1;
                                int count = 0;
                                RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");

                                while ((len = is.read(buffer)) != -1) {//从输入流中读取数据写入到文件中
                                    fileOutStream.write(buffer, 0, len);
                                    count += 1;
                                    System.out.println("len : " + len + "--->count : " + count);
                                }


                                Log.e(TAG, "run: GetFile: end");

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void initData() {
        vmId = "43-1706-0001";
        typeId = "07";
        fileName = "image";
        hostIp = "112.74.217.13";
        hostPort = 8090;
        reqDlInfo = vmId + "|" + typeId + "|" + fileName;
        savePath = FileUtil.getSDcardPath() + "/skylark/" + fileName + ".zip";
    }

    class GetFile implements Runnable {

        public InputStream is;
        public FileOutputStream fos;
        public byte[] b;
        public String str;

        public GetFile(InputStream is, FileOutputStream fos, String str) {
            this.is = is;
            this.fos = fos;
            this.str = str;
            b = new byte[1024]; // normal
        }

        @Override
        public void run() {
            try {
                int fileTotalSize = 0;
                int size = 0;
                byte[] b1 = new byte[8192];
                byte[] blen = new byte[10];

                if (str.equals("07") || str.equals("08") || str.equals("0a")) {
                    DataInputStream dis = new DataInputStream(is);
                    // 得到流的总长度
                    Log.e(TAG, "GetFile run: len01 " + dis.readLong());
                    dis.read(blen);

                    String recLenString = ByteUtil.toStr(blen);
                    String subString = recLenString;
                    Log.e(TAG, "GetFile run: subString01 " + subString.toString());
//                    if (recLenString.contains("a")) {
//                        subString = recLenString.substring(0,
//                                recLenString.indexOf("a"));
//                        Log.e(TAG, "GetFile run: subString02 " + subString);
//                    }
                    if (!subString.equals("") ){//&& isNumeric(subString)) {

//                        long len = Long.parseLong(subString);
//                        Log.e(TAG, "GetFile run: len02 " + len);
                        BufferedInputStream bis = new BufferedInputStream(dis);

                        while ((size = bis.read(b1)) != -1 && isTrue) {
                            fileTotalSize += size;
                            fos.write(b1, 0, size);
                            fos.flush();
                            Log.e(TAG, "GetFile run: fileTotalSize " + fileTotalSize);
                        }
                        Log.e(TAG, "GetFile run: finish");
                        bis.close();
                    } else {
                        Log.e(TAG, "GetFile run: fail");
                    }

                    dis.close();

                } else {
                    while ((size = is.read(b)) != -1) {
                        fileTotalSize += size;
                        fos.write(b, 0, size);
                        fos.flush();
                    }
                }

                if (fileTotalSize == 0) {
                    Log.e(TAG, "GetFile run: fileTotalSize==0");
                }
                fos.close(); // >>> 关闭文件流

            } catch (IOException e) {
                Log.e(TAG, "GetFile run: Exception " + e.getMessage());
            }
        }
    }

    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

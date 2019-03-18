package com.foxconn.test.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.foxconn.test.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-30 上午10:21
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class CallWebServiceActivity extends Activity {
    //显示结果的listview
    ListView listView = null;
    //输入文本框
    EditText provinceEdit = null;
    //用于存放数据的集合list
    List<Map<String, Object>> data = null;
    //提示对话框
    ProgressDialog myDialog = null;
    //搜索按钮
    Button searchButton = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_ws);
        //获得文本输入框
        provinceEdit = (EditText) this.findViewById(R.id.provinceEdit);
        //获得搜索按钮
        searchButton = (Button) this.findViewById(R.id.searchButton);
        //为搜索按钮添加单击监听事件
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //响应按钮单击事件的函数
                ResponseOnClick();
            }
        });
    }

    //响应按钮单击事件的函数
    public void ResponseOnClick() {
        //创建一个线程
        HttpThread thread = new HttpThread(handler);
        //构造请求参数
        HashMap<String, Object> params = new HashMap<String, Object>();
        try {
            CharSequence etValue = provinceEdit.getText();
            String name = "";
            if (etValue != null) {
                //字符转码
                name = new String(etValue.toString().getBytes(), "UTF-8");
            }
            params.put("byProvinceName", name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //
        String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
        // String url = "http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";
        String nameSpace = "http://WebXml.com.cn/";
        String methodName = "getSupportCity";
        // 开始新线程进行WebService请求
        thread.doStart(url, nameSpace, methodName, params);
    }

    /**
     * 捕获消息队列
     */
    Handler handler = new Handler() {
        public void handleMessage(Message m) {
            ArrayList<String> myList = (ArrayList<String>) m.getData().getStringArrayList("data");
            if (myList != null) {
                if (data != null) {
                    data.clear();
                } else {
                    data = new ArrayList<Map<String, Object>>();
                }
                for (int i = 0; i < myList.size(); i++) {
                    Map<String, Object> item = new HashMap<String, Object>();
                    item.put("text", myList.get(i));
                    data.add(item);
                }
                /**
                 * 列表显示
                 *
                 */
                SimpleAdapter simpleAdapter = new SimpleAdapter(CallWebServiceActivity.this
                        , data, R.layout.list_item_call_ws, new String[]{"text"}, new int[]{R.id.callws});
                listView = (ListView) findViewById(R.id.showListView);
                listView.setAdapter(simpleAdapter);
            }
        }
    };

    /**
     * 线程类
     *
     * @author Administrator
     */
    public class HttpThread extends Thread {
        private Handler handle = null;
        String url = null;
        String nameSpace = null;
        String methodName = null;
        HashMap<String, Object> params = null;
        ProgressDialog progressDialog = null;

        //构造函数
        public HttpThread(Handler hander) {
            handle = hander;
        }

        /**
         * 启动线程
         */
        public void doStart(String url, String nameSpace, String methodName,
                            HashMap<String, Object> params) {
            // TODO Auto-generated method stub
            this.url = url;
            this.nameSpace = nameSpace;
            this.methodName = methodName;
            this.params = params;
            progressDialog = ProgressDialog.show(CallWebServiceActivity.this, "提示", "正在请求请稍等……", true);
            this.start();
        }

        /**
         * 线程运行
         */
        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println("jack");
            super.run();
            try {
                //web service请求
                SoapObject result = (SoapObject) CallWebService();
                //构造数据
                ArrayList<String> list = null;
                if (result != null && result.getPropertyCount() > 0) {
                    list = new ArrayList<String>();
                    for (int i = 0; i < result.getPropertyCount(); i++) {
                        SoapPrimitive value = (SoapPrimitive) result.getProperty(i);
                        list.add(value.toString());
                    }
                    //a取消进度对话框
                    progressDialog.dismiss();
                    //构造消息
                    Message message = handle.obtainMessage();
                    Bundle b = new Bundle();
                    b.putStringArrayList("data", list);
                    message.setData(b);
                    handle.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
            }
        }

        /**
         * 请求web service
         */
        protected Object CallWebService() {
            String SOAP_ACTION = nameSpace + methodName;
            //创建SoapObject实例
            SoapObject request = new SoapObject(nameSpace, methodName);
            //生成调用web service方法的soap请求消息
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            //设置.net web service
//            envelope.dotNet = true;
            envelope.dotNet = false;
            //发送请求
            envelope.setOutputSoapObject(request);
            //请求参数
            if (params != null && !params.isEmpty()) {
                for (Iterator it = params.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry e = (Map.Entry) it.next();
                    request.addProperty(e.getKey().toString(), e.getValue());
                }
            }
            //
            HttpTransportSE androidHttpTrandsport = new HttpTransportSE(url);
            SoapObject result = null;
            try {
                //web service请求
//                androidHttpTrandsport.call(SOAP_ACTION, envelope);
                androidHttpTrandsport.call(null, envelope);
                //得到返回结果
                result = (SoapObject) envelope.getResponse();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return result;
        }
    }
}
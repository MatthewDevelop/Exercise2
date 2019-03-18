package com.foxconn.test.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.foxconn.test.FileUtil;
import com.foxconn.test.R;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-28 上午8:56
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class TestActivity extends Activity implements View.OnClickListener {

    Button mBtnTest;
    private String NAMESPACE = "http://webservice.rest.web.base.com/";
    private String SERVER_URL = "http://112.74.217.13/FSVMWS/rest/androidreport";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
//                            postServer();
//                            testAndroidWebReport();

                            Log.e("skylark","Result:" + listWeekSaleTest());
//                            Log.e("skylark","Result:" + listWeekSale().toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    /**
     * Result:{"Total":4,"Rows":[
     {"cashNum":20,"cashSum":26.5,"monthSum":746.74,"onlineNum":2
     ,"onlineSum":0.11,"saleNum":22,"saleSum":26.61,"saleTime":"2016-08-15"}

     ,{"cashNum":1,"cashSum":2,"monthSum":746.74,"onlineNum":0
     ,"onlineSum":0,"saleNum":1,"saleSum":2,"saleTime":"2016-08-12"}

     ,{"cashNum":18,"cashSum":52,"monthSum":746.74,"onlineNum":1
     ,"onlineSum":0.01,"saleNum":19,"saleSum":52.01,"saleTime":"2016-08-11"}

     ,{"cashNum":15,"cashSum":74.5,"monthSum":746.74,"onlineNum":1
     ,"onlineSum":0.01,"saleNum":16,"saleSum":74.51,"saleTime":"2016-08-09"}
     ]}
     * @return
     */
    public String listWeekSaleTest(){
        // 创建HttpTransportSE对象
        final HttpTransportSE httpSe = new HttpTransportSE(SERVER_URL);
        httpSe.debug = true;
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(NAMESPACE, "listWeekSale");
        // 添加参数
//        soapObject.addProperty("orgId", 1);
//        soapObject.addProperty("cluId", "1;31;32");
//        soapObject.addProperty("searchTime", "2016-08-15");
//        soapObject.addProperty("arg0", 1);//orgId
//        soapObject.addProperty("arg1", "1;31;32");//cluId
//        soapObject.addProperty("arg2", "2016-08-15");//searchTime
        soapObject.addProperty("arg0", 1);//orgId
        soapObject.addProperty("arg1", "1;31;32");//cluId
        soapObject.addProperty("arg2", "2016-08-15");//searchTime
        // 创建SoapSerializationEnvelope
        final SoapSerializationEnvelope serializa = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        serializa.bodyOut = soapObject;
        serializa.dotNet = false;
        serializa.encodingStyle = "UTF-8";
        Log.e("skylark","------------------");
        FutureTask<String> future = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Log.e("skylark","------------------call()");
                        List<String> list = new ArrayList<String>();
                        String result = null;
                        // 调用webservice
                        httpSe.call(NAMESPACE + "listWeekSale", serializa);
                        Log.e("skylark","serializa.getResponse():" + serializa.getResponse().toString());
                        // 获取返回信息
                        if (serializa.getResponse() != null) {
                            /**
                             * serializa.getResponse() 取单行数据
                             * serializa.bodyIn 取多行数据
                             */
                            Log.e("skylark","serializa.getResponse():" + serializa.getResponse().toString());
                            result = serializa.getResponse().toString();
                            /*SoapObject result = (SoapObject) serializa.bodyIn;
                            SoapObject deialt = (SoapObject) result
                                    .getProperty("return");
                            // 解析数据
                            for (int i = 0; i < deialt.getPropertyCount(); i++) {
                                list.add(deialt.getProperty(i).toString());
                            }*/
                        }
                        return result;
                    }
                });
        new Thread(future).start();
        try {
            return future.get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public List<String> listWeekSale() {
        // 创建HttpTransportSE对象
        final HttpTransportSE httpSe = new HttpTransportSE(SERVER_URL);
        httpSe.debug = true;
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(NAMESPACE, "listWeekSale");
        // 添加参数
//        soapObject.addProperty("orgId", 1);
//        soapObject.addProperty("cluId", "1;31;32");
//        soapObject.addProperty("searchTime", "2016-08-15");
        soapObject.addProperty("arg0", 1);
        soapObject.addProperty("arg1", "1;31;32");
        soapObject.addProperty("arg2", "2016-08-15");
        // 创建SoapSerializationEnvelope
        final SoapSerializationEnvelope serializa = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        serializa.bodyOut = soapObject;
        serializa.dotNet = false;
        serializa.encodingStyle = "UTF-8";
        Log.e("skylark","------------------");
        FutureTask<List<String>> future = new FutureTask<List<String>>(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        Log.e("skylark","------------------call()");
                        List<String> list = new ArrayList<String>();
                        // 调用webservice
                        httpSe.call(NAMESPACE + "listWeekSale", serializa);
                        Log.e("skylark","serializa.getResponse():" + serializa.getResponse().toString());
                        // 获取返回信息
                        if (serializa.getResponse() != null) {
                            /**
                             * serializa.getResponse() 取单行数据
                             * serializa.bodyIn 取多行数据
                             */
                            Log.e("skylark","serializa.getResponse():" + serializa.getResponse().toString());
                            SoapObject result = (SoapObject) serializa.bodyIn;
                            SoapObject deialt = (SoapObject) result
                                    .getProperty("return");
                            // 解析数据
                            for (int i = 0; i < deialt.getPropertyCount(); i++) {
                                list.add(deialt.getProperty(i).toString());
                            }
                        }
                        return list;
                    }
                });
        new Thread(future).start();
        try {
            return future.get();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static String[] url = {"http://112.74.217.13/FSVMWS/rest/androidreport"};
    private static String keyStorePath = FileUtil.getSDcardPath() + "/skylark/key/client_b_keystore";
    private static String trustStorePath = FileUtil.getSDcardPath() + "/skylark/key/client_b_trust";
    private static final String TAG = "skylark";
    private String URL_BASE = "http://112.74.217.13/rest/androidreport";

    private static void testAndroidWebReport() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        WebClientUtils webClientUtils = new WebClientUtils();
        // url:webservice接口访问路径
        // signPath:签名证书配置文件路径
        // encPath:加密配置文件路径

//        String signPath2 = "configs_client/clientside_sign_b.properties";
//        String encPath2 = "configs_client/clientside_enc_b.properties";
        String signPath2 = keyStorePath;
        String encPath2 = trustStorePath;

        factory = webClientUtils.createFactory(url[0], signPath2, encPath2,
                "Bob", "aclientprivate", "aserverpublic");

        // 该接口名字不一定和服务器端的webservice接口名字相同，但是其中要调用到的方法和参数类型必须一样。
        factory.setServiceClass(IAndroidWebReportService.class);

        IAndroidWebReportService androidWebReportService = (IAndroidWebReportService) factory.create();

        String spSaleResult = androidWebReportService.listSpSale(1, "1;31;32", "2016-08-15", 1, 10);
        System.out.println("spSaleResult:" + spSaleResult);

        String vmSaleResult = androidWebReportService.listVmSale(1, "1;31;32", "2016-08-15", 1, 3);
        System.out.println("vmSaleResult:" + vmSaleResult);

        String vmDailySaleResult = androidWebReportService.listVmDailySale("3-1601-0001", "2016-08-15", 1, 3);
        System.out.println("vmDailySaleResult:" + vmDailySaleResult);

        String weekSaleResult = androidWebReportService.listWeekSale(1, "1;31;32", "2016-08-15");
        System.out.println("weekSaleResult:" + weekSaleResult);

    }


}

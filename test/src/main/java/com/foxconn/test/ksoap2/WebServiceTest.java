package com.foxconn.test.ksoap2;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class WebServiceTest {

    // Webservice服务器地址
    private static final String SERVER_URL = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
    // 调用的webservice命令空间
    private static final String PACE = "http://WebXml.com.cn/";
    // 获取所有省份的方法名
    private static final String M_NAME = "getSupportProvince";
    // 获取省份包含的城市的方法名
    private static final String MC_NAME = "getSupportCity";
    // 获取天气详情的方法名
    private static final String W_NAME = "getWeatherbyCityName";

    /**
     * @return 所有省份
     */

    public static List<String> getCitys() {
        // 创建HttpTransportSE传说对象 传入webservice服务器地址
        final HttpTransportSE httpSE = new HttpTransportSE(SERVER_URL);
        httpSE.debug = true;
        // 创建soapObject对象并传入命名空间和方法名
        SoapObject soapObject = new SoapObject(PACE, M_NAME);
        // 创建SoapSerializationEnvelope对象并传入SOAP协议的版本号
        final SoapSerializationEnvelope soapserial = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        soapserial.bodyOut = soapObject;
        // 设置与.NET提供的Web service保持有良好的兼容性
        //true是网络 false是java
        soapserial.dotNet = true;
//        soapserial.dotNet = false;
        // 使用Callable与Future来创建启动线程
        FutureTask<List<String>> future = new FutureTask<List<String>>(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        List<String> citys = new ArrayList<String>();
                        // 调用HttpTransportSE对象的call方法来调用 webserice
                        httpSE.call(PACE + M_NAME, soapserial);
                        if (soapserial.getResponse() != null) {
                            Log.e("skylark",soapserial.getResponse().toString());
                            // 获取服务器响应返回的SOAP消息
                            SoapObject result = (SoapObject) soapserial.bodyIn;
                            SoapObject detail = (SoapObject) result
                                    .getProperty("getSupportProvinceResult");
                            // 解析返回信息
                            for (int i = 0; i < detail.getPropertyCount(); i++) {
                                citys.add(detail.getProperty(i).toString());
                            }
                            return citys;
                        }
                        return null;
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

    /**
     * @param citys 省份
     * @return 该省下的所有城市
     */
    public static List<String> getCity(String citys) {

        // 创建HttpTransportSE对象
        final HttpTransportSE httpSE = new HttpTransportSE(SERVER_URL);
        httpSE.debug = true;
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(PACE, MC_NAME);
        // 添加参数
        soapObject.addProperty("byProvinceName ", citys);
        // 创建SoapSerializationEnvelope
        final SoapSerializationEnvelope serializa = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        serializa.bodyOut = soapObject;
        serializa.dotNet = true;
        serializa.encodingStyle="UTF-8";
        FutureTask<List<String>> future = new FutureTask<List<String>>(
                new Callable<List<String>>() {

                    @Override
                    public List<String> call() throws Exception {
                        List<String> city = new ArrayList<String>();
                        // 调用Web Service
                        httpSE.call(PACE + MC_NAME, serializa);
                        // 获取返回信息
                        if (serializa.getResponse() != null) {
                            SoapObject restul = (SoapObject) serializa.bodyIn;
                            SoapObject detial = (SoapObject) restul
                                    .getProperty("getSupportCityResult");
                            // 解析返回信息
                            for (int i = 0; i < detial.getPropertyCount(); i++) {
                                // 获取城市名
                                String str = detial.getPropertyAsString(i);
                                String strCity = str.substring(0,
                                        str.indexOf("(") - 1);
                                city.add(strCity);
                            }
                            return city;
                        }
                        return null;
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

    /**
     * 获取三天之内的天气详情
     * @param citys
     * @return
     */
    public static List<String> getWeather(String citys) {
        final HttpTransportSE httpSe = new HttpTransportSE(SERVER_URL);
        httpSe.debug = true;
        SoapObject soapObject = new SoapObject(PACE, W_NAME);
        soapObject.addProperty("theCityName", citys);
        final SoapSerializationEnvelope serializa = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        serializa.bodyOut = soapObject;
        serializa.dotNet = true;
        FutureTask<List<String>> future = new FutureTask<List<String>>(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        List<String> list = new ArrayList<String>();
                        // 调用webservice
                        httpSe.call(PACE + W_NAME, serializa);
                        // 获取返回信息
                        if (serializa.getResponse() != null) {

                            Log.e("skylark",serializa.getResponse().toString());
                            SoapObject result = (SoapObject) serializa.bodyIn;
                            SoapObject deialt = (SoapObject) result
                                    .getProperty("getWeatherbyCityNameResult");
                            Log.e("skylark",deialt.getProperty(0).toString());
                            // 解析数据
//                            for (int i = 0; i < deialt.getPropertyCount(); i++) {
//                                list.add(deialt.getProperty(i).toString());
//                            }
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

}

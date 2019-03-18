package com.foxconn.test.test;

import com.foxconn.test.FileUtil;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.net.MalformedURLException;

public class TestEncryptWsHandler {

    private static String[] url = {"http://localhost:8080/FSVMWS/rest/androidreport"};
    private static String signPath = "configs_client/clientside_sign_a.properties";
    private static String encPath = "configs_client/clientside_enc_a.properties";
    private static String keyStorePath = FileUtil.getSDcardPath() + "/skylark/client_b_keystore";
    private static String trustStorePath = FileUtil.getSDcardPath() + "/skylark/client_b_trust";

    /**
     * WS-Security的客户端调用入口程式
     *
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {


        testAndroidWebReport();
    }


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

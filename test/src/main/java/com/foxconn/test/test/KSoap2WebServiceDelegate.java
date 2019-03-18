package com.foxconn.test.test;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-30 下午3:09
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public interface KSoap2WebServiceDelegate {
    public void handleException(Object ex);
    public void handleResultOfWebService(String methodName,Object result);
}

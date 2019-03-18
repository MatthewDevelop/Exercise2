package com.foxconn.test.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

import java.util.HashMap;
import java.util.Map;

public class WebClientUtils {
    private static String signPath = "configs_client/clientside_sign.properties";
    private static String encPath = "configs_client/clientside_enc.properties";

    public JaxWsProxyFactoryBean createFactory(String url) {
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps = getOutParam(signPath, encPath);
        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps = getInParam(encPath, signPath);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        // 设置ws访问地址
        factory.setAddress(url);
        factory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
        factory.getInInterceptors().add(new WSS4JInInterceptor(inProps));
        return factory;
    }

    public JaxWsProxyFactoryBean createFactory(String url, String signPath,
                                               String encPath) {
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps = getOutParam(signPath, encPath);
        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps = getInParam(encPath, signPath);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        // 设置ws访问地址
        factory.setAddress(url);
        // 发送认证信息
        factory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
        // 接收认证信息
        factory.getInInterceptors().add(new WSS4JInInterceptor(inProps));
        return factory;
    }

    public JaxWsProxyFactoryBean createFactory(String url, String signPath,
                                               String encPath, String userName, String signName, String encName) {
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps = getOutParam(signPath, encPath, userName, signName, encName);
        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps = getInParam(encPath, signPath, userName);
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        // 设置ws访问地址
        factory.setAddress(url);
        // 发送认证信息
        factory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
        // 接收认证信息
        factory.getInInterceptors().add(new WSS4JInInterceptor(inProps));
        return factory;
    }

    private Map<String, Object> getInParam(String signPath, String decPath) {
        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.USERNAME_TOKEN + " "
                        + WSHandlerConstants.SIGNATURE + " "
                        + WSHandlerConstants.ENCRYPT);
        // inProps.put(WSHandlerConstants.USER, "gaoqishi");
        inProps.put(WSHandlerConstants.USER, "admin");
        // outProps.put(WSHandlerConstants.SIGNATURE_USER,
        // "merrickserverpublic");
        inProps.put(WSHandlerConstants.SIG_PROP_FILE, signPath);
        // outProps.put(WSHandlerConstants.ENCRYPTION_USER,
        // "merrickclientprivate");
        inProps.put(WSHandlerConstants.DEC_PROP_FILE, decPath);
        // PW_TEXT为明文，PW_DIGEST为密文
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // 指定在接收远程ws之前触发的回调函数PasswordHandler，其实类似于一个拦截器
        inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                PasswordHandler.class.getName());
        return inProps;
    }

    private Map<String, Object> getOutParam(String signPath, String encPath) {
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.USERNAME_TOKEN + " "
                        + WSHandlerConstants.SIGNATURE + " "
                        + WSHandlerConstants.ENCRYPT);
        // 必须要有一个默认的USER。
        // 如果WSHandlerConstants.ACTION没有UsernameToken，
        // 则WSHandlerConstants.SIGNATURE_USER改为WSHandlerConstants.USER
        outProps.put(WSHandlerConstants.USER, "admin");

        //outProps.put(WSHandlerConstants.SIGNATURE_USER, "aclientprivate");
        outProps.put(WSHandlerConstants.SIGNATURE_USER, "merrickclientprivate");
        outProps.put(WSHandlerConstants.SIG_PROP_FILE, signPath);

        //outProps.put(WSHandlerConstants.ENCRYPTION_USER, "aserverpublic");
        outProps.put(WSHandlerConstants.ENCRYPTION_USER, "merrickserverpublic");
        outProps.put(WSHandlerConstants.ENC_PROP_FILE, encPath);

        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // 指定在调用远程ws之前触发的回调函数PasswordHandler，其实类似于一个拦截器
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                PasswordHandler.class.getName());
        return outProps;
    }

    private Map<String, Object> getInParam(String signPath, String decPath,
                                           String userName) {
        Map<String, Object> inProps = new HashMap<String, Object>();
        inProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.USERNAME_TOKEN + " "
                        + WSHandlerConstants.SIGNATURE + " "
                        + WSHandlerConstants.ENCRYPT);
        // inProps.put(WSHandlerConstants.USER, "gaoqishi");
        inProps.put(WSHandlerConstants.USER, userName);
        // outProps.put(WSHandlerConstants.SIGNATURE_USER,
        // "merrickserverpublic");
        inProps.put(WSHandlerConstants.SIG_PROP_FILE, signPath);
        // outProps.put(WSHandlerConstants.ENCRYPTION_USER,
        // "merrickclientprivate");
        inProps.put(WSHandlerConstants.DEC_PROP_FILE, decPath);
        // PW_TEXT为明文，PW_DIGEST为密文
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // 指定在接收远程ws之前触发的回调函数PasswordHandler，其实类似于一个拦截器
        inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                PasswordHandler.class.getName());
        return inProps;
    }

    private Map<String, Object> getOutParam(String signPath, String encPath,
                                            String userName, String signName, String encName) {
        Map<String, Object> outProps = new HashMap<String, Object>();
        outProps.put(WSHandlerConstants.ACTION,
                WSHandlerConstants.USERNAME_TOKEN + " "
                        + WSHandlerConstants.SIGNATURE + " "
                        + WSHandlerConstants.ENCRYPT);
        // 必须要有一个默认的USER。
        // 如果WSHandlerConstants.ACTION没有UsernameToken，
        // 则WSHandlerConstants.SIGNATURE_USER改为WSHandlerConstants.USER
        outProps.put(WSHandlerConstants.USER, userName);
        // outProps.put(WSHandlerConstants.SIGNATURE_USER, "aclientprivate");
        outProps.put(WSHandlerConstants.SIGNATURE_USER, signName);
        outProps.put(WSHandlerConstants.SIG_PROP_FILE, signPath);
        // outProps.put(WSHandlerConstants.ENCRYPTION_USER, "aserverpublic");
        outProps.put(WSHandlerConstants.ENCRYPTION_USER, encName);
        outProps.put(WSHandlerConstants.ENC_PROP_FILE, encPath);
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // 指定在调用远程ws之前触发的回调函数PasswordHandler，其实类似于一个拦截器
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,
                PasswordHandler.class.getName());
        return outProps;
    }
}

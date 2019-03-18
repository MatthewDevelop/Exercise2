package com.foxconn.test.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by：LiXueLong 李雪龙 on 17-6-30 下午2:57
 * <p>
 * Mail : skylarklxlong@outlook.com
 * <p>
 * Description:
 */
public class KSoap2Utils {

    public String namespace = "http://webservice.rest.web.base.com/";
    public String serviceURL = "http://112.74.217.13/FSVMWS/rest/androidreport";

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

    /**
     * 解密、验证
     * @param signPath
     * @param decPath
     * @param userName
     * @return
     */
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

    /**
     * 签名、加密
     * @param signPath
     * @param encPath
     * @param userName
     * @param signName
     * @param encName
     * @return
     */
    private Map<String, Object> getOutParam(String signPath, String encPath,
                                            String userName, String signName,
                                            String encName) {
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

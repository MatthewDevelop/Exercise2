package com.foxconn.test.test;


import org.apache.ws.security.WSPasswordCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class PasswordHandler implements CallbackHandler {

    private Map<String, String> passwords = new HashMap<String, String>();

    public PasswordHandler() {
        /**
         * 用户名，密码
         */
        passwords.put("Bob", "Bob");
        /**
         *  签名key
         */
        passwords.put("aclientprivate", "bclientpasswd");
        /**
         * 加密key
         */
        passwords.put("aserverpublic", "bserverpasswd");
        /**
         * 验证签名key
         */
        passwords.put("aserverpublic", "bserverpasswd");
        /**
         *解密key
         */
        passwords.put("aclientprivate", "bclientpasswd");
    }

    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
            System.out.println(pc.getIdentifier());
            pc.setPassword(passwords.get(pc.getIdentifier()));
        }
    }
}

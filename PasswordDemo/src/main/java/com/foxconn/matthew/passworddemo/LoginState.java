package com.foxconn.matthew.passworddemo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Matthew on 2017/12/14.
 */

/**
 * {"result":"true"
 * ,"userInfo":[{"englishName":"","address":"","sex":"男","name":"NTHZ01","chineseName":"南通弘准","telNum":"","fax":"","userId":27,"email":""}]}
 */

public class LoginState {
    @SerializedName("result")
    boolean loginState;
    @SerializedName("userInfo")
    ArrayList<UserInfo> mUserInfoArrayList;

    public boolean isLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    public ArrayList<UserInfo> getUserInfoArrayList() {
        return mUserInfoArrayList;
    }

    public void setUserInfoArrayList(ArrayList<UserInfo> userInfoArrayList) {
        mUserInfoArrayList = userInfoArrayList;
    }

    @Override
    public String toString() {
        return "LoginState{" +
                "loginState=" + loginState +
                ", mUserInfoArrayList=" + mUserInfoArrayList +
                '}';
    }
}

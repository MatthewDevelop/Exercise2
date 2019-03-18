package com.foxconn.matthew.passworddemo;

/**
 * Created by Matthew on 2017/12/14.
 */

/**
 * userId             用户Id(Int)
 *name             用户名(String)
 *address           用户地址(String)
 *englishName       用户英文名(String)
 *chineseName      用户中文名(String)
 *sex               用户性别(String)
 *email             邮箱(String)
 *fax               传真(String)
 *telNum            联系方式(String)
 */

/**
 * [{"englishName":"","address":"","sex":"男","name":"NTHZ01","chineseName":"南通弘准","telNum":"","fax":"","userId":27,"email":""}]
 */
public class UserInfo {
    int userId;
    String name;
    String address;
    String englishName;
    String chineseName;
    String sex;
    String email;
    String fax;
    String telNum;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", englishName='" + englishName + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", fax='" + fax + '\'' +
                ", telNum='" + telNum + '\'' +
                '}';
    }
}

package com.wyp.boot.earthlyfisher.domain;

import com.wyp.boot.earthlyfisher.pojo.User;

import java.util.List;

/**
 * Created by earthlyfisher on 2017/5/5.
 */
public class UserDomain {

    private User user;

    private String rePwd;

    private String originPwd;

    private List<Long> roleIdLst;

    private List<Long> menuIdLst;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRePwd() {
        return rePwd;
    }

    public void setRePwd(String rePwd) {
        this.rePwd = rePwd;
    }

    public String getOriginPwd() {
        return originPwd;
    }

    public void setOriginPwd(String originPwd) {
        this.originPwd = originPwd;
    }

    public List<Long> getRoleIdLst() {
        return roleIdLst;
    }

    public void setRoleIdLst(List<Long> roleIdLst) {
        this.roleIdLst = roleIdLst;
    }

    public List<Long> getMenuIdLst() {
        return menuIdLst;
    }

    public void setMenuIdLst(List<Long> menuIdLst) {
        this.menuIdLst = menuIdLst;
    }
}

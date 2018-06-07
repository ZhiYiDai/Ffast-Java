package cn.ffast.core.auth;

import java.util.List;
import java.util.Set;

/**
 * @description: 当前登录用户基础类
 * @copyright:
 * @createTime: 2017/8/31 9:02
 * @author：dzy
 * @version：1.0
 */
public class OperatorBase {
    //当前登录用户名
    protected String userName;
    //当前登录用户id
    protected Long userId;

    protected String token;
    //当前登录账号姓名
    protected String name;
    /**
     * 拥有的角色id
     */
    protected Set<Long> hasRoleId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Long> getHasRoleId() {
        return hasRoleId;
    }

    public void setHasRoleId(Set<Long> hasRoleId) {
        this.hasRoleId = hasRoleId;
    }
}

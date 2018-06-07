package cn.ffast.web.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import cn.ffast.core.support.BaseEntity;

/**
 * @description: 系统_用户
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_sys_user")
public class User extends BaseEntity<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户状态【1启用、0禁用】
     */
    private Integer status;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String tel;
    /**
     * 是否锁定【1是、0否】
     */
    @TableField("is_lock")
    private Integer isLock;
    /**
     * 锁定时间
     */
    @TableField("lock_time")
    private Date lockTime;
    /**
     * 登录次数
     */
    @TableField("login_count")
    private Long loginCount;
    /**
     * 失败次数
     */
    @TableField("login_failure_count")
    private Long loginFailureCount;
    /**
     * 登录Ip
     */
    @TableField("login_ip")
    private String loginIp;
    /**
     * 登录时间
     */
    @TableField("login_time")
    private String loginTime;

	/**
	 * 密码盐
	 */
	@TableField("salt")
	private String salt;

	@TableField(exist = false)
	private String roleId;



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public Long getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Long loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}

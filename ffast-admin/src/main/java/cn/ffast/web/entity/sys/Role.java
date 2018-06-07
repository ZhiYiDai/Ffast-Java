package cn.ffast.web.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cn.ffast.core.support.BaseEntity;

/**
 * @description: 系统_角色
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_sys_role")
public class Role extends BaseEntity<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色标识
     */
    private String role;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 角色状态
     */
    private Integer status;
    /**
     * 是否内置
     */
    @TableField("is_sys")
    private Integer isSys;

	private String main;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}
}

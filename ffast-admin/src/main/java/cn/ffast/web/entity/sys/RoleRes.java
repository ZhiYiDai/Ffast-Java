package cn.ffast.web.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cn.ffast.core.support.BaseEntity;

/**
 * @description: 系统_角色资源
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_sys_role_res")
public class RoleRes extends BaseEntity<RoleRes> {

    private static final long serialVersionUID = 1L;

    @TableField("role_id")
    private Long roleId;
    @TableField("res_id")
    private Long resId;

	/**
	 * 资源标识符
	 */
	@TableField(exist = true)
	private String identity;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}

package cn.ffast.web.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cn.ffast.core.support.BaseEntity;

/**
 * @description: 系统_资源
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_sys_res")
public class Res extends BaseEntity<Res> {

    private static final long serialVersionUID = 1L;

    /**
     * 资源标识符
     */
    private String identity;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 父资源
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 父路径
     */
    @TableField("parent_ids")
    private String parentIds;
    /**
     * 菜单权重
     */
    private Integer weight;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 资源类型（1=显示2禁止0=隐藏）
     */
    private Integer status;
    /**
     * 资源类型（1=菜单2=权限）
     */
    @TableField("res_type")
    private Integer resType;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}


}

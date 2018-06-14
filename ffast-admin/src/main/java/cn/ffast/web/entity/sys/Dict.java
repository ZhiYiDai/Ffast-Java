package cn.ffast.web.entity.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import cn.ffast.core.support.BaseEntity;

/**
 * @description: 字典
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_sys_dict")
public class Dict extends BaseEntity<Dict> {

    private static final long serialVersionUID = 1L;

    /**
     * 类型ID
     */
    @TableField("dict_type_id")
    private Long dictTypeId;

	/**
	 * 父类ID
	 */
	@TableField("parent_id")
	private Long parentId;

    /**
     * 编号
     */
    private String code;
    /**
     * 是否系统内置【是1、否0】
     */
    @TableField("is_sys")
    private Integer isSys;
    /**
     * 排序
     */
    private Integer weight;


	/**
	 * 备注
	 */
	private String note;

	public Long getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(Long dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}


	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}

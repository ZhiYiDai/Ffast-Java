package cn.ffast.web.entity.sys;

import cn.ffast.core.support.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @description: 系统_附件
 * @copyright:
 * @createTime: 2017-09-27 15:47:59
 * @author: lxb
 * @version: 1.0
 */
@TableName(value="t_sys_attach")
public class Attach extends BaseEntity<Attach> {

    private static final long serialVersionUID = 1L;

    /**
     * 路径
     */
    private String path;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件后缀
     */
    private String extention;
	/**
	 * 引用计数
	 */
    private Integer count;
	/**
	 * 创建人
	 */
	@TableField(exist=false)
	private String creatorName;
	/**
	 * 判断文件是否存在
	 */
	@TableField(exist=false)
	private String exist;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getCreatorName() {return creatorName;}

	public void setCreatorName(String creatorName) {this.creatorName = creatorName;}

	public String getExist() {return exist;}

	public void setExist(String exist) {this.exist = exist;}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}

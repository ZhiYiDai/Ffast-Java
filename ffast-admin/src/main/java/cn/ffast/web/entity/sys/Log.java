package cn.ffast.web.entity.sys;

import cn.ffast.core.support.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @description: 操作日志表
 * @copyright:
 * @createTime: 2017-11-14 14:48:11
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_sys_log")
public class Log extends BaseEntity<Log> {

    private static final long serialVersionUID = 1L;

    /**
     * 日志内容
     */
    private String content;
    /**
     * 用户操作
     */
    private String operation;

	/**
	 * 操作人姓名
	 */
	@TableField(exist=false)
	private String creatorName;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getCreatorName() {return creatorName;}

	public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
}

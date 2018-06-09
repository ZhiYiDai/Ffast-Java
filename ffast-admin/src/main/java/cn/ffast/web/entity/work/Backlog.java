package cn.ffast.web.entity.work;


import cn.ffast.core.support.BaseEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @description: 待办事项
 * @copyright:
 * @createTime: 2018-06-09 13:48:53
 * @author: dzy
 * @version: 1.0
 */
@TableName(value="t_work_backlog",resultMap="BaseResultMap")
public class Backlog extends BaseEntity<Backlog> {

    private static final long serialVersionUID = 1L;

    /**
     * 内容
     */
    private String content;
    /**
     * 图片
     */
    private String pictures;
    /**
     * 待办开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 待办完成时间
     */
    @TableField("finish_time")
    private String finishTime;
    /**
     * 来源模块
     */
    @TableField("from_module")
    private Integer fromModule;
    /**
     * 来源id
     */
    @TableField("from_id")
    private Long fromId;
    /**
     * 优先级（0=一般1=重要）
     */
    private Integer priority;
    /**
     * 待办用户
     */
    @TableField("user_ids")
    private String userIds;
    /**
     * 状态(0=未完成1=已完成)
     */
    private Integer status;
    /**
     * 提前多少天提醒
     */
    @TableField("inform_days")
    private Integer informDays;
    /**
     * 开启提醒
     */
    @TableField("inform_enable")
    private Integer informEnable;
    /**
     * 通知渠道
     */
    @TableField("inform_type")
    private String informType;
    /**
     * 通知状态（0=未通知1=已通知2=已提前通知）
     */
    @TableField("inform_status")
    private Integer informStatus;

	@TableField(exist = false)
	private String afterDate;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getFromModule() {
		return fromModule;
	}

	public void setFromModule(Integer fromModule) {
		this.fromModule = fromModule;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getInformDays() {
		return informDays;
	}

	public void setInformDays(Integer informDays) {
		this.informDays = informDays;
	}

	public Integer getInformEnable() {
		return informEnable;
	}

	public void setInformEnable(Integer informEnable) {
		this.informEnable = informEnable;
	}

	public String getInformType() {
		return informType;
	}

	public void setInformType(String informType) {
		this.informType = informType;
	}

	public Integer getInformStatus() {
		return informStatus;
	}

	public void setInformStatus(Integer informStatus) {
		this.informStatus = informStatus;
	}

	public String getAfterDate() {
		return afterDate;
	}

	public void setAfterDate(String afterDate) {
		this.afterDate = afterDate;
	}
}

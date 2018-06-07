package cn.ffast.core.support;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @description: 实体基类
 * @copyright:
 * @createTime: 2017年9月12日下午5:27:50
 * @author：dzy
 * @version：1.0
 */
public class BaseEntity<T extends Model> extends Model<T> implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = -34115333603863619L;
    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;
    /**
     * name
     */
    @TableId(value = "name")
    protected String name;
    /**
     * 创建人
     */
    @TableField("creator_id")
    private Long creatorId;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;
    /**
     * 最后修改时间
     */
    @TableField(value = "last_modify_time", fill = FieldFill.INSERT_UPDATE, update = "now()")
    private String lastModifyTime;
    /**
     * 最后修改人
     */
    @TableField("last_modifier_id")
    private Long lastModifierId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

package cn.ffast.core.vo;


import java.util.List;

/**
 * @description: 树
 * @copyright:
 * @createTime: 2017/12/8 14:15
 * @author：dzy
 * @version：1.0
 */
public class Tree {
    private Long parentId;
    private Long id;
    private String name;
    private String title;
    private List<Tree> children;
    private String type;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

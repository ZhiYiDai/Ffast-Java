package cn.ffast.core.vo;


/**
 * @description:
 * @copyright:
 * @createTime: 2017/8/31 17:23
 * @author：dzy
 * @version：1.0
 */

public class Menu {
    private Long id;
    /*
    菜单名
     */
    private String name;
    /*
    菜单图标
     */
    private String icon;
    /*
    访问路径
     */
    private String url;
    /*
    组件名
     */
    private String component;
    /**
     * 父id
     */
    private Long parentId;


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}

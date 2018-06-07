package cn.ffast.web.vo;

import cn.ffast.core.vo.Menu;
import java.util.HashSet;
import java.util.List;

/**
 * @description: 角色菜单权限
 * @copyright:
 * @createTime: 2017/11/14 9:28
 * @author：dzy
 * @version：1.0
 */

public class RoleMenuPerms {
    private HashSet<String> permsList;
    private List<Menu> menuList;

    public HashSet<String> getPermsList() {
        return permsList;
    }

    public void setPermsList(HashSet<String> permsList) {
        this.permsList = permsList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}

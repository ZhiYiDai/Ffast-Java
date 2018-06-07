package cn.ffast.web.service.sys;

import cn.ffast.core.support.ICrudService;
import cn.ffast.web.entity.sys.Res;
import cn.ffast.web.entity.sys.RoleRes;
import cn.ffast.web.vo.RoleMenuPerms;
import cn.ffast.core.vo.ServiceResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 系统_角色资源服务类
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */
public interface IRoleResService extends ICrudService<RoleRes,Long> {
    /**
     * 根据角色ID获得菜单或权限列表
     * @param roleId
     * @return
     */
    List<Res> getRoleResList(Long roleId,Integer resType);

    /**
     * 根据角色ID获得菜单或权限列表
     * @param roleIds
     * @param resType
     * @return
     */
    List<Res> getRoleResList(String roleIds, Integer resType);
    /**
     * 根据角色ID获得菜单列表
     * @param roleId
     * @return
     */
     List<Res> getRoleMenuList(Long roleId);
    /**
     * 根据角色ID获得权限集合
     * @param roleId
     * @return
     */
     HashSet<String> getRolePermissionList(Long roleId);

    /**
     * 根据角色id获得权限集合和菜单列表
     * @param roleId
     * @param permissionList
     * @param menuList
     */
    RoleMenuPerms getRoleMenuAndPerms(String roleIds);

    /**
     * 保存角色资源
     * @param ids
     * @param roleId
     * @return
     */
    ServiceResult saveRes(String ids,Long roleId);

    /**
     * 获得角色资源列表
     * @param roleId
     * @return
     */
    List<Long> getRoleResIds(Long roleId);

    /**
     * 获得角色资源列表
     * @param roleId
     * @return
     */
    Set<String> getRoleResIdentitys(Long roleId);

}

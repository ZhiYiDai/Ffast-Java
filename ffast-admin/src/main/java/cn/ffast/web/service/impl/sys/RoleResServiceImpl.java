package cn.ffast.web.service.impl.sys;


import cn.ffast.core.support.CrudServiceImpl;

import cn.ffast.core.utils.CollectionUtils;
import cn.ffast.web.dao.sys.RoleResMapper;
import cn.ffast.web.entity.sys.Res;
import cn.ffast.web.entity.sys.RoleRes;
import cn.ffast.web.service.sys.IRoleResService;
import cn.ffast.core.utils.FStringUtil;
import cn.ffast.core.vo.Menu;
import cn.ffast.web.vo.RoleMenuPerms;
import cn.ffast.core.vo.ServiceResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 系统_角色资源服务实现类
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */
@Service
public class RoleResServiceImpl extends CrudServiceImpl<RoleResMapper, RoleRes, Long> implements IRoleResService {

    @Override
    public List<Res> getRoleResList(Long roleId, Integer resType) {
        return baseMapper.listByRoleId(roleId.toString(), resType);
    }

    @Override
    public List<Res> getRoleResList(String roleIds, Integer resType) {
        return baseMapper.listByRoleId(roleIds, resType);
    }

    @Override
    public List<Res> getRoleMenuList(Long roleId) {
        return getRoleResList(roleId, 1);
    }

    @Override
    public HashSet<String> getRolePermissionList(Long roleId) {
        HashSet<String> result = new HashSet<>();
        for (Res resources : getRoleResList(roleId, 2)) {
            result.add(resources.getIdentity());
        }
        return result;
    }


    @Cacheable(value = "sys", key = "'roleRes_menuPerms_'+#roleIds")
    @Override
    public RoleMenuPerms getRoleMenuAndPerms(String roleIds) {
        RoleMenuPerms result = new RoleMenuPerms();
        HashSet<String> permissionList = new HashSet<>();
        List<Menu> menuList = new ArrayList<>();
        for (Res resources : getRoleResList(roleIds, null)) {
            if (resources.getResType() != null) {
                if (resources.getResType().equals(1)) {
                    Menu menu = new Menu();
                    menu.setId(resources.getId());
                    menu.setIcon(resources.getIcon());
                    menu.setParentId(resources.getParentId());
                    menu.setName(resources.getName());
                    menu.setUrl(resources.getUrl());
                    menuList.add(menu);
                } else if (resources.getResType().equals(2)) {
                    permissionList.add(resources.getIdentity());
                }
            }
        }
        result.setMenuList(menuList);
        result.setPermsList(permissionList);
        return result;
    }

    @Override
    public ServiceResult saveRes(String ids, Long roleId) {
        ServiceResult result = new ServiceResult(false);
        String[] idArray = FStringUtil.split(ids, ",");
        List<RoleRes> roleResList = new ArrayList<>();
        Long creatorId = getLoginUserId();
        EntityWrapper ew = new EntityWrapper<RoleRes>();
        ew.eq("role_id", roleId);
        delete(ew);

        for (int i = 0; i < idArray.length; i++) {
            RoleRes roleRes = new RoleRes();
            roleRes.setResId(new Long(idArray[i]));
            roleRes.setRoleId(roleId);
            roleRes.setCreatorId(creatorId);
            roleResList.add(roleRes);
        }
        // 批量插入
        if (insertBatch(roleResList)) {
            result.setSuccess(true);
        }
        clearCache();
        return result;
    }

    @Cacheable(value = "sys", key = "'roleRes_resIds_'+#roleId")
    @Override
    public List<Long> getRoleResIds(Long roleId) {
        RoleRes m = new RoleRes();
        m.setRoleId(roleId);
        EntityWrapper ew = new EntityWrapper<RoleRes>(m);
        ew.setSqlSelect("res_id");
        List<RoleRes> roleResList = selectList(ew);
        List<Long> resIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roleResList)) {
            for (RoleRes roleRes : roleResList) {
                resIds.add(roleRes.getResId());
            }
        }
        return resIds;
    }

    @Cacheable(value = "sys", key = "'roleRes_resIdentitys_'+#roleId")
    @Override
    public Set<String> getRoleResIdentitys(Long roleId) {
        List<Res> roleResList = baseMapper.listByRoleId(roleId.toString(), new Integer(2));
        Set<String> identitys = new HashSet<>();
        if (CollectionUtils.isNotEmpty(roleResList)) {
            for (Res res : roleResList) {
                identitys.add(res.getIdentity());
            }
        }
        return identitys;
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        redisCacheUtils.clear("sys::roleRes_*");
    }

    @Override
    protected ServiceResult createAfter(RoleRes m) {
        clearCache();
        return null;
    }

    @Override
    protected ServiceResult deleteAfter(String ids) {
        clearCache();
        return null;
    }

    @Override
    protected ServiceResult updateAfter(RoleRes m, RoleRes oldM) {
        clearCache();
        return null;
    }
}

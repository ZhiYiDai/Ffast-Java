package cn.ffast.web.service.impl.sys;


import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.web.dao.sys.RoleMapper;
import cn.ffast.web.dao.sys.UserRoleMapper;
import cn.ffast.web.entity.sys.Role;
import cn.ffast.web.entity.sys.UserRole;
import cn.ffast.web.service.sys.IRoleService;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import cn.ffast.web.service.sys.IUserRoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 系统_角色服务实现类
 * @copyright:
 * @createTime: 2017-09-12 17:18:46
 * @author: dzy
 * @version: 1.0
 */
@Service
public class RoleServiceImpl extends CrudServiceImpl<RoleMapper, Role, Long> implements IRoleService {

    @Resource
    UserRoleMapper userRoleMapper;


    @Override
    protected ServiceRowsResult listBefore(Role m, EntityWrapper<Role> ew) {
        if (m.getName() != null) {
            ew.like("name", m.getName());
            m.setName(null);
        }
        if (m.getDescription() != null) {
            ew.like("description", m.getDescription());
            m.setDescription(null);
        }
        if (m.getStatus() != null) {
            ew.eq("status", m.getStatus());
            m.setStatus(null);
        }
        return null;
    }

    @Override
    protected ServiceResult createBefore(Role m) {
        ServiceResult result = new ServiceResult();
        EntityWrapper ew = new EntityWrapper<Role>();
        ew.eq("name", m.getName());
        if (selectCount(ew) > 0) {
            result.setMessage("该角色已存在");
            return result;
        } else {
            return null;
        }
    }

    @Override
    protected ServiceResult deleteBefore(String ids, EntityWrapper<Role> ew) {
        EntityWrapper selectEw = new EntityWrapper<Role>();
        selectEw.in("id", ids);
        List<Role> roles = baseMapper.selectList(selectEw);
        for (Role role : roles) {
            if (new Integer(1).equals(role.getIsSys())) {
                return new ServiceResult(false).setMessage("无法删除系统角色:" + role.getName());
            }
        }
        return null;
    }

    @Override
    protected ServiceResult deleteAfter(String ids) {
        // 删除账号角色
        EntityWrapper deleteEw = new EntityWrapper<UserRole>();
        deleteEw.in("role_id", ids);
        userRoleMapper.delete(deleteEw);
        return null;
    }


    @Override
    protected ServiceResult updateBefore(Role m, Role oldM) {
        if (Integer.valueOf(1).equals(oldM.getIsSys())) {
            return new ServiceResult(false).setMessage("不能修改系统角色");
        }
        return null;
    }
}

package cn.ffast.web.service.impl.sys;

import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.web.dao.sys.ResMapper;
import cn.ffast.web.dao.sys.RoleResMapper;
import cn.ffast.web.entity.sys.Res;
import cn.ffast.web.entity.sys.RoleRes;
import cn.ffast.web.service.sys.IResService;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 系统_资源服务实现类
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */
@Service
public class ResServiceImpl extends CrudServiceImpl<ResMapper, Res, Long> implements IResService {
    private static Logger logger = LoggerFactory.getLogger(ResServiceImpl.class);

    @Resource
    RoleResMapper roleResMapper;



    private Res makeRes(String identity, String name, String append, String parentName, Long parentId, String parentUrl) {
        Res res = new Res();
        res.setIdentity(identity + ":" + append);
        res.setStatus(1);
        res.setResType(2);
        res.setName(parentName + ":" + name);
        res.setUrl("/api" + parentUrl + "/" + append);
        res.setParentId(parentId);
        res.setCreatorId(getLoginUserId());
        return res;
    }


    @Override
    public boolean addBaseCrud(Res m) {
        if (m.getId() == null) {
            return false;
        }
        List<Res> resList = new ArrayList<>();
        //显示权限
        resList.add(makeRes(m.getIdentity(), "显示", "list", m.getName(), m.getId(), m.getUrl()));
        //添加权限
        resList.add(makeRes(m.getIdentity(), "添加", "create", m.getName(), m.getId(), m.getUrl()));
        //更新权限
        resList.add(makeRes(m.getIdentity(), "修改", "update", m.getName(), m.getId(), m.getUrl()));
        //删除权限
        resList.add(makeRes(m.getIdentity(), "删除", "delete", m.getName(), m.getId(), m.getUrl()));
        insertBatch(resList);
        return true;
    }

    @Override
    protected ServiceResult deleteBefore(String ids, EntityWrapper<Res> ew) {
        EntityWrapper selectEw = new EntityWrapper<RoleRes>();
        selectEw.in("res_id", ids);
        roleResMapper.delete(selectEw);
        return null;
    }

    @Override
    protected ServiceRowsResult listBefore(Res m, EntityWrapper<Res> ew) {
        if (m.getName() != null) {
            ew.like("name", m.getName());
        }
        if (m.getIdentity() != null) {
            ew.like("identity", m.getIdentity());
        }
        if (m.getUrl() != null) {
            ew.like("url", m.getName());
        }
        if (m.getStatus() != null) {
            ew.eq("status", m.getStatus());
        }
        if (m.getParentId() != null) {
            ew.eq("parent_id", m.getParentId().toString()).or("id=" + m.getParentId().toString());
        }

        m.setParentId(null);
        m.setIdentity(null);
        m.setUrl(null);
        m.setName(null);
        m.setStatus(null);
        return null;
    }

    @Override
    protected ServiceResult deleteAfter(String ids) {
        clearCache();
        return null;
    }


    @Override
    protected ServiceResult createAfter(Res m) {
        clearCache();
        return null;
    }

    @Override
    protected ServiceResult updateAfter(Res m, Res oldM) {
        clearCache();
        return null;
    }


    @Override
    protected ServiceResult updateBefore(Res m, Res oldM) {
        try {
            BeanUtils.copyProperties(m, oldM);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        redisCacheUtils.clear("sys::roleRes_*");
    }

}

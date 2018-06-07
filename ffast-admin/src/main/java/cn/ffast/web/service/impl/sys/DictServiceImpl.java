package cn.ffast.web.service.impl.sys;

import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.web.dao.sys.DictMapper;
import cn.ffast.web.entity.sys.Dict;
import cn.ffast.web.service.sys.IDictService;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 字典服务实现类
 * @copyright:
 * @createTime: 2017-09-12 17:18:46
 * @author: dzy
 * @version: 1.0
 */
@Service
public class DictServiceImpl extends CrudServiceImpl<DictMapper, Dict, Long> implements IDictService {


    @Override
    protected ServiceRowsResult listBefore(Dict m, EntityWrapper<Dict> ew) {
        ew.like("name", m.getName());
        ew.like("code", m.getCode());
        m.setName(null);
        m.setCode(null);
        return null;
    }


    @Cacheable(value = "sys", key = "'dict_type_'+(#isName!=null && #isName?'name_':'')+#type")
    @Override
    public ServiceResult getDict(String type, Boolean isName) {
        ServiceRowsResult result = new ServiceRowsResult(false);
        List<Dict> list = null;
        if (isName != null && isName) {
            list = baseMapper.listDict(null, new Long(type));
        } else {
            list = baseMapper.listDict(type, null);
        }
        result.setPage(list);
        result.setSuccess(true);
        clearCache();
        return result;
    }

    /**
     * 清除缓存
     */
    private void clearCache(){
        redisCacheUtils.clear("sys::dict_*");
    }

    @Override
    protected ServiceResult createAfter(Dict m) {
        clearCache();
        return null;
    }

    @Override
    protected ServiceResult deleteAfter(String ids) {
        clearCache();
        return null;
    }

    @Override
    protected ServiceResult updateAfter(Dict m, Dict oldM) {
        clearCache();
        return null;
    }
}

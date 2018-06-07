package cn.ffast.web.service.impl.sys;

import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.web.dao.sys.DictTypeMapper;
import cn.ffast.web.entity.sys.DictType;
import cn.ffast.web.service.sys.IDictTypeService;
import cn.ffast.core.vo.ServiceResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

/**
 * @description: 基础字典类型服务实现类
 * @copyright:
 * @createTime: 2017-09-12 17:18:46
 * @author: dzy
 * @version: 1.0
 */
@Service
public class DictTypeServiceImpl extends CrudServiceImpl<DictTypeMapper, DictType, Long> implements IDictTypeService {

    @Override
    protected ServiceResult createBefore(DictType m) {
        ServiceResult result = new ServiceResult();
        EntityWrapper ew = new EntityWrapper<DictType>();
        ew.eq("identity", m.getIdentity());
        if (selectCount(ew) > 0) {
            result.setMessage("标识重复，请重新输入");
            return result;
        }
        EntityWrapper ew1 = new EntityWrapper<DictType>();
        ew1.eq("name", m.getName());
        if (selectCount(ew1) > 0) {
            result.setMessage("分类名重复，请重新输入");
            return result;
        }
        return null;
    }
}

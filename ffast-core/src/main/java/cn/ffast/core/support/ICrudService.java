package cn.ffast.core.support;

import cn.ffast.core.vo.ServiceRowsResult;
import cn.ffast.core.vo.ServiceResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @description: 基础增删改查
 * @copyright:
 * @createTime: 2017/9/5 9:39
 * @author：dzy
 * @version：1.0
 */
public interface ICrudService<T extends BaseEntity,ID> extends IService<T> {
    /**
     * @description: 插入对象
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param m 实体类
     * @return
     */
    ServiceResult create(T m);
    /**
     * @description: 更新对象
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param m 实体类
     * @param updateAllColumn 是否更新所有字段
     * @param ignoreProperties 更新排除字段
     * @return
     */
    ServiceResult update(T m, boolean updateAllColumn,String[] ignoreProperties);
    /**
     * @description: 批量删除
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param ids
     * @return
     */
    ServiceResult mulDelete(String ids);
    /**
     * @description: 根据Id删除对象
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param id
     * @return
     */
    ServiceResult delById(ID id);
    /**
     * @description: 删除支持批量
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param ids
     * @return
     */
    ServiceResult delete(String ids);
    /**
     * @description: 根据Id发现对象
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param id
     * @return
     */
    T findById(ID id);
    /**
     * @description: 分页查询
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param m
     * @param page
     */
    ServiceRowsResult findListByPage(T m, Page<T> page);
    /**
     * @description: 分页查询
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param m
     * @param page
     */
    ServiceRowsResult findListByPage(T m, Page<T> page,String[] properties);
    /**
     * @description: 查询
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param m
     * @param properties 查询字段
     * @return
     */
    ServiceRowsResult list(T m,String[] properties);
    /**
     * @description: 查询
     * @createTime: 2017-9-5 10:00
     * @author: dzy
     * @param m
     * @param properties 查询字段
     * @return
     */
    List<T> selectList(T m,String[] properties);

}

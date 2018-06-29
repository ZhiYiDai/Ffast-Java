package cn.ffast.core.support;


import cn.ffast.core.annotations.Log;
import cn.ffast.core.redis.RedisCacheUtils;
import cn.ffast.core.utils.FStringUtil;
import cn.ffast.core.utils.ReflectionUtils;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 基础增删改查S
 * @copyright:
 * @createTime: 2017/9/5 9:39
 * @author：dzy
 * @version：1.0
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CrudServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, ID extends Serializable> extends BaseService<M, T> implements ICrudService<T, ID> {
    private static Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);

    @Resource
    protected RedisCacheUtils redisCacheUtils;


    @Log("插入记录")
    @Override
    public ServiceResult create(T m) {
        ServiceResult result = new ServiceResult(false);

        ServiceResult beforeResult = createBefore(m);
        if (beforeResult != null) {
            return beforeResult;
        }

        try {
            if (insert(m)) {
                ServiceResult afterResult = createAfter(m);
                if (afterResult != null) {
                    return afterResult;
                }
                result.addData("id", m.getId());
                result.setSuccess(true);
            } else {
                result.setMessage("添加失败!");
                return result;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            result.setMessage("添加数据出现异常!");
            return result;
        }
        return result;
    }


    @Override
    @Log("更新记录")
    public ServiceResult update(T m, boolean updateAllColumn, String[] ignoreProperties) {
        ServiceResult result = new ServiceResult(false);
        if (m == null || m.getId() == null) {
            result.setMessage("请指定要修改记录");
            return result;
        }
        T oldM = selectById(m.getId());
        ServiceResult beforeResult = updateBefore(m, oldM);
        if (beforeResult != null) {
            return beforeResult;
        }
        boolean status = false;
        if (m != null && oldM != null) {
            try {
                // 是否更新所有字段
                if (updateAllColumn) {
                    // 将旧记录和修改的记录合并
                    BeanUtils.copyProperties(m, oldM, ignoreProperties);
                    status = updateAllColumnById(oldM);
                } else {
                    ReflectionUtils.reflectClassValueToNull(m, ignoreProperties);
                    status = updateById(m);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                result.setMessage("更新异常！");
                return result;
            }
        }
        if (status) {
            ServiceResult afterResult = updateAfter(m, oldM);
            if (afterResult != null) {
                return afterResult;
            }
            result.setSuccess(true);
            return result;
        } else {
            result.setMessage("更新失败！");
            return result;
        }
    }

    @Log("删除记录")
    @Override
    public ServiceResult mulDelete(String ids) {
        ServiceResult result = new ServiceResult(false);
        String[] idArray = FStringUtil.split(ids);
        if (ArrayUtils.isEmpty(idArray)) {
            result.setMessage("请选择要删除的数据行");
        } else {
            EntityWrapper ew = new EntityWrapper<T>();
            ServiceResult beforeResult = deleteBefore(ids, ew);
            if (beforeResult != null) {
                return beforeResult;
            }

            try {
                ew.in("id", ids);
                if (delete(ew)) {
                    ServiceResult afterResult = deleteAfter(ids);
                    if (afterResult != null) {
                        return afterResult;
                    }
                    result.setSuccess(true);
                } else {
                    result.setMessage("删除失败");
                }
            } catch (Exception e) {
                result.setMessage("删除数据异常！");
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    @Log("根据id删除记录")
    @Override
    public ServiceResult delById(ID id) {
        ServiceResult result = new ServiceResult(false);
        if (id == null) {
            result.setMessage("请选择要删除的数据行");
            return result;
        }
        try {
            if (deleteById(id)) {
                result.setSuccess(true);
            } else {
                result.setMessage("删除失败");
            }
        } catch (Exception e) {
            result.setMessage("删除数据异常！");
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    @Log("删除记录")
    @Override
    public ServiceResult delete(String ids) {
        return mulDelete(ids);
    }

    @Override
    public T findById(ID id) {
        return selectById((Serializable) id);
    }


    @Override
    public ServiceRowsResult findListByPage(T m, Page<T> page) {
        return findListByPage(m, page, null);
    }


    @Override
    public ServiceRowsResult findListByPage(T m, Page<T> page, String[] properties) {
        ServiceRowsResult result = new ServiceRowsResult(false);
        EntityWrapper ew = new EntityWrapper<T>(m);
        if (properties != null) {
            ew.setSqlSelect(properties);
        }
        ServiceRowsResult beforeResult = listBefore(m, ew);
        if (beforeResult != null) {
            return beforeResult;
        }

        page = selectPage(page, ew);
        ServiceRowsResult afterResult = listAfter(m, page.getRecords());
        if (afterResult != null) {
            return afterResult;
        }
        result.setPage(page.getRecords(), page.getTotal());
        result.setSuccess(true);
        return result;
    }


    @Override
    public ServiceRowsResult list(T m, String[] properties) {
        ServiceRowsResult result = new ServiceRowsResult(false);
        result.setPage(selectList(m, properties));
        result.setSuccess(true);
        return result;
    }

    @Override
    public List<T> selectList(T m, String[] properties) {
        EntityWrapper ew = new EntityWrapper<T>(m);
        if (properties != null) {
            ew.setSqlSelect(properties);
        }
        return selectList(ew);
    }


    /**
     * 以下方法和在子类覆盖进行特殊操作
     * 如果返回ServiceResult不为NULL就使用该返回结果
     */

    /**
     * 数据插入前
     *
     * @param m
     * @return
     */
    protected ServiceResult createBefore(T m) {
        return null;
    }

    /**
     * 数据删除前
     *
     * @param ids
     * @return
     */
    protected ServiceResult deleteBefore(String ids, EntityWrapper<T> ew) {
        return null;
    }

    /**
     * 数据更新前
     *
     * @param m
     * @param oldM
     * @return
     */
    protected ServiceResult updateBefore(T m, T oldM) {
        return null;
    }

    /**
     * 数据查询前
     *
     * @param m
     * @param ew
     * @return
     */
    protected ServiceRowsResult listBefore(T m, EntityWrapper<T> ew) {
        return null;
    }

    /**
     * 数据插入后
     *
     * @param m
     * @return
     */
    protected ServiceResult createAfter(T m) {
        return null;
    }

    /**
     * 数据删除后
     *
     * @param ids
     * @return
     */
    protected ServiceResult deleteAfter(String ids) {
        return null;
    }

    /**
     * 数据更新后
     *
     * @param m
     * @param oldM
     * @return
     */
    protected ServiceResult updateAfter(T m, T oldM) {
        return null;
    }

    /**
     * 数据查询后
     *
     * @param m
     * @param resultList 查询结果集
     * @return
     */
    protected ServiceRowsResult listAfter(T m, List<T> resultList) {
        return null;
    }


}

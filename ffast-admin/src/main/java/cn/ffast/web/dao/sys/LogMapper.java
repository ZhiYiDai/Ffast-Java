package cn.ffast.web.dao.sys;

import cn.ffast.web.entity.sys.Log;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

/**
 * @description: 操作日志表Mapper接口
 * @copyright:
 * @createTime: 2017-11-14 14:48:11
 * @author: dzy
 * @version: 1.0
 */
public interface LogMapper extends BaseMapper<Log> {
    /**
     * 分页查询
     * @param page
     * @param log
     * @return
     */
    List<Log> listByPage(Pagination page, Log log);
}
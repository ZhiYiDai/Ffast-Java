package cn.ffast.web.dao.sys;

import cn.ffast.web.entity.sys.Attach;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 系统_附件Mapper接口
 * @copyright:
 * @createTime: 2017-09-27 15:47:59
 * @author: dzy
 * @version: 1.0
 */
public interface AttachMapper extends BaseMapper<Attach> {
    /**
     * 分页查询
     * @param page
     * @param attach
     * @return
     */
    List<Attach> listByPage(Pagination page, Attach attach);


    /**
     * 更新引用计数
     * @param ids
     * @param addValue
     */
    void updateCount(@Param("ids") String ids, @Param("addValue") Integer addValue);
}
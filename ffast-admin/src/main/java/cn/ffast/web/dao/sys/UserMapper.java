package cn.ffast.web.dao.sys;

import cn.ffast.web.entity.sys.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 系统_用户Mapper接口
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */

public interface UserMapper extends BaseMapper<User> {
    /**
     * 分页查询
     * @param page
     * @param user
     * @return
     */
    List<User> listByPage(Pagination page, User user);

    /**
     * 根据登录结果更新登录次数时间信息
     * ct
     */
    void updateLoginResult(@Param("username") String username, @Param("bool") Boolean bool);
}
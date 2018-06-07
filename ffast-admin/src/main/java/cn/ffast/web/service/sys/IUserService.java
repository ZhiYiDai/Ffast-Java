package cn.ffast.web.service.sys;

import cn.ffast.core.support.ICrudService;
import cn.ffast.web.entity.sys.User;
import cn.ffast.core.vo.ServiceResult;


/**
 * @description: 系统_用户服务类
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */
public interface IUserService extends ICrudService<User,Long> {
    /**
     * 根据username取得用户对象
     */
    User getUserByUserName(String username) ;
    /**
     * 验证用户
     */
    User verifyUser(String username,String password) ;
    /**
     * 重置密码
     */
    ServiceResult reseting(Long ids);

    /**
     * 修改密码
     */
    ServiceResult respwd(Long userId, String pwd, String newpwd, String newpwd2);

    /**
     * 根据登录结果更新登录次数时间信息
     */
    void updateLoginResult(String username, Boolean bool);

}

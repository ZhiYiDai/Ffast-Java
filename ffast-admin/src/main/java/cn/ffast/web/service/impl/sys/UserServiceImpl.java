package cn.ffast.web.service.impl.sys;

import cn.ffast.core.annotations.Log;
import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.core.utils.Md5Utils;
import cn.ffast.web.dao.sys.UserMapper;
import cn.ffast.web.entity.sys.Role;
import cn.ffast.web.entity.sys.User;
import cn.ffast.web.service.sys.IUserService;
import cn.ffast.core.utils.PasswordUtil;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 系统_用户服务实现类
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<UserMapper, User, Long> implements IUserService {

    @Value("${auth.pwdDefault}")
    private String PWD_DEFAULT;


    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    UserMapper userMapper;

    @Override
    public User getUserByUserName(String username) {
        User user = new User();
        user.setUsername(username);
        return userMapper.selectOne(user);
    }

    @Log("登录")
    @Override
    public User verifyUser(String username, String password) {
        User user = getUserByUserName(username);
        if (user != null) {
            if (PasswordUtil.getPwd(user.getSalt(), password).equals(user.getPwd())) {
                logger.debug(username + " 账户密码验证通过");
                return user;
            }
        }
        return null;
    }

    @Log("重置密码")
    @Override
    public ServiceResult reseting(Long id) {
        ServiceResult result = new ServiceResult(false);
        if (id == null) {
            result.setMessage("请选择要重置密码的用户");
        } else {
            User user = new User();
            user.setId(id);
            user.setPwd(PasswordUtil.getPwd(user.getSalt(), PWD_DEFAULT));
            try {
                int res = userMapper.updateById(user);
                if (res > 0) {
                    result.setSuccess(true);
                    result.setMessage("重置成功，密码重置为：" + PWD_DEFAULT);
                } else {
                    result.setMessage("修改失败");
                }
            } catch (Exception e) {
                result.setMessage("密码修改异常！");
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected ServiceRowsResult listBefore(User m, EntityWrapper<User> ew) {
        ew.like("username", m.getUsername());
        ew.like("email", m.getEmail());
        ew.like("tel", m.getTel());
        ew.like("login_ip", m.getLoginIp());
        m.setEmail(null);
        m.setTel(null);
        m.setUsername(null);
        m.setLoginIp(null);
        return null;
    }


    @Override
    protected ServiceResult createBefore(User m) {
        ServiceResult result = new ServiceResult();
        EntityWrapper ew = new EntityWrapper<User>();
        ew.eq("username", m.getUsername());
        if (selectCount(ew) > 0) {
            result.setMessage("该用户名已存在");
            return result;
        }
        m.setSalt(Md5Utils.hash(Md5Utils.getUUID()));
        //设置初始密码
        m.setPwd(PasswordUtil.getPwd(m.getSalt(), PWD_DEFAULT));
        return null;
    }

    @Override
    public ServiceRowsResult findListByPage(User m, Page<User> page, String[] properties) {
        ServiceRowsResult result = new ServiceRowsResult(false);
        page.setRecords(baseMapper.listByPage(page, m));
        result.setPage(page.getRecords(), page.getTotal());
        result.setSuccess(true);
        redisCacheUtils.setCacheObject("user", result);

        return result;
    }

    @Log("修改密码")
    @Override
    public ServiceResult respwd(Long userId, String pwd, String newpwd, String newpwd2) {
        logger.debug(" 用户ID:" + userId + "  原密码" + pwd + "  新密码:" + newpwd);
        ServiceRowsResult result = new ServiceRowsResult(false);
        User user = userMapper.selectById(userId);
        //判断密码是否相同
        if (!newpwd.equals(newpwd2)) {
            result.setMessage("输入的两次密码不相同！");
        } else if (user == null) {
            result.setMessage("不存在的用户！");
        } else if (!user.getPwd().equals(PasswordUtil.getPwd(user.getSalt(), pwd))) {
            logger.error(userId + "原密码错误");
            result.setMessage("原密码错误！");
        } else {
            //修改密码
            User u = new User();
            u.setId(userId);
            u.setPwd(PasswordUtil.getPwd(user.getSalt(), newpwd));
            Integer reg = userMapper.updateById(u);
            if (reg.equals(new Integer(1))) {
                result.setSuccess(true);
                result.setMessage("修改成功！");
            } else {
                result.setMessage("系统繁忙,修改失败！");
            }
        }
        return result;
    }

    @Override
    public void updateLoginResult(String username, Boolean bool) {
        userMapper.updateLoginResult(username, bool);
    }


    @Override
    protected ServiceResult deleteBefore(String ids, EntityWrapper<User> ew) {
//        EntityWrapper selectEw = new EntityWrapper<User>();
//        selectEw.in("id", ids);
//        List<User> users = baseMapper.selectList(selectEw);
//        for (User user : users) {
//            if (Integer.valueOf(1).equals(user.getIsSys())) {
//                return new ServiceResult(false).setMessage("不能修改系统账号");
//            }
//        }
        return null;


    }
}

package cn.ffast.web.service.sys;


import cn.ffast.core.support.ICrudService;
import cn.ffast.web.entity.sys.Res;

import java.util.HashMap;


/**
 * @description: 系统_资源服务类
 * @copyright:
 * @createTime: 2017年08月31日 09:49:42
 * @author: dzy
 * @version: 1.0
 */
public interface IResService extends ICrudService<Res, Long> {

    /**
     * 添加增删改查权限
     *
     * @param m
     * @return
     */
    boolean addBaseCrud(Res m);



}

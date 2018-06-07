package cn.ffast.web.service.sys;

import cn.ffast.core.support.ICrudService;
import cn.ffast.web.entity.sys.Dict;
import cn.ffast.core.vo.ServiceResult;

/**
 * @description: 字典服务类
 * @copyright:
 * @createTime: 2017-09-12 17:18:46
 * @author: dzy
 * @version: 1.0
 */
public interface IDictService extends ICrudService<Dict,Long> {
	/**
	 * 根据字典分类获得字典
	 * @param type 分类
	 * @param isName 是否为字典分类名称
	 * @return
	 */
	ServiceResult getDict(String type,Boolean isName);
}

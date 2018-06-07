package cn.ffast.web.dao.sys;

import cn.ffast.web.entity.sys.Dict;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 字典Mapper接口
 * @copyright:
 * @createTime: 2017-09-12 17:18:46
 * @author: dzy
 * @version: 1.0
 */
public interface DictMapper extends BaseMapper<Dict> {
    /**
     * 分页查询
     * @param identity
     * @param name
     * @return
     */
    List<Dict> listDict(@Param("identity") String identity, @Param("name") Long name);
}
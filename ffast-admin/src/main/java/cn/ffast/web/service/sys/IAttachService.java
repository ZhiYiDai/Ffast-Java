package cn.ffast.web.service.sys;

import cn.ffast.core.support.ICrudService;
import cn.ffast.web.entity.sys.Attach;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @description: 系统_附件服务类
 * @copyright:
 * @createTime: 2017-09-27 15:47:59
 * @author: lxb
 * @version: 1.0
 */
public interface IAttachService extends ICrudService<Attach,Long> {
    /**
     * 获得附件文件对象
     * @param attachId
     * @return
     */
    File getAttach(Long attachId);

    /**
     * 上传图片
     * @param resource
     * @param file
     * @return
     */
    ServiceResult uploadImg(String resource,MultipartFile file);

    /**
     * 上传文件
     * @param resource
     * @param file
     * @return
     */
    ServiceResult upload(String resource, MultipartFile file);

    /**
     * 删除垃圾文件
     */
    ServiceRowsResult deleteRubbish();

    /**
     * 增加引用计数
     * @param ids
     */
    void addCount(String ids);


    /**
     * 减去引用计数
     * @param ids
     */
    void subtractCount(String ids);

}

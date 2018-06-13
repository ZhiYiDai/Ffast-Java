package cn.ffast.web.service.impl.sys;

import cn.ffast.core.annotations.Log;
import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.web.dao.sys.AttachMapper;
import cn.ffast.web.entity.sys.Attach;
import cn.ffast.web.service.sys.IAttachService;
import cn.ffast.core.utils.FileUtil;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.core.vo.ServiceRowsResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 系统_附件服务实现类
 * @copyright:
 * @createTime: 2017-09-27 15:47:59
 * @author: dzy
 * @version: 1.0
 */
@Service
public class AttachServiceImpl extends CrudServiceImpl<AttachMapper, Attach, Long> implements IAttachService {
    private static Logger logger = LoggerFactory.getLogger(AttachServiceImpl.class);
    @Resource
    private AttachMapper attachMapper;

    @Value("${upload.filesBasePath}")
    private String BASE_PATH = "";
    @Value("${upload.uploadUrl}")
    private String UPLOAD_URL = "";




    @Override
    public File getAttach(Long attachId) {
        // 文件保存目录路径

        String path = null;
        if (attachId != null) {
            Attach attach = baseMapper.selectById(attachId);
            if (attach != null) {
                path = attach.getPath();
            }
        } else {
            //throw new RuntimeException("没有找到相应的图片");
            return null;
        }
        if (StringUtils.isNotEmpty(path)) {
            String filePath = BASE_PATH + path;
            logger.debug(filePath);
            File file = new File(filePath);

            if (file.exists()) {
                return file;
            }

        }
        return null;
    }

    @Override
    @Log("上传图片")
    public ServiceResult uploadImg(String resource, MultipartFile file) {
        ServiceResult result = new ServiceResult(false);
        String fileName = file.getOriginalFilename();
        if (file == null) {
            result.setMessage("请选择上传的图片！");
            return result;
        }
        if (StringUtils.isEmpty(fileName)) {
            result.setMessage("请指定要上传的图片名！");
            return result;
        }
        String extention = FilenameUtils.getExtension(fileName).toLowerCase();
        if (!"jpg".equals(extention) && !"png".equals(extention) && !"jpeg".equals(extention) && !"gif".equals(extention)) {
            result.setMessage("图片格式不正确！");
            return result;
        }
        return upload("images/" + resource, file);
    }

    @Override
    public ServiceResult upload(String resource, MultipartFile file) {
        ServiceResult result = new ServiceResult(false);
        String resourcePath = resource + "/";
        String extention = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
        String fileName = System.currentTimeMillis() + "." + extention;
        Long creatorId = getLoginUserId();

        File courseFileDir = new File(BASE_PATH + resourcePath);
        File courseFile = new File(BASE_PATH + resourcePath + fileName);

        //创建文件
        if (!courseFileDir.exists()) {
            courseFileDir.mkdirs();
        }
        try {
            FileUtil.saveFileNew(file.getInputStream(), courseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 附件路径
        Attach attach = new Attach();
        attach.setPath(BASE_PATH + resourcePath + fileName);
        attach.setExtention(extention);
        attach.setSize(file.getSize());
        attach.setName(file.getOriginalFilename());
        attach.setCreatorId(creatorId);
        attach.setLastModifierId(creatorId);
        insert(attach);
        result.addData("path", UPLOAD_URL  + resourcePath + fileName);
        result.addData("id", attach.getId());
        result.setSuccess(true);
        return result;
    }

    @Override
    protected ServiceResult deleteAfter(String ids) {
        EntityWrapper<Attach> ew = new EntityWrapper<>();
        ew.in("id", ids);
        List<Attach> attachList = selectList(ew);
        for (Attach attach : attachList) {
            FileUtil.deleteFile(BASE_PATH + attach.getPath());
        }
        return null;
    }

    @Override
    protected ServiceRowsResult listAfter(Attach m, List<Attach> list) {
        for (int i = 0; i < list.size(); i++) {
            String path = list.get(i).getPath();
            String filePath = BASE_PATH + path;
            File file = new File(filePath);
            if (file.exists()) {
                list.get(i).setExist("是");
            } else {
                list.get(i).setExist("否");
            }
        }
        return null;
    }

    /**
     * 删除垃圾文件
     */
    @Override
    public ServiceRowsResult deleteRubbish() {
        EntityWrapper ew = new EntityWrapper();
        ew.where("count={0}", 0);
        List<Attach> list = baseMapper.selectList(ew);
        List idList = new ArrayList();
        //删除记录
        for (int i = 0; i < list.size(); i++) {
            idList.add(list.get(i).getId());
            attachMapper.deleteById(list.get(i).getId());
        }
        //删除文件
        deleteAfter(idList.toString().replaceAll("[\\[\\]]", ""));
        ServiceRowsResult result = new ServiceRowsResult(true);
        result.setMessage("删除成功");
        return result;
    }

    @Override
    public void addCount(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            baseMapper.updateCount(ids, 1);
        }
    }

    @Override
    public void subtractCount(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            baseMapper.updateCount(ids, -1);
        }
    }


}

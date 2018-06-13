package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.vo.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.ffast.web.entity.sys.Attach;
import cn.ffast.web.service.sys.IAttachService;
import cn.ffast.core.support.BaseCrudController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;

/**
 * @description: 系统_附件数据接口
 * @copyright:
 * @createTime: 2017-09-27 15:47:59
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/attach")
@Logined
@Permission(value = "auth")
@Log("附件")
public class AttachController extends BaseCrudController<Attach, IAttachService, Long> {

    private static Logger logger = LoggerFactory.getLogger(AttachController.class);

    @Resource
    private IAttachService service;

    @Override
    protected IAttachService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


    /**
     * 获得图片
     * @param attachId
     * @param response
     */
    @RequestMapping(value = "/getImg", method = RequestMethod.GET )
    public void getImg(Long id, HttpServletResponse response) {
        File file = service.getAttach(id);
        if (file != null) {
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                InputStream in = new FileInputStream(file.getPath());
                BufferedOutputStream bout = new BufferedOutputStream(outputStream);
                byte[] b = new byte[(int)file.length()];
                int len = in.read(b);
                while (len > 0) {
                    bout.write(b, 0, len);
                    len = in.read(b);
                }
                bout.close();
                in.close();
                outputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImg(MultipartFile file, HttpServletRequest request) {
        return service.uploadImg("ffast", file);
    }

    /**
     * 删除垃圾文件
     * @return
     */
    @RequestMapping(value = "/deleteRubbish", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteRubish(){
        ServiceResult result = new ServiceResult(false);
        result = service.deleteRubbish();
        return result;
    }
}

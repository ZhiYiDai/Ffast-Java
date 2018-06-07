package cn.ffast.web.controller.sys;


import cn.ffast.core.annotations.Logined;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import javax.annotation.Resource;
import java.util.*;

import static java.awt.SystemColor.info;

/**
 * @description: 接口列表
 * @copyright:
 * @createTime: 2017/11/10 10:34
 * @author：dzy
 * @version：1.0
 */
@RestController
@RequestMapping("/api/restful")
@Logined
public class RestfulController {
    @Resource
    private RequestMappingHandlerMapping handlerMapping;

    /**
     * 获得所有api接口
     * @return
     */
    @RequestMapping("/list")
    public Object list() {
        Map<RequestMappingInfo, HandlerMethod> map = this.handlerMapping.getHandlerMethods();
        List<Object> result = new ArrayList<>();
        Iterator<?> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            RequestMappingInfo requestMappingInfo = ((RequestMappingInfo) entry.getKey());
            Map<String, Object> obj = new HashMap();
            for (String url : requestMappingInfo.getPatternsCondition().getPatterns()) {
                obj.put("url", url);
            }
            HandlerMethod method = map.get(requestMappingInfo);
            // RequestMapping requestMapping = method.getMethodAnnotation(RequestMapping.class);
            result.add(obj);
        }
        return result;
    }
}

# Ffast-Java
Ffast 基于Srping boot + Mybatis Plus后台管理系统前后分离快速开发解决方案

## 简 介
``` bash
    Ffast 基于Srping boot + Mybatis Plus后台管理系统前后分离快速开发解决方案，并具备以下特点
    1.在Mybatis Plus基础上封装了基础增删改Service Controller
    2.增删改查CrudController CrudService 支持提供灵活的 删除前,删除后,查询前,查询后,更新前,更新后,增加前,增加后方法提供给子类扩展。
    3.可以在application.yml进行项目参数配置
    4.redis序列化方式可以配置化，支持msgpack  jackson fastjson ，并可以使用RedisUtils工具类进行不同的序列方式缓存
    5.Session 存放方式可配置化，支持Redis Jwt 两种方式
    6.封装了权限验证注解，登录拦截注解、日志注解、增删改查配置注解、简单方便。
```  
## 前端解决方案Ffast-FE 
``` bash
    Ffast-FE 是一套基于vue iview后台管理系统前端快速开发解决方案
    详细地址 https://github.com/ZhiYiDai/Ffast-FE 
```  
## 项目结构
``` bash
    Ffast
    ├── ffast-admin -- 后台管理系统
    ├── ffast-core -- 项目核心代码与工具类
    ├── ffast-generator -- 代码生成器
    ├── ffast-parent -- parent
```  
### 已实现的模块
``` bash
    1.用户管理
    2.角色管理
    3.字典管理
    4.简单接口测试
    5.系统日志
    6.权限菜单管理
    7.待办事项
```   
### 开发中的模块
``` bash
    1.代码生成器
``` 
## 增删改查Controller
``` bash
    @Controller
    @RequestMapping("/api/sys/res")
    // 增删改查配置注解
    @CrudConfig(updateAllColumn = true,retrievePermission = "")
    // 权限前缀，最后与方法的Permission组合成 res:方法的Permission值
    @Permission(value = "res")
    // 登录拦截（必须为登录状态，在类上注解则拦截类下面所有方法，如果注解在方法上只拦截方法）
    @Logined
    public class ResController extends BaseCrudController<Res, IResService, Long> {
        private static Logger logger = LoggerFactory.getLogger(ResController.class);
        @Resource
        private IResService service;
    
        @Override
        protected IResService getService() {
            return this.service;
        }
    
        @Override
        protected Logger getLogger() {
            return logger;
        }

    }

    /*
        这样就有4个接口
        查询/api/sys/res/list 
        删除/api/sys/res/update 
        删除/api/sys/res/delete 
        更新/api/sys/res/create
        具体实现可以查看BaseCrudController源代码
    */
```   
### 增删改查Service
``` bash
    @Service
    public class DictServiceImpl extends CrudServiceImpl<DictMapper, Dict, Long> implements IDictService {
    
        /**
         * 您可以覆盖增删改查前增删改查后方法进行扩展，就不用为了一个简单的需求覆盖了整段增删改查方法
         * 如果返回ServiceResult不为NULL就使用该返回结果
         */
    
        /**
         * 数据插入前
         *
         * @param m
         * @return
         */
        protected ServiceResult createBefore(Dict m) {
            return null;
        }
    
        /**
         * 数据删除前
         *
         * @param ids
         * @return
         */
        protected ServiceResult deleteBefore(String ids) {
            return null;
        }
    
        /**
         * 数据更新前
         *
         * @param m 
         * @param oldM 旧对象
         * @return
         */
        protected ServiceResult updateBefore(Dict m, Dict oldM) {
            return null;
        }
    
        /**
         * 数据查询前
         *
         * @param m
         * @param ew
         * @return
         */
        protected ServiceRowsResult listBefore(Dict m, EntityWrapper<Dict> ew) {
            return null;
        }
    
        /**
         * 数据插入后
         *
         * @param m
         * @return
         */
        protected ServiceResult createAfter(Dict m) {
            return null;
        }
    
        /**
         * 数据删除后
         *
         * @param ids
         * @return
         */
        protected ServiceResult deleteAfter(String ids) {
            return null;
        }
    
        /**
         * 数据更新后
         *
         * @param m
         * @param oldM 旧对象
         * @return
         */
        protected ServiceResult updateAfter(Dict m, Dict oldM) {
            return null;
        }
    
        /**
         * 数据查询后
         *
         * @param m
         * @param resultList 查询结果集
         * @return
         */
        protected ServiceRowsResult listAfter(Dict m, List<Dict> resultList) {
            return null;
        }
    }
```   
> 更多详细请阅读代码,说明文档完善中...
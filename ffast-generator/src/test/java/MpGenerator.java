
import cn.ffast.core.utils.DateUtil;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * mybatis plus代码生成器
 */
public class MpGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        final int year = Calendar.getInstance().get(Calendar.YEAR);
        final String createTime = DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
        final String version = "1.0";

        final String projectBase = "D://projects//ffast_generator";
        String author = "dzy";

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectBase + "//src//main//java//");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor(author);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        //gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("coin_web");
        dsc.setPassword("dk93&*6FLJ39r");
        dsc.setUrl("jdbc:mysql://172.16.2.155:3306/coin_web?useUnicode=true&characterEncoding=utf-8");
        mpg.setDataSource(dsc);


        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("last_modify_time", FieldFill.INSERT_UPDATE));

        final String apiPrefix = "api";
        final String resPrefix = "web";
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[]{"t_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{"t_web_article","t_web_article_category","t_web_banner","t_web_config","t_web_friend"}); // 需要生成的表
        strategy.setTableFillList(tableFillList);
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        strategy.setSuperEntityClass("cn.ffast.core.support.BaseEntity");
        // 自定义实体，公共字段
        strategy.setSuperEntityColumns(new String[]{"id", "name", "creator_id", "create_time", "last_modify_time", "last_modifier_id"});
        // 自定义 mapper 父类
        //strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        strategy.setSuperServiceClass("cn.ffast.core.support.ICrudService");
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass("cn.ffast.core.support.impl.CrudServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass("cn.ffast.core.support.BaseCrudController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public OldUser setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        mpg.setStrategy(strategy);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.ffast");
        pc.setModuleName(null);
        pc.setController("controller." + resPrefix);
        pc.setMapper("dao." + resPrefix);
        pc.setXml("dao.mapper");
        pc.setService("service." + resPrefix);
        pc.setServiceImpl("service.impl." + resPrefix);
        pc.setEntity("entity." + resPrefix);
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("year", year);
                map.put("createTime", createTime);
                map.put("version", version);
                map.put("apiPrefix", apiPrefix);
                map.put("resPrefix", resPrefix);
                this.setMap(map);
            }
        };

        // 自定义 jsp  js 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String vueFileName = tableInfo.getEntityName().substring(0, 1).toLowerCase() +
                        tableInfo.getEntityName().substring(1, tableInfo.getEntityName().length()) + ".vue";
                return projectBase + "//pages//" + resPrefix + "//" + vueFileName;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 调整 xml 生成目录演示
         /*focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectBase+"//src//main//resources//sqlMapperXml//" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);*/
//        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();

        //tc.setController(null);
        //tc.setEntity(null);
        //tc.setMapper(null);
        //tc.setXml(null);
        //tc.setService(null);
        //tc.setServiceImpl(null);
//
        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
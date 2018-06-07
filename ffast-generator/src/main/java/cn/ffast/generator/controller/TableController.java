package cn.ffast.generator.controller;


import cn.ffast.core.vo.ServiceRowsResult;
import cn.ffast.generator.dao.TableDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/generator/table")
public class TableController {

    @Resource
    TableDao tableDao;


    @RequestMapping("/list")
    public ServiceRowsResult list(String id) {
        ServiceRowsResult result = new ServiceRowsResult(true);
        result.setPage(tableDao.listTable());
        return result;
    }
    @RequestMapping("/columns")
    public ServiceRowsResult info(String tableName) {
        ServiceRowsResult result = new ServiceRowsResult(true);
        result.setPage(tableDao.listTableColumn(tableName));
        return result;
    }
}
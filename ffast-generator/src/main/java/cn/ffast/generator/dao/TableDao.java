package cn.ffast.generator.dao;

import cn.ffast.generator.entity.Column;
import cn.ffast.generator.entity.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TableDao {

    @Select("select * from information_schema.TABLES where TABLE_SCHEMA=(select database())")
    List<Table> listTable();

    @Select("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName}")
    List<Column> listTableColumn(String tableName);
}


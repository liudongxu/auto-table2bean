package io.feikuai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import io.feikuai.model.Column;
import io.feikuai.model.Table;

/**
 * 数据库接口
 * @author liudo
 * @date 2018/12/26
 */
@Repository("gensDAO")
@Mapper
public interface GensDAO {

	/**
	 * 查询当前数据库中所有表
	 * @return
	 */
	@Select("select table_name tableName, engine, table_comment comments, create_time createTime from"
			+ " information_schema.tables where table_schema = (select database()) order by create_time desc")
	List<Table> queryTableList();

	/**
	 * 通过表明查询表元数据信息:
	 * <p>:表明、引擎、注释、创建时间
	 * @param tableName
	 * @return
	 */
	@Select("select table_name tableName, engine, table_comment tableComment, create_time createTime  "
			+ "	from information_schema.tables where table_schema = (select database()) and table_name = #{tableName}")
	Table queryTable(String tableName);

	/**
	 * 通过表明查询对应的字段元数据信息 列明、数据类型、注释、主键、其他信息
	 * @param tableName
	 * @return
	 */
	@Select("select column_name columnName, data_type dataType, column_comment comments, column_key columnKey, extra from "
			+ " information_schema.columns where table_name = #{tableName} and table_schema = (select database()) "
			+ " order by ordinal_position")
	List<Column> queryColumns(String tableName);

}

package io.feikuai.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 数据库表信息
 * @author liudo
 * @date 2018/12/26
 */
@Data
public class Table implements Serializable {

	private static final long serialVersionUID = 3020920288246124367L;
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 表注释
	 */
	private String comments;
	/**
	 * 表主键
	 */
	private Column pk;
	/**
	 * 表所有列，除主键
	 */
	private List<Column> columns;
	/**
	 * 表明首字母大写
	 */
	private String className;

}

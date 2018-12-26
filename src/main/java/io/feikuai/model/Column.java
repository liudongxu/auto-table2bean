package io.feikuai.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 数据库表列信息
 * @author liudo
 * @date 2018/12/26
 */
@Data
public class Column implements Serializable {


	private static final long serialVersionUID = 3415935948313731279L;
	/**
	 * 列名
	 */
	private String columnName;

	/**
	 * 列 主外键
	 */
	private String columnKey;
	/**
	 * 列名类型
	 */
	private String dataType;
	/**
	 * 列注释
	 */
	private String comments;

	/**
	 * 属性名称 --- 第一个字母大写
	 */
	private String attrName;
	/**
	 * 属性类型
	 */
	private String attrType;
	/**
	 * 其他信息
	 */
	private String extra;
}

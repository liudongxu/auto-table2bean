package io.feikuai.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.feikuai.dao.GensDAO;
import io.feikuai.model.Column;
import io.feikuai.model.Table;
import io.feikuai.service.GensService;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码生成服务实现类
 * @author liudo
 * @date 2018/12/26
 */
@Slf4j
@Service("gensService")
public class GensServiceImpl implements GensService {
	/**
	 * 代码生成DAO
	 */
	@Resource(name = "gensDAO")
	private GensDAO gensDAO;

	/**
	 * 表明前缀
	 */
	@Value("${table.prefix}")
	private String tablePrefix;

	/**
	 * 主要路径
	 */
	@Value("${main.path}")
	private String mainPath;
	/**
	 * 包名
	 */
	@Value("${package}")
	private String packages;

	/**
	 * 模块名称
	 */
	@Value("${module.name}")
	private String moduleName;

	/**
	 * 开发者
	 */
	@Value("${author}")
	private String author;

	/**
	 * 开发者邮箱
	 */
	@Value("${email}")
	private String email;
	/**
	 * 类型配置文件实例
	 */
	@Resource(name = "typeConfiguration")
	private Configuration typeConfiguration;

	/**
	 * 生成整个数据库表
	 */
	@Override
	public byte[] gensCodes() throws IOException {
		List<Table> tables = gensDAO.queryTableList();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (Table table : tables) {
			List<Column> columns = gensDAO.queryColumns(table.getTableName());
			gensCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 生成指定表
	 */
	@Override
	public byte[] gensCode(String tableName) throws IOException {
		Table table = gensDAO.queryTable(tableName);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		List<Column> columns = gensDAO.queryColumns(table.getTableName());
		gensCode(table, columns, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 生成代码
	 * @param table
	 * @param columns
	 * @throws IOException 
	 */
	private void gensCode(Table table, List<Column> columns, ZipOutputStream zip) throws IOException {
		if (StringUtils.isNotBlank(tablePrefix)) {
			// 替换掉表前缀
			table.setTableName(table.getTableName().replace(tablePrefix, ""));
		}
		// 所有-分隔的单词转换为大写单词，即每个单词由一个标题字符组成，然后是一系列小写字符
		table.setClassName(WordUtils.capitalizeFully(table.getTableName(), new char[]{'_'}).replace("_", ""));
		for (Column column : columns) {
			// 设置属性名称--把列表转换为java属性名
			column.setAttrName(StringUtils
					.uncapitalize(WordUtils.capitalizeFully(column.getColumnName(), new char[]{'_'}).replace("_", "")));
			// 设置属性类型
			column.setAttrType(typeConfiguration.getString(column.getDataType(), "unknow"));
			// 是否主键
			if ("PRI".equalsIgnoreCase(column.getColumnKey())) {
				table.setPk(column);
			}
		}
		table.setColumns(columns);
		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		mainPath = StringUtils.isBlank(mainPath) ? "io.feikuai" : mainPath;
		// 封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", table.getTableName());
		map.put("comments", table.getComments() == null ? "" : table.getComments());
		map.put("pk", table.getPk());
		map.put("className", table.getClassName());
		map.put("pathName", table.getClassName().toLowerCase());
		map.put("columns", table.getColumns());
		map.put("mainPath", mainPath);
		map.put("package", packages);
		map.put("moduleName", moduleName);
		map.put("author", author);
		map.put("email", email);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("datetime", df.format(Calendar.getInstance().getTime()));
		VelocityContext context = new VelocityContext(map);
		String template = "template/Bean.java.tpl";
		// 渲染模板
		StringWriter sw = new StringWriter();
		Template tpl = Velocity.getTemplate(template, "UTF-8");
		tpl.merge(context, sw);
		// 添加到zip
		zip.putNextEntry(new ZipEntry(getFileName(template, table.getClassName(), packages, moduleName)));
		IOUtils.write(sw.toString(), zip, "UTF-8");
		IOUtils.closeQuietly(sw);
		zip.closeEntry();
	}

	/**
	 * 获取文件名称
	 * @param template
	 * @param className
	 * @param packageName
	 * @param moduleName
	 * @return
	 */
	private String getFileName(String template, String className, String packageName, String moduleName) {
		String packagePath = "main" + File.separator + "java" + File.separator;
		if (StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
		}
		String path = packagePath + className + ".java";
		log.info("path:" + path);
		return path;
	}
}

package com.hyzhangjr.generator.mybatis;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

public class MapperPlugin extends PluginAdapter {

	private boolean caseSensitive = false;
	
	// 数据库模式
	private String schema;
	
	@Override
	public void setContext(Context context) {
		super.setContext(context);
		// 设置默认的注释生成器
		CommentGeneratorConfiguration commentCfg = new CommentGeneratorConfiguration();
		commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
		context.setCommentGeneratorConfiguration(commentCfg);
		// 支持oracle获取注释#114
		context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
	}
	
	@Override
	public boolean validate(List<String> arg0) {		
		return true;
	}

	/**
	 * 生成的Mapper接口
	 *
	 * @param interfaze
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		// 获取实体类
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// import接口
		/*for (String mapper : mappers) {
			interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
			interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
		}*/
		//interfaze.addImportedType(new FullyQualifiedJavaType("com.hyzhangjr.mybatis.mapper.BaseMapper"));
		//interfaze.addSuperInterface(new FullyQualifiedJavaType("com.hyzhangjr.mybatis.mapper.BaseMapper" + "<" + entityType.getShortName() + ">"));
		
		// import实体类
		interfaze.addImportedType(entityType);
		return true;
	}
	
	/**
	 * 生成基础实体类
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		processEntityClass(topLevelClass, introspectedTable);
		return true;
	}
	
	/**
	 * 处理实体类的包和@Table注解
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// 引入JPA注解
		topLevelClass.addImportedType("javax.persistence.*");
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		// 如果包含空格，或者需要分隔符，需要完善
		if (StringUtility.stringContainsSpace(tableName)) {
			tableName = context.getBeginningDelimiter() + tableName + context.getEndingDelimiter();
		}
		// 是否忽略大小写，对于区分大小写的数据库，会有用
		if (caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
			topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
		} else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
			topLevelClass.addAnnotation("@Table(name = \"" + tableName+ "\")");
		} else if (StringUtility.stringHasValue(schema)) {
			topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
		}
	}
}

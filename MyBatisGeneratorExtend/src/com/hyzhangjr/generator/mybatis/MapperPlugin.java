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
	
	// ���ݿ�ģʽ
	private String schema;
	
	@Override
	public void setContext(Context context) {
		super.setContext(context);
		// ����Ĭ�ϵ�ע��������
		CommentGeneratorConfiguration commentCfg = new CommentGeneratorConfiguration();
		commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
		context.setCommentGeneratorConfiguration(commentCfg);
		// ֧��oracle��ȡע��#114
		context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
	}
	
	@Override
	public boolean validate(List<String> arg0) {		
		return true;
	}

	/**
	 * ���ɵ�Mapper�ӿ�
	 *
	 * @param interfaze
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		// ��ȡʵ����
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		// import�ӿ�
		/*for (String mapper : mappers) {
			interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
			interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
		}*/
		//interfaze.addImportedType(new FullyQualifiedJavaType("com.hyzhangjr.mybatis.mapper.BaseMapper"));
		//interfaze.addSuperInterface(new FullyQualifiedJavaType("com.hyzhangjr.mybatis.mapper.BaseMapper" + "<" + entityType.getShortName() + ">"));
		
		// importʵ����
		interfaze.addImportedType(entityType);
		return true;
	}
	
	/**
	 * ���ɻ���ʵ����
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
	 * ����ʵ����İ���@Tableע��
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// ����JPAע��
		topLevelClass.addImportedType("javax.persistence.*");
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		// ��������ո񣬻�����Ҫ�ָ�������Ҫ����
		if (StringUtility.stringContainsSpace(tableName)) {
			tableName = context.getBeginningDelimiter() + tableName + context.getEndingDelimiter();
		}
		// �Ƿ���Դ�Сд���������ִ�Сд�����ݿ⣬������
		if (caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
			topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
		} else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
			topLevelClass.addAnnotation("@Table(name = \"" + tableName+ "\")");
		} else if (StringUtility.stringHasValue(schema)) {
			topLevelClass.addAnnotation("@Table(name = \"" + tableName + "\")");
		}
	}
}

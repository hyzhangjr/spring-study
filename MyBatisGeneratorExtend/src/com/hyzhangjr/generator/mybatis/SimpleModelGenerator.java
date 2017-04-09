package com.hyzhangjr.generator.mybatis;


import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.messages.Messages;

//Model文件生成处理器,可自定义
public class SimpleModelGenerator extends AbstractJavaGenerator {

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
		this.progressCallback.startTask(Messages.getString("Progress.8", table.toString()));
		Plugin plugins = this.context.getPlugins();
		CommentGenerator commentGenerator = this.context.getCommentGenerator();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);

		FullyQualifiedJavaType superClass = getSuperClass();
		if (superClass != null) {
			topLevelClass.setSuperClass(superClass);
			topLevelClass.addImportedType(superClass);
		}

		commentGenerator.addModelClassComment(topLevelClass, this.introspectedTable);

		List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getAllColumns();

		/*
		 * 如果实体存在主键，添加两个构造函数
		 */
		if (this.introspectedTable.getPrimaryKeyColumns().size() > 0) {
			// 添加默认构造函数
			addDefaultConstructor(topLevelClass);
			// 添加构造函数，参数包含所有主键字段
			addParameterizedConstructorWithPk(topLevelClass);
		}

		StringBuilder formatString = new StringBuilder();
		StringBuilder formatFields = new StringBuilder();
		String rootClass = getRootClass();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			if (RootClassInfo.getInstance(rootClass, this.warnings).containsProperty(introspectedColumn)) {
				continue;
			}

			Field field = JavaBeansUtil.getJavaBeansField(introspectedColumn, this.context, this.introspectedTable);
			if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, this.introspectedTable,
					Plugin.ModelClassType.BASE_RECORD)) {
				topLevelClass.addField(field);
				topLevelClass.addImportedType(field.getType());
			}

			Method method = JavaBeansUtil.getJavaBeansGetter(introspectedColumn, this.context, this.introspectedTable);
			if (plugins.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, this.introspectedTable,
					Plugin.ModelClassType.BASE_RECORD)) {
				topLevelClass.addMethod(method);
			}

			if (!(this.introspectedTable.isImmutable())) {
				method = JavaBeansUtil.getJavaBeansSetter(introspectedColumn, this.context, this.introspectedTable);
				if (plugins.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn,
						this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
					topLevelClass.addMethod(method);
				}
			}

			formatString.append(",");
			formatString.append(field.getName());
			formatString.append("=%s");
			formatFields.append(", this.");
			formatFields.append(field.getName());
		}

		StringBuilder toStringBody = new StringBuilder("return ");
		if (formatString.length() > 0) {
			toStringBody.append("String.format(\"{");
			toStringBody.append(formatString.substring(1));
			toStringBody.append("}\"");
			toStringBody.append(formatFields);
			toStringBody.append(");");
		}

		/*
		 * 覆盖toString方法
		 */
		Method method = new Method();
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(PrimitiveTypeWrapper.getStringInstance());
		method.setName("toString");
		method.addBodyLine(toStringBody.toString());
		topLevelClass.addMethod(method);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (this.context.getPlugins().modelBaseRecordClassGenerated(topLevelClass, this.introspectedTable)) {
			answer.add(topLevelClass);
		}
		return answer;
	}

	private FullyQualifiedJavaType getSuperClass() {
		String rootClass = getRootClass();
		FullyQualifiedJavaType superClass;
		if (rootClass != null)
			superClass = new FullyQualifiedJavaType(rootClass);
		else {
			superClass = null;
		}

		return superClass;
	}
	

	private void addParameterizedConstructorWithPk(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.setName(topLevelClass.getType().getShortName());
		this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);

		List<IntrospectedColumn> constructorColumns = this.introspectedTable.getPrimaryKeyColumns();

		for (IntrospectedColumn introspectedColumn : constructorColumns) {
			method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(),
					introspectedColumn.getJavaProperty()));
		}

		StringBuilder sb = new StringBuilder();
		if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
			boolean comma = false;
			sb.append("super(");
			for (IntrospectedColumn introspectedColumn : this.introspectedTable.getPrimaryKeyColumns()) {
				if (comma)
					sb.append(", ");
				else {
					comma = true;
				}
				sb.append(introspectedColumn.getJavaProperty());
			}
			sb.append(");");
			method.addBodyLine(sb.toString());
		}

		List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();

		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			sb.setLength(0);
			sb.append("this.");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" = ");
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		topLevelClass.addMethod(method);
	}
	
}

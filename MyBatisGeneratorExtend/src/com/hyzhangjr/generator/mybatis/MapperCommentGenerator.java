package com.hyzhangjr.generator.mybatis;

import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;

//注释处理器,可自定义
public class MapperCommentGenerator implements CommentGenerator {

	public MapperCommentGenerator(){
		super();
	}
	
	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		
	}

	//生成model文件注释
	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable,
			boolean markAsDoNotDelete) {
		StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); 
        innerClass
                .addJavaDocLine(" * This class was generated by MyBatis Generator."); 

        sb.append(" * This class corresponds to the database table "); 
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */"); 
	}
	
	//生成model文件注释
	protected void addJavadocTag(JavaElement javaElement,
            boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); 
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); 
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge"); 
        }
        String s = (new Date()).toString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

	//生成*mapper.xml文件的注释
	@Override
	public void addComment(XmlElement xmlElement) {		
		xmlElement.addElement(new TextElement("<!--"));
        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement.addElement(new TextElement("-->"));
	}

	@Override
	public void addConfigurationProperties(Properties arg0) {
		
	}

	@Override
	public void addEnumComment(InnerEnum arg0, IntrospectedTable arg1) {
		
	}

	 /**
     * 给字段添加数据库备注
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
		
	}

	//生成model文件注释
	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {		
		if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
        //添加注解
        if (field.isTransient()) {
            //@Column
            field.addAnnotation("@Transient");
        }
        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }
        String column = introspectedColumn.getActualColumnName();
        if (StringUtility.stringContainsSpace(column) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            column = introspectedColumn.getContext().getBeginningDelimiter()
                    + column
                    + introspectedColumn.getContext().getEndingDelimiter();
        }
        if (!column.equals(introspectedColumn.getJavaProperty())) {
            //@Column
            field.addAnnotation("@Column(name = \"" + column + "\")");
        } 
        if (introspectedColumn.isIdentity()) {
            if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        } else if (introspectedColumn.isSequenceColumn()) {
            field.addAnnotation("@SequenceGenerator(name=\"\",sequenceName=\"" + introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement() + "\")");
        }
	}

	//生成Mapper.java文件的注释
	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**"); 
        //method.addJavaDocLine(" * This method was generated by MyBatis Generator."); 
        //sb.append(" * This method corresponds to the database table "); 
        sb.append(introspectedTable.getFullyQualifiedTable());
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); 
	}

	//生成model文件注释
	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * 获取");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getActualColumnName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" - ");
            sb.append(introspectedColumn.getRemarks());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");

	}

	@Override
	public void addJavaFileComment(CompilationUnit arg0) {
		
	}

	//生成model文件注释
	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		String remarks = introspectedTable.getRemarks();
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * " + remarks);
		topLevelClass.addJavaDocLine(" * ");
		topLevelClass.addJavaDocLine(" * @mbg.generated");
		topLevelClass.addJavaDocLine(" */");
	}

	//生成*mapper.xml文件的注释
	@Override
	public void addRootComment(XmlElement arg0) {
		
	}

	//生成model文件注释
	@Override
	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn) {
		StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" * 设置");
            sb.append(introspectedColumn.getRemarks());
            method.addJavaDocLine(sb.toString());
            method.addJavaDocLine(" *");
        }
        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            sb.append(" ");
            sb.append(introspectedColumn.getRemarks());
        }
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");

	}

}

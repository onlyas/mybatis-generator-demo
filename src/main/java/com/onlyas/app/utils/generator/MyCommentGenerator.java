package com.onlyas.app.utils.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

public class MyCommentGenerator implements CommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;
    /** If suppressAllComments is true, this option is ignored. */
    private boolean addRemarkComments;
    private SimpleDateFormat dateFormat;

    public MyCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        if (null == introspectedColumn.getRemarks()) {
            String id = introspectedTable.getContext().getId();
            if (MBGlobal.dbColumnRemarkMap.containsKey(id)) {
                Map<String, Object> tableMap = (Map<String, Object>) MBGlobal.dbColumnRemarkMap.get(id);
                String table =introspectedTable.getFullyQualifiedTableNameAtRuntime();
                if (tableMap.containsKey(table)) {
                    Map<String, Object> colMap = (Map<String, Object>) tableMap.get(table);
                    if (colMap.containsKey(introspectedColumn.getActualColumnName())) {
                        sb.append(colMap.get(introspectedColumn.getActualColumnName()));
                    }
                }
            } else {
                sb.append(introspectedColumn.getActualColumnName());
            }
        } else {
            if (introspectedColumn.getRemarks().isEmpty())
                sb.append(introspectedColumn.getActualColumnName());
            else
                sb.append(introspectedColumn.getRemarks());
        }
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        topLevelClass.addJavaDocLine("/**");
        sb.append(" * 表名: ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" ");
        topLevelClass.addJavaDocLine(sb.toString());

        String id = introspectedTable.getContext().getId();
        String remark="";
        if (MBGlobal.dbTableRemarkMap.containsKey(id)) {
            Map<String, Object> tableMap = (Map<String, Object>) MBGlobal.dbTableRemarkMap.get(id);
            String table = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            if(tableMap.containsKey(table)){
                remark=tableMap.get(table).toString();
            }else{
                remark=introspectedTable.getRemarks();
            }
        }else
            remark=introspectedTable.getRemarks();
        sb.setLength(0);
        sb.append(" * @Description ");
        sb.append(remark);

        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(" MBG ");
        sb.append(" ");
        sb.append(getDateString());
        topLevelClass.addJavaDocLine(sb.toString());

        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" ");
        sb.append(getDateString());
        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    protected String getDateString() {
        if (suppressDate) {
            return "";
        } else if (dateFormat != null) {
            return dateFormat.format(new Date());
        } else {
            return new Date().toString();
        }
    }
}

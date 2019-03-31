package com.onlyas.app.utils.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.runtime.dynamic.sql.IntrospectedTableMyBatis3DynamicSqlImpl;

import java.util.Iterator;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class SelectWithNolockPlugin extends PluginAdapter {

    /**
     * 判断数据库是否支持nolock
     */
    private boolean isSupportNolock = false;

    /**
     * 根据DriverClass判断是否支持nolock
     */
    public void checkIsSupportNolock() {
        String driverClass = context.getJdbcConnectionConfiguration().getDriverClass();
        if (driverClass.contains("sqlserver")) {
            isSupportNolock = true;
        } else {
            isSupportNolock = false;
        }
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * 代码参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.CountByExampleElementGenerator
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        //return super.sqlMapCountByExampleElementGenerated(element, introspectedTable);
        checkIsSupportNolock();
        element.getElements().clear();

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        if (isSupportNolock)
            sb.append(" with (nolock) ");
        element.addElement(new TextElement(sb.toString()));
        element.addElement(getExampleIncludeElement(introspectedTable));

        return true;
    }

    /**
     * 代码参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithoutBLOBsElementGenerator
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        //return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
        checkIsSupportNolock();
        element.getElements().clear();
        element.addElement(new TextElement("select")); //$NON-NLS-1$
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        element.addElement(ifElement);

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable
                .getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            element.addElement(new TextElement(sb.toString()));
        }
        element.addElement(getBaseColumnListElement(introspectedTable));

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        if (isSupportNolock)
            sb.append(" with (nolock) ");
        element.addElement(new TextElement(sb.toString()));
        element.addElement(getExampleIncludeElement(introspectedTable));

        ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
        element.addElement(ifElement);

        return true;
    }

    /**
     * 代码参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByExampleWithBLOBsElementGenerator
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        //return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
        checkIsSupportNolock();
        element.getElements().clear();
        element.addElement(new TextElement("select")); //$NON-NLS-1$
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        element.addElement(ifElement);

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable
                .getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            element.addElement(new TextElement(sb.toString()));
        }

        element.addElement(getBaseColumnListElement(introspectedTable));
        element.addElement(new TextElement(",")); //$NON-NLS-1$
        element.addElement(getBlobColumnListElement(introspectedTable));

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        if (isSupportNolock)
            sb.append(" with (nolock) ");
        element.addElement(new TextElement(sb.toString()));
        element.addElement(getExampleIncludeElement(introspectedTable));

        ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
        element.addElement(ifElement);
        return true;
    }

    /**
     * 代码参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByPrimaryKeyElementGenerator
     * 和参考自org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectByPrimaryKeyElementGenerator
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        //return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
        checkIsSupportNolock();
        String type = context.getTargetRuntime();
        if (!stringHasValue(type)) {
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if ("MyBatis3".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if ("MyBatis3Simple".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableMyBatis3SimpleImpl.class.getName();
        } else if ("MyBatis3DynamicSql".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableMyBatis3DynamicSqlImpl.class.getName();
        }

        if (type.equals(IntrospectedTableMyBatis3Impl.class.getName())) {
            element.getElements().clear();
            StringBuilder sb = new StringBuilder();
            sb.append("select "); //$NON-NLS-1$

            if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
                sb.append('\'');
                sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
                sb.append("' as QUERYID,"); //$NON-NLS-1$
            }
            element.addElement(new TextElement(sb.toString()));
            element.addElement(getBaseColumnListElement(introspectedTable));
            if (introspectedTable.hasBLOBColumns()) {
                element.addElement(new TextElement(",")); //$NON-NLS-1$
                element.addElement(getBlobColumnListElement(introspectedTable));
            }

            sb.setLength(0);
            sb.append("from "); //$NON-NLS-1$
            sb.append(introspectedTable
                    .getAliasedFullyQualifiedTableNameAtRuntime());
            if (isSupportNolock)
                sb.append(" with (nolock) ");
            element.addElement(new TextElement(sb.toString()));

            boolean and = false;
            for (IntrospectedColumn introspectedColumn : introspectedTable
                    .getPrimaryKeyColumns()) {
                sb.setLength(0);
                if (and) {
                    sb.append("  and "); //$NON-NLS-1$
                } else {
                    sb.append("where "); //$NON-NLS-1$
                    and = true;
                }

                sb.append(MyBatis3FormattingUtilities
                        .getAliasedEscapedColumnName(introspectedColumn));
                sb.append(" = "); //$NON-NLS-1$
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn));
                element.addElement(new TextElement(sb.toString()));
            }
            return true;
        } else if (type.equals(IntrospectedTableMyBatis3SimpleImpl.class.getName())) {
            element.getElements().clear();
            StringBuilder sb = new StringBuilder();
            sb.append("select "); //$NON-NLS-1$

            if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
                sb.append('\'');
                sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
                sb.append("' as QUERYID,"); //$NON-NLS-1$
            }

            Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                    .iterator();
            while (iter.hasNext()) {
                sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
                        .next()));

                if (iter.hasNext()) {
                    sb.append(", "); //$NON-NLS-1$
                }

                if (sb.length() > 80) {
                    element.addElement(new TextElement(sb.toString()));
                    sb.setLength(0);
                }
            }

            if (sb.length() > 0) {
                element.addElement(new TextElement(sb.toString()));
            }

            sb.setLength(0);
            sb.append("from "); //$NON-NLS-1$
            sb.append(introspectedTable
                    .getAliasedFullyQualifiedTableNameAtRuntime());
            if (isSupportNolock)
                sb.append(" with (nolock) ");
            element.addElement(new TextElement(sb.toString()));

            boolean and = false;
            for (IntrospectedColumn introspectedColumn : introspectedTable
                    .getPrimaryKeyColumns()) {
                sb.setLength(0);
                if (and) {
                    sb.append("  and "); //$NON-NLS-1$
                } else {
                    sb.append("where "); //$NON-NLS-1$
                    and = true;
                }

                sb.append(MyBatis3FormattingUtilities
                        .getAliasedEscapedColumnName(introspectedColumn));
                sb.append(" = "); //$NON-NLS-1$
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn));
                element.addElement(new TextElement(sb.toString()));
            }
            return true;
        } else {
            return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
        }
    }

    /**
     * 代码参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        //return super.sqlMapSelectAllElementGenerated(element, introspectedTable);
        element.getElements().clear();
        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns()
                .iterator();
        while (iter.hasNext()) {
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
                    .next()));

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                element.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            element.addElement(new TextElement(sb.toString()));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        if (isSupportNolock)
            sb.append(" with (nolock) ");
        element.addElement(new TextElement(sb.toString()));

        String orderByClause = introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
        if (hasOrderBy) {
            sb.setLength(0);
            sb.append("order by "); //$NON-NLS-1$
            sb.append(orderByClause);
            element.addElement(new TextElement(sb.toString()));
        }
        return true;
    }

    /**
     * 该方法参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator
     * 新增IntrospectedTable类型参数
     *
     * @param introspectedTable
     * @return
     */
    protected XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBaseColumnListId()));
        return answer;
    }

    /**
     * 该方法参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator
     * 新增IntrospectedTable类型参数
     *
     * @param introspectedTable
     * @return
     */
    protected XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBlobColumnListId()));
        return answer;
    }

    /**
     * 该方法参考org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator
     * 新增IntrospectedTable类型参数
     *
     * @param introspectedTable
     * @return
     */
    protected XmlElement getExampleIncludeElement(IntrospectedTable introspectedTable) {
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "_parameter != null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getExampleWhereClauseId()));
        ifElement.addElement(includeElement);

        return ifElement;
    }
}

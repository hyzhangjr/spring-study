package com.hyzhangjr.spring.ds;

import com.hyzhangjr.spring.Constance;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class MultipleDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }
    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    public static String getDataSourceKey() {
        String name = dataSourceKey.get();
        if (name == null || name.length() == 0) {
            //默认使用该数据源
            return Constance.DEFAULT_DS_NAME;
        }
        return name;
    }

}

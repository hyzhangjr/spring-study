package com.hyzhangjr.spring.ds.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.hyzhangjr.spring.Constance;
import com.hyzhangjr.spring.ds.MultipleDataSource;
import com.hyzhangjr.spring.ds.annotation.DataSourceSelector;


@Component
public class DataSourceSelectAdvisor implements MethodBeforeAdvice, Ordered {

	public int getOrder() {
        return 1;
    }
	
	//根据DataSourceSelector注释自动切换数据源
    public void before(Method m, Object[] args, Object target) throws Throwable {
    	String targetName = target.getClass().getName();
		Method[] methods = Class.forName(targetName).getMethods();
		String dsname = null;
		for (Method method : methods) {
			if (this.isSameMethod(m, method)) {
				DataSourceSelector a = method.getAnnotation(DataSourceSelector.class);
				if (a != null) {
					dsname = a.value();
					MultipleDataSource.setDataSourceKey(dsname);
					
				} else {					
					MultipleDataSource.setDataSourceKey(Constance.DEFAULT_DS_NAME);
				}
				break;
			}
		}    	      
    }

    
    
    @SuppressWarnings("rawtypes")
	private boolean isSameMethod(Method m1, Method m2) {
		if (!m1.getName().equals(m2.getName())) return false;
		
		Class[] m1Clazzs = m1.getParameterTypes();
		Class[] m2Clazzs = m2.getParameterTypes();
		
		if (m1Clazzs.length != m2Clazzs.length) return false;
		
		for (int i = 0; i < m1Clazzs.length; i++) {
			if (!m1Clazzs[i].getName().equals(m2Clazzs[i].getName())) {
				return false;
			}
		}
		
		return true;
	}
}

package com.hyzhangjr.spring.test;

import java.lang.reflect.Field;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//指定bean注入的配置文件  
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
//使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTestCase extends AbstractTransactionalJUnit4SpringContextTests {

	@Override
	@Resource(name = "multipleDataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Object getProxyTargetObject(Object proxy) {
		if (!AopUtils.isAopProxy(proxy)) {
			return proxy;
		}
		Field h;
		try {
			h = proxy.getClass().getSuperclass().getDeclaredField("h");

			h.setAccessible(true);
			AopProxy aopProxy = (AopProxy) h.get(proxy);

			Field advised = aopProxy.getClass().getDeclaredField("advised");
			advised.setAccessible(true);
			return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
		} catch (Exception e) {
			e.printStackTrace();
			return proxy;
		}
	}
}

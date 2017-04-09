package com.hyzhangjr.spring.test.demo.impl;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.hyzhangjr.spring.Constance;
import com.hyzhangjr.spring.bean.DemoBean;
import com.hyzhangjr.spring.bean.User;
import com.hyzhangjr.spring.ds.annotation.DataSourceSelector;
import com.hyzhangjr.spring.service.DemoService;
import com.hyzhangjr.spring.test.SpringTestCase;

@ActiveProfiles({ "mapper-mock-test" })
public class DemoServiceImplTest extends SpringTestCase{
	private User user;
	private DemoBean bean;	

	@Resource
	DemoService demoService;
	
	@Before
	public void setUp() {
		user = new User();
        user.setId("181");
        user.setUsername("demo");
        user.setUserage("20");
        user.setUseraddress("test");
		
        bean = new DemoBean();
        bean.setId("1");
        bean.setName("demo");
	}
	
	@Test
	@DataSourceSelector(Constance.DEFAULT_DS_ONE)
	public void testInsertUserService(){
		int result = demoService.insertUser(user);
		Assert.assertEquals(1, result);
	}
	
	@Test
	public void testInserDemoService(){
		int result = demoService.insertDemo(bean);
		Assert.assertEquals(1, result);
	}
}

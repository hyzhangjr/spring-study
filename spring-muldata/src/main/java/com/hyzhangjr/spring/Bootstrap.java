package com.hyzhangjr.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyzhangjr.spring.service.DemoService;


public class Bootstrap {

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();

        DemoService demoService = (DemoService)context.getBean("demoService");
        demoService.dataSourceDemo();
        
    }
}

package com.hyzhangjr.spring.action;

import javax.annotation.Resource;

import com.hyzhangjr.spring.service.DemoService;


public class BootstrapAction {

    @Resource
    DemoService demoService;

    public void start(){
        System.out.println("start");
        //demoService.dataSourceDemo();
    }
}

package com.hyzhangjr.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hyzhangjr.spring.Constance;
import com.hyzhangjr.spring.bean.DemoBean;
import com.hyzhangjr.spring.bean.User;
import com.hyzhangjr.spring.ds.annotation.DataSourceSelector;
import com.hyzhangjr.spring.mapper.DemoMapper;
import com.hyzhangjr.spring.service.DemoService;



@Service("demoService")
public class DemoServiceImpl implements DemoService {
    @Autowired
    DemoMapper demoMapper;

    @Transactional(propagation = Propagation.REQUIRED)   
    public void dataSourceDemo() {
        
        User user = new User();
        user.setId("180");
        user.setUsername("demo");
        user.setUserage("20");
        user.setUseraddress("test");
        insertUser(user);
    	
    	
        DemoBean bean = new DemoBean();
        bean.setId("1");
        bean.setName("demo");
        insertDemo(bean);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @DataSourceSelector
    private void selectUser(){
    	User seluser = demoMapper.selectUser();
        System.out.println(seluser.getId());
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @DataSourceSelector
    public int insertUser(User user){
       return demoMapper.insertUser(user);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @DataSourceSelector(Constance.DEFAULT_DS_TWO)
    public int insertDemo(DemoBean bean){
       return demoMapper.insertDemo(bean);
    }
}

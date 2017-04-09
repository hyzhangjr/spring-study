package com.hyzhangjr.spring.service;

import com.hyzhangjr.spring.bean.DemoBean;
import com.hyzhangjr.spring.bean.User;

/**
 * Created by kfzx-zhangjr on 2017/4/9.
 */
public interface DemoService {
    public void dataSourceDemo();
    
    public int insertUser(User user);
    
    public int insertDemo(DemoBean bean);
}

package com.hyzhangjr.spring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import com.hyzhangjr.spring.bean.DemoBean;
import com.hyzhangjr.spring.bean.User;



@MapperScan
public interface DemoMapper {
    @Insert("insert into users(ID,USERNAME,USERAGE,USERADDRESS) values(#{id}," +
            "#{username}," +
            "#{userage}," +
            "#{useraddress}" +
            ")")
    public int insertUser(User user);

    @Select("select * from users where rownum = 1 ")
    public User selectUser();
    
    @Insert("insert into demo_table(id,name) values(#{id},#{name})")
    public int insertDemo(DemoBean bean);
    
}

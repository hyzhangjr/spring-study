package com.hyzhangjr.test.mapper;

import com.hyzhangjr.test.entity.DemoTable;
import java.util.List;

public interface DemoTableMapper {
    /**
    CTP4.DEMO_TABLE
     *
     * @mbg.generated Sun Apr 09 22:19:28 CST 2017
     */
    int deleteByPrimaryKey();

    /**
    CTP4.DEMO_TABLE
     *
     * @mbg.generated Sun Apr 09 22:19:28 CST 2017
     */
    int insert(DemoTable record);

    /**
    CTP4.DEMO_TABLE
     *
     * @mbg.generated Sun Apr 09 22:19:28 CST 2017
     */
    DemoTable selectByPrimaryKey();

    /**
    CTP4.DEMO_TABLE
     *
     * @mbg.generated Sun Apr 09 22:19:28 CST 2017
     */
    List<DemoTable> selectAll();

    /**
    CTP4.DEMO_TABLE
     *
     * @mbg.generated Sun Apr 09 22:19:28 CST 2017
     */
    int updateByPrimaryKey(DemoTable record);
}
# spring-study
# <h2>Spring学习</h2> #
**自己学习和使用Spring时写的一些案例**

一、多数据源配置spring-muldata项目

在实际项目中Spring+Mybatis需要使用到多数据源切换，针对Spring的多数据源配置写的案例
主要原理是利用了AOP实现：
1、MultipleDataSource继承AbstractRoutingDataSource利用ThreadLocal变量注入要切换的数据源，数据源通过注释DataSourceSelector传入
2、定义Aspect类DataSourceSelectAdvisor，通过before在执行前进行数据源选择
3、启动Bootstrap就可看到示例运行结果

# spring-study
# <h2>Spring学习</h2> #
**自己学习和使用Spring时写的一些案例**

###一、多数据源配置spring-muldata项目###

在实际项目中Spring+Mybatis需要使用到多数据源切换，针对Spring的多数据源配置写的案例
主要原理是利用了AOP实现：

* 1、MultipleDataSource继承AbstractRoutingDataSource利用ThreadLocal变量注入要切换的数据源，数据源通过注释DataSourceSelector传入
* 2、定义Aspect类DataSourceSelectAdvisor，通过before在执行前进行数据源选择
* 3、启动Bootstrap就可看到示例运行结果



###二、mybatis代码自动生成器扩展 MyBatisGeneratorExtend项目###

在Spring中用到了mybatis时接触到mybatis-generator用来自动生成model和mapper,发现原生生成的不是很好,看到有人扩展过就学习了下
扩展时主要有几点：
* 1、context中targetRuntime属性默认MyBatis3或MyBatis3Simple是可以修改,自继承IntrospectedTableMyBatis3SimpleImpl实现如何生成文件,见MyBatis3SimpleImpl
* 2、Model文件如何生成也可自定义，继承AbstractJavaGenerator实现，getCompilationUnits方法中可自定义生成内容，见SimpleModelGenerator
* 3、Mapper和xml文件也可自定义，继承AbstractJavaClientGenerator实现，getCompilationUnits可自定义生成内容，见SimpleJavaClientGenerator。其中XML文件生成也可自定义，本次直接使用默认的SimpleXMLMapperGenerator
* 4、对于各个文件生成时的注释也可自定义，实现CommentGenerator接口并将各个环节要自定义的注释在对应方法实现即可，见MapperCommentGenerator
* 5、最后还有个插件，用于在代码生成过程的不同阶段被调用的方法，扩展适配器类org.mybatis.generator.api.PluginAdapter即可，见MapperPlugin
主要是modelBaseRecordClassGenerated在model实体生成时触发，clientGenerated在mapper生成时触发，其他还有各个方法调用的触发。
摘一段官网介绍
    > 插件的生命周期
    > 插件有一个生命周期。插件在代码生成过程的初始化期间创建并且在这个过程中的不同阶段被调用。下面的列表显示了插件的基本生命周期：
    > 
    > 插件通过默认的构造函数创建
    > setContext方法被调用
    > setProperties方法被调用
    > validate方法被调用。如果该方法返回false ，那么插件中的其他方法都不会再被调用。
    > 对于配置中的每个表：
    > initialized方法被调用
    > Java客户端的方法：1,2
    > clientXXXMethodGenerated(Method, TopLevelClass, IntrospectedTable) - 当Java客户端实现类生成的时候这些方法被调用.
    > clientXXXMethodGenerated(Method, Interface, IntrospectedTable) -当Java客户端接口生成的时候这些方法被调用。
    > clientGenerated(Interface, TopLevelClass, IntrospectedTable)方法被调用
    > 模型方法：1
    > modelFieldGenerated, modelGetterMethodGenerated, modelSetterMethodGenerated for each field in the class
    > modelExampleClassGenerated(TopLevelClass, IntrospectedTable)
    > modelPrimaryKeyClassGenerated(TopLevelClass, IntrospectedTable)
    > modelBaseRecordClassGenerated(TopLevelClass, IntrospectedTable)
    > modelRecordWithBLOBsClassGenerated(TopLevelClass, IntrospectedTable)
    > SQL映射方法：1
    > sqlMapXXXElementGenerated(XmlElement, IntrospectedTable) - 当生成SQL映射的每个元素的时候这些方法被调用
    > sqlMapDocumentGenerated(Document, IntrospectedTable)
    > sqlMapDocument(GeneratedXmlFile, IntrospectedTable)
    > contextGenerateAdditionalJavaFiles(IntrospectedTable)方法被调用
    > contextGenerateAdditionalXmlFiles(IntrospectedTable)方法被调用
    > contextGenerateAdditionalJavaFiles()方法被调用
    > contextGenerateAdditionalXmlFiles()方法被调用
    > 注意事项:
    > * 1 -这些方法将被包装的代码生成器调用。如果您提供一个自定义的代码生成器，那么这些方法将仅在自定义代码生成调用它们时调用。
    > * 2 -Java客户端的方法只有当配置Java客户端生成器的时候会被调用。


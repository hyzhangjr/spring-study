package com.hyzhangjr.generator.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {		
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(Generator.class.getResource("generatorConfig.xml").getFile());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("生成JAVA文件如下：");
		for (GeneratedJavaFile file : myBatisGenerator.getGeneratedJavaFiles()) {
			System.out.println(file.getTargetPackage() + "." + file.getFileName());
		}

		System.out.println("生成XML文件如下：");
		for (GeneratedXmlFile file : myBatisGenerator.getGeneratedXmlFiles()) {
			System.out.println(file.getTargetPackage() + "." + file.getFileName());
		}
	}

}

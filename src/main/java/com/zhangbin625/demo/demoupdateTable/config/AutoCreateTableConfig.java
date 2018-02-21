package com.zhangbin625.demo.demoupdateTable.config;

import java.io.IOException;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlAnyAttribute;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.huawei.testcasebot.mybatisACT.core.manager.MysqTableManagerImpl;


@Configuration
//@ComponentScan(basePackages="com.mybatis.enhance.store.manager.*")//manager下有目录时的写法
@ComponentScan(basePackages="com.huawei.testcasebot.mybatisACT.core.manager")//manager下只有文件时的写法
public class AutoCreateTableConfig implements EnvironmentAware{
	private final String prefix = "mybatis.auto.create.table.";
	private PropertiesFactoryBean configProperties;
	
	@Override
	public void setEnvironment(Environment environment) {
		try {
			RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment, prefix);
			configProperties = new PropertiesFactoryBean();
			configProperties.setLocations(
					new DefaultResourceLoader().getResource((relaxedPropertyResolver.getProperty("Locations"))));
			configProperties.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Bean
	public PropertiesFactoryBean configProperties(){
		return configProperties;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		try {
			sqlSessionFactory.setDataSource(dataSource);
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources(configProperties.getObject().getProperty(prefix.concat("createTableMapping"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		try {
			mapperScannerConfigurer.setBasePackage(configProperties.getObject().getProperty(prefix.concat("createTableDao")));
			mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapperScannerConfigurer;
	}
}

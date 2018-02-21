package com.zhangbin625.demo.demoupdateTable.config;

import java.io.IOException;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


//@Configuration
@ComponentScan(basePackages="com.mybatis.enhance.store.manager.*")
public class AutoCreateTableConfig2 implements EnvironmentAware{
	private RelaxedPropertyResolver relaxedPropertyResolver;
	
	@Override
	public void setEnvironment(Environment environment) {
		relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "mybatis.auto.create.table.");
	}
	
	@Bean
	public PropertiesFactoryBean configProperties(){
		PropertiesFactoryBean configProperties = new PropertiesFactoryBean();
		configProperties.setLocations(new DefaultResourceLoader().getResource((relaxedPropertyResolver.getProperty("Locations"))));
		return configProperties;
	}
	
	@Bean
	public PreferencesPlaceholderConfigurer propertyConfigurer(@Qualifier("configProperties") PropertiesFactoryBean configProperties) throws IOException{
		PreferencesPlaceholderConfigurer propertyConfigurer = new PreferencesPlaceholderConfigurer();
		propertyConfigurer.setProperties(configProperties.getObject());
		return propertyConfigurer;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		try {
			sqlSessionFactory.setDataSource(dataSource);
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources(relaxedPropertyResolver.getProperty("defaultMapping")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.mybatis.enhance.store.dao.*");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}
}

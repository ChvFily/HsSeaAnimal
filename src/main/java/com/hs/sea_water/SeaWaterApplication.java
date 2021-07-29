package com.hs.sea_water;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;


@EnableScheduling // 开启定时任务功能
@SpringBootApplication
public class SeaWaterApplication {
	
	
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters(){
		//1、定义convert转换消息对象
		FastJsonHttpMessageConverter fasConverter  = new  FastJsonHttpMessageConverter();
		//2、添加fastJson的配置信息，比如：是否要格式化返回json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		//3、再convert中添加配置信息
		fasConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fasConverter;
		return new HttpMessageConverters(converter); 
	}
	
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(SeaWaterApplication.class, args);
	}

}

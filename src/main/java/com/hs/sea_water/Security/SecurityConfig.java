package com.hs.sea_water.Security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/**
	 * 所有的都可以访问 不需要登陆 
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable() // 禁用 post 安全问题 重在
		.headers().frameOptions().disable().and()
		.authorizeRequests()
		.antMatchers("/","/getTest/**","/showPage","srs/***",
				"/streams.do","/liveDetails/**","/clients",
				"/re/**","/seach**","/seachType**")
		.permitAll() ; // 都可以访问
	}
}

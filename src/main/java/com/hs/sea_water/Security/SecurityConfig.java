package com.hs.sea_water.Security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
				"/re/**","/seach**","/seachType**","/admin/" 
				,"/admin/getContent","/admin/getType","/amdin/getLunbo",
				"/admin/getAbout","/admin/contentData.json","/admin/contentAdd"
				,"/admin/contentSave","/admin/uploadImg","/admin/goLook",
				"/admin/uploadVideo","/admin/contentEdit" ,"/admin/contentUpData"
				,"/admin/delOne","/admin/delSelects").permitAll();
//		.antMatchers().hasRole("admin"); // 都可以访问
		
		
////	    没有权限默认会到登录页面
//        http.formLogin().loginPage("/toLogin");
//    //    防止网站攻击
//    //     http.csrf().disable();//关闭csrf功能
//    //    开启注销功能
//        http.logout().logoutSuccessUrl("/");
//
//    //    开启记住我功能
//        http.rememberMe().rememberMeParameter("remember");
	}
	
	
	//认证，在springboot 2.1.x中可以直接使用
    //密码编码passwordEncoder
    //在spring Security 5.0+ 新增了很多加密方式
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //这些数据正常应该从数据库中获取
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//        //配置用户权限
//        .withUser("admin").password(new BCryptPasswordEncoder().encode("Ad@123")).roles("admin","vip1","vip3")
//        .and()
//        .withUser("chvfily").password(new BCryptPasswordEncoder().encode("123123")).roles("admin");
//    }
}

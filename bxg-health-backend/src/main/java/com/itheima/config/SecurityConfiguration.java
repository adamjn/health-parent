/*
package com.itheima.config;

import com.itheima.controller.backend.security.SpringSecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

*/
/**
 * Security配置类
 *//*


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

*/
/**
     * http请求处理方法
     *
     * @param http
     * @throws Exception
     *//*


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()// 开启表单认证
                .loginPage("/backend/login.html")// 自定义登录页面
                .loginProcessingUrl("/login")// 登录处理Url
                .usernameParameter("username").passwordParameter("password")// 修改自定义表单name值.
                .defaultSuccessUrl("/backend/pages/main.html")// 登录成功后跳转路径
                .and()
                .authorizeRequests()
                .antMatchers("/backend/login.html").permitAll()
                .anyRequest().authenticated(); //所有请求都需要登录认证才能访问;
        // 关闭csrf防护
        http.csrf().disable();
        // 允许iframe加载页面
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/backend/css/**", "/backend/img/**",
                "/backend/js/**", "/backend/plugins/**", "/favicon.ico");
    }

    @Autowired
    private SpringSecurityUserService userService;
    @Bean
    protected PasswordEncoder myPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(myPasswordEncoder());
    }
}
*/

package com.techmatch.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techmatch.base.config.jwt.JwtRequestFilter;
import com.techmatch.base.config.user.TechMatchUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebBasicAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Override
    public void configure(WebSecurity web) throws Exception {
        //  全体に対するセキュリティ設定を行う
    	web.ignoring().antMatchers("/css/**","favicon.ico","/js/**","/img/**","/user/**","/**","/swagger-ui.html");
    }

    @Autowired
    JwtRequestFilter jwtRequestFilter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  URLごとに異なるセキュリティ設定を行う
    	http
    	     .authorizeRequests()
    	     .antMatchers("/api/auth/**","/","/api/master/tables","/api/user/exist","/api/user/getInfo/**","/api/requirement/getList","/swagger-ui.html")
    	         .permitAll()
    	     .anyRequest()
    	         .authenticated()
    	     .and()
    	         .sessionManagement()
    	         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	     .and()
    	         .csrf()
    	         .disable();

    	// フィルターとビフォーフィルター
    	http
    	      .addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    private TechMatchUserDetailsService techMatchUserDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  認証方法の実装の設定を行う
    	auth
    	.eraseCredentials(true)
    	.userDetailsService(techMatchUserDetailsService)
    	.passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
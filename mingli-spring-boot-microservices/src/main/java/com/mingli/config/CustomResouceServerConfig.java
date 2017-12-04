package com.mingli.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


/**
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc 
 *
 */

@Configuration
@EnableResourceServer
public class CustomResouceServerConfig extends ResourceServerConfigurerAdapter {

    Logger log = LoggerFactory.getLogger(CustomResouceServerConfig.class);

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http
			.antMatcher("/**")
			.authorizeRequests()
				.antMatchers("/", "/index.html","/login**", "/webjars/**","/test**","/default**")
				.permitAll()
			.anyRequest()
				.authenticated();
                
    }
	/*
    @Autowired
    private TokenStore tokenStore;
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .tokenStore(tokenStore)
//                .resourceId("resourceId")
        ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
            .and()
                .authorizeRequests()
                .antMatchers("/home")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/user")
                .access("#oauth2.hasScope('read')")
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        ;
    }
    */
}

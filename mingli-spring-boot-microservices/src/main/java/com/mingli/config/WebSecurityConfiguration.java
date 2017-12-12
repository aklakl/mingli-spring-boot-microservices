package com.mingli.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;



@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * @author: MingLi
	 * @Date: 12/11/2017
	 * @Description: if you want to except the swagger UI in spring security OAuth2 ,please using this configuration  
	 * @warning this is only for WebSecurity , we recommend using ResourceServerConfigurerAdapter to implement it. 
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**");//
    }

}




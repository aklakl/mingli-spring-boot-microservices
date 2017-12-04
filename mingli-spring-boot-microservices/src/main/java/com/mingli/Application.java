package com.mingli;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * refere:
 * https://spring.io/guides/gs/maven/
 * 
 * 
 * 
 * @author MingLi
 * @Date: 11/27/2017
 * @name   
 * @desc   
 *
 * 
 */

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableResourceServer
//@RestController
//@EnableAutoConfiguration
public class Application  {//extends WebSecurityConfigurerAdapter 

	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	 
	//@Override
	//unused because is conflict with the CustomResouceServerConfig bean
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
			.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**","/test**","/default**")
				.permitAll()
			.anyRequest()
				.authenticated();
	}
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.antMatcher("/**")
	    ... // existing code here
	    .and().logout().logoutSuccessUrl("/").permitAll();
	}
	*/
	
    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "This is Ming Li's microservices!";
    }

    
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
    
}

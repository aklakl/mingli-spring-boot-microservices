package com.example.demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@ServletComponentScan
public class DemoApplication {

	
	Logger log = LoggerFactory.getLogger(DemoApplication.class);
	 
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        System.out.println("*** corsConfigurer called");
        return new WebMvcConfigurerAdapter() {
            @Override public void addCorsMappings(CorsRegistry registry) {
                System.out.println("*** addCorsMappings called");
                registry.addMapping("/v2/api-docs");
            }
        };
    }
	
//	@Bean
//    @ConditionalOnProperty(name = "jhipster.cors.allowed-origins")
//    public CorsFilter corsFilter() {
//        log.debug("Registering CORS filter");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = jHipsterProperties.getCors();
//        source.registerCorsConfiguration("/api/**", config);
//        source.registerCorsConfiguration("/v2/api-docs", config);
//        source.registerCorsConfiguration("/oauth/**", config);
//        return new CorsFilter(source);
//    }
	
	//CORS   https://github.com/joshlong/bookmarks/blob/master/security/src/main/java/bookmarks/Application.java
    @Bean
    FilterRegistrationBean corsFilter(@Value("${tagit.origin:*}") String origin) {
        return new FilterRegistrationBean(new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                String method = request.getMethod();
                // this origin value could just as easily have come from a database
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
                response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                if ("OPTIONS".equals(method)) {
                    response.setStatus(HttpStatus.OK.value());
                } else {
                    chain.doFilter(req, res);
                }
            }

            public void init(FilterConfig filterConfig) {
            	System.out.println(" FilterRegistrationBean initinitinit");
            }

            public void destroy() {
            	System.out.println("FilterRegistrationBean destroydestroydestroy");
            }
        });
    }
	
}

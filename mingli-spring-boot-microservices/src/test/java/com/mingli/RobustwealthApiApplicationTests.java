package com.mingli;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import jdk.nashorn.internal.ir.annotations.Ignore;

//need to figure out how to make this test work. It's more about instantiating the objects successfully
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class RobustwealthApiApplicationTests {

	@Autowired
	RobustwealthApiApplication robustwealthApiApplication;
	@Autowired
	HttpSecurity httpSecurity;
	@Autowired
	ResourceServerConfiguration resourceServerConfig;
	

	@Before
	public void setUp() throws Exception {
		assertNotNull("RobustwealthApiApplication should not be null", robustwealthApiApplication);
		resourceServerConfig.configure(httpSecurity);
		
	}
	
	public void tearDown() {
		robustwealthApiApplication = null;
		resourceServerConfig = null;
	}
	
	@Test
	public void testForSetup() {
		assertTrue(true);
	}
	
	

}

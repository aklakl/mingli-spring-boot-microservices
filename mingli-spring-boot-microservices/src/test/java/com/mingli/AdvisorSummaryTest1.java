package com.mingli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
 

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static junit.framework.TestCase.assertEquals;



class AdvisorSummaryTest1 {

	
		
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);
    
//    @Test
//    public void wireMockExampleTest() throws IOException, JSONException {
//        String restposeJson = "{\"hello\":\"ok http\"}";
//        stubFor(get(urlEqualTo("/my/resource"))
//                .withHeader("Accept", equalTo("application/json"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withBody(restposeJson)));
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://localhost:8089/my/resource")
//                .addHeader("Accept", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();
//        assertTrue(response.isSuccessful());
//        assertEquals(response.code(), 200);
//        JSONAssert.assertEquals(restposeJson, response.body().string(), false);
//        verify(1, getRequestedFor(urlEqualTo("/my/resource"))
//                .withHeader("Accept", equalTo("application/json")));
//    }
    public void exampleTest() {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/xml")
                    .withBody("<response>Some content</response>")));
     
        Result result = myHttpServiceCallingObject.doSomething();
     
        assertTrue(result.wasSuccessFul());
     
        verify(postRequestedFor(urlMatching("/my/resource/[a-z0-9]+"))
                .withRequestBody(matching(".*<message>1234</message>.*"))
                .withHeader("Content-Type", notMatching("application/json")));
    }
	
	
	/*@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}*/

}

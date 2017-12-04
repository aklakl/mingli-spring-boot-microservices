package com.mingli;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.robustwealth.microservices.ads.Application;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static junit.framework.TestCase.assertEquals;

import java.io.IOException;

public class WireMockApplicationTest {
	@Rule
	public WireMockRule forecastIoService = new WireMockRule();

	private Application wireMockServer = new Application();

	@Before
	public void setUp() {
		forecastIoService.start();
	}

	@After
	public void tearDown() {
		forecastIoService.stop();
	}

	@Test
	public void servesWindSpeedBasedOnForecastIoResponse() throws IOException {
		forecastIoService.stubFor(get(urlEqualTo("/forecast/e67b0e3784104669340c3cb089412b67/51.507253,-0.127755"))
				.willReturn(aResponse().withBody("{\"currently\":{\"windSpeed\":12.34}}")));

		Content content = Request.Get("http://localhost:" + forecastIoService.port() + "/wind-speed").execute()
				.returnContent();

		assertEquals("12.34mph", content.toString());
	}

	@Test
	public void reportsErrorWhenForecastIoReturnsANonSuccessfulResponse() throws IOException {
		forecastIoService.stubFor(get(urlEqualTo("/forecast/e67b0e3784104669340c3cb089412b67/51.507253,-0.127755"))
				.willReturn(aResponse().withStatus(SC_INTERNAL_SERVER_ERROR)));

		HttpResponse httpResponse = Request.Get("http://localhost:" + forecastIoService.port() + "/wind-speed")
				.execute().returnResponse();

		assertEquals(503, httpResponse.getStatusLine().getStatusCode());
		assertEquals("ERROR", IOUtils.toString(httpResponse.getEntity().getContent()));
	}
}
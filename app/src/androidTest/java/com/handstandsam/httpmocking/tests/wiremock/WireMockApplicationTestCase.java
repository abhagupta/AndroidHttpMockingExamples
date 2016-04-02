package com.handstandsam.httpmocking.tests.wiremock;

import android.test.ApplicationTestCase;

import com.handstandsam.wiremockwebserver.AssetReaderUtil;
import com.handstandsam.wiremockwebserver.WireMockWebServerDispatcher;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.inject.WeatherviewApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.handstandsam.wiremockwebserver.WireMockWebServer.aResponse;
import static com.handstandsam.wiremockwebserver.WireMockWebServer.get;
import static com.handstandsam.wiremockwebserver.WireMockWebServer.urlMatching;

public class WireMockApplicationTestCase extends ApplicationTestCase<WeatherviewApplication> {

    Logger logger = LoggerFactory.getLogger(WireMockApplicationTestCase.class);

    public WireMockApplicationTestCase() {
        super(WeatherviewApplication.class);
    }

    WireMockWebServerDispatcher wireMockWebServerDispatcher = new WireMockWebServerDispatcher();

    MockWebServer mMockWebServer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMockWebServer = new MockWebServer();
        mMockWebServer.play(BuildConfig.PORT);
        mMockWebServer.setDispatcher(wireMockWebServerDispatcher);
        createApplication();
    }

    @Override
    protected void tearDown() throws Exception {
        mMockWebServer.shutdown();
        mMockWebServer = null;
        super.tearDown();
    }

    /**
     * Test WireMock, but just the Http Call.  Make sure the response matches the mock we want.
     */
    public void testWiremockPlusOkHttp() throws IOException {
        logger.debug("testWiremockPlusOkHttp");

        String uri = "/api/840dbdf2737a7ff9/conditions/q/CA/atlanta.json";

        String assetJson = AssetReaderUtil.assetAsString(getApplication(), "atlanta-conditions.json");
        wireMockWebServerDispatcher.stubFor(get(urlMatching(uri))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(assetJson)));

        String serviceEndpoint = "http://127.0.0.1:" + BuildConfig.PORT;
        logger.debug("WireMock Endpoint: " + serviceEndpoint);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(serviceEndpoint + uri)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        assertNotNull(response.body().string());
    }
}

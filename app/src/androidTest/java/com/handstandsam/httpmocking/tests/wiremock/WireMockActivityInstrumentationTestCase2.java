package com.handstandsam.httpmocking.tests.wiremock;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.handstandsam.wiremockwebserver.AssetReaderUtil;
import com.handstandsam.wiremockwebserver.RequestDefinition;
import com.handstandsam.wiremockwebserver.WireMockWebServerDispatcher;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.service.WeatherServiceManager;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.handstandsam.wiremockwebserver.WireMockWebServer.aResponse;
import static com.handstandsam.wiremockwebserver.WireMockWebServer.get;
import static com.handstandsam.wiremockwebserver.WireMockWebServer.urlMatching;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class WireMockActivityInstrumentationTestCase2 extends ActivityInstrumentationTestCase2<MainActivity> {

    Logger logger = LoggerFactory.getLogger(WireMockActivityInstrumentationTestCase2.class);

    private MainActivity activity;

    public WireMockActivityInstrumentationTestCase2() {
        super(MainActivity.class);
    }

    WireMockWebServerDispatcher wireMockWebServerDispatcher = new WireMockWebServerDispatcher();

    MockWebServer mMockWebServer;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mMockWebServer = new MockWebServer();
        mMockWebServer.play(BuildConfig.PORT);
        mMockWebServer.setDispatcher(wireMockWebServerDispatcher);
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        mMockWebServer.shutdown();
        mMockWebServer = null;
        super.tearDown();
    }

    /**
     * Test WireMock
     */
    @Test
    public void testWiremock() {
        String assetJson = AssetReaderUtil.assetAsString(activity, "atlanta-conditions.json");
        stubFor(get(urlMatching("/api/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(assetJson)));

        String serviceEndpoint = "http://127.0.0.1:" + BuildConfig.PORT;
        logger.debug("WireMock Endpoint: " + serviceEndpoint);
        activity.setWeatherServiceManager(new WeatherServiceManager(serviceEndpoint));

        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("atlanta"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

    private void stubFor(RequestDefinition requestDefinition) {
        wireMockWebServerDispatcher.stubFor(requestDefinition);
    }

}


package cc.ddsakura.okhttptest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    MockWebServer mMockWebServer;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class, true, false);

    @Before
    public void setup() throws Exception {
        mMockWebServer = new MockWebServer();
        // Start the mMockWebServer.
        mMockWebServer.start();
        HttpUrl baseUrl = mMockWebServer.url("/");
        MainActivity.uri = baseUrl.toString();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cc.ddsakura.okhttptest", appContext.getPackageName());
    }

    @Test
    public void testOKHttp() throws Exception {

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        mMockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("AAABBB!"));

        RecordedRequest request = mMockWebServer.takeRequest();
        assertEquals("/", request.getPath());

        onView(withId(R.id.descTv)).check(matches(withText("AAABBB!")));

    }

    @After
    public void tearDown() throws Exception {
        mMockWebServer.shutdown();
    }

}

package it.tiwiz.lumos;


import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This test checks the correctness of the TemporaryActivity
 */
public class TemporaryActivityTest extends ActivityInstrumentationTestCase2<TemporaryActivity>{
    TemporaryActivity mTemporaryActivity;
    TextView mTemporaryMessageTextView;
    ImageView mTemporaryLogoImage;
    Toolbar mToolbar;

    public TemporaryActivityTest() {
        super(TemporaryActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mTemporaryActivity = getActivity();
        mTemporaryMessageTextView = (TextView) mTemporaryActivity.findViewById(R.id.temporary_message);
        mTemporaryLogoImage = (ImageView) mTemporaryActivity.findViewById(R.id.imageView);
        mToolbar = (Toolbar) mTemporaryActivity.findViewById(R.id.toolbar);
    }

    public void testPreconditions() throws Exception {
        assertNotNull("mTemporaryActivity is null", mTemporaryActivity);
        assertNotNull("mTemporaryMessageTextView is null", mTemporaryMessageTextView);
        assertNotNull("mTemporaryLogoImage is null", mTemporaryLogoImage);
    }

    public void testCorrectTemporarySentence() throws Exception {
        final String expected = mTemporaryActivity.getString(R.string.temporary_warning_text);
        final String actual = mTemporaryMessageTextView.getText().toString();

        assertEquals(actual, expected);
    }

    public void testCorrectLogoPresence() throws Exception {
        final View decorView = mTemporaryActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mTemporaryLogoImage);

        final ViewGroup.LayoutParams layoutParams = mTemporaryLogoImage.getLayoutParams();

        assertNotNull("LayoutParams of ImageView is null", layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void testToolbar() throws Exception {
        final View decorView = mTemporaryActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(decorView, mToolbar);

        final String expected = mTemporaryActivity.getString(R.string.app_name);
        final String actual = mToolbar.getTitle().toString();

        assertEquals(actual, expected);
    }
}

package it.tiwiz.lumos.skeleton;

import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;

public class ScreenBaseActivity extends Activity {

    public void setScreenBrightness(int screenValue) {
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenValue);
    }

    public int getScreenBrightness() {
        int screenBrightness;
        try {
            screenBrightness = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            screenBrightness = 0;
        }

        if (screenBrightness > 255) {
            screenBrightness = 255;
        }

        return screenBrightness;
    }
}

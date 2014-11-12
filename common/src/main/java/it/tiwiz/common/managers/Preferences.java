package it.tiwiz.common.managers;

import android.content.Context;
import android.content.SharedPreferences;

import it.tiwiz.common.constants.Defaults;
import it.tiwiz.common.constants.Keys;

public class Preferences {

    private static final SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Keys.Preferences.KEY, Context.MODE_PRIVATE);
    }

    private static final SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    private static final void saveIntValueToPreferences(Context context, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * This method returns the maximum value of the luminosity of the Wear device
     */
    public static int getLumosValue(Context context) {
        return getPreferences(context).getInt(Keys.Preferences.LUMOS, Defaults.Preferences.LUMOS);
    }

    /**
     * This method saves the maximum luminosity value for the Wear device as the user specifies it
     */
    public static void saveLumosValue(Context context, int lumosValue) {
        saveIntValueToPreferences(context, Keys.Preferences.LUMOS, lumosValue);
    }

    /**
     * This method returns the minimum value of the luminosity of the Wear device
     */
    public static int getNoxValue(Context context) {
        return getPreferences(context).getInt(Keys.Preferences.NOX, Defaults.Preferences.NOX);
    }

    /**
     * This method saves the minimum luminosity value for the Wear device as the user specifies it
     */
    public static void saveNoxValue(Context context, int noxValue) {
        saveIntValueToPreferences(context, Keys.Preferences.NOX, noxValue);
    }

    /**
     * This method retrieves the offset value as the user decided it, so that the progress bar can be
     * moved at different pace.
     */
    public static int getBrightnessOffsetValue(Context context) {
        return getPreferences(context).getInt(Keys.Preferences.BRIGHTNESS_OFFSET, Defaults.Preferences.BRIGHTNESS_OFFSET);
    }

    /**
     * This method saves the offset that will move the progress bar at a different pace
     */
    public static void saveBrightnessOffsetValue(Context context, int brightnessOffsetValue) {
        saveIntValueToPreferences(context, Keys.Preferences.BRIGHTNESS_OFFSET, brightnessOffsetValue);
    }
}

package it.tiwiz.common.constants;

public class Keys {

    private static final String KEYS_TAG = Keys.class.getSimpleName();

    public static class Preferences {

        public static final String KEY = KEYS_TAG + "." + Preferences.class.getSimpleName();
        public static final String LUMOS = KEY + ".LUMOS";
        public static final String NOX = KEY + ".NOX";
        public static final String BRIGHTNESS_OFFSET = KEY + ".OFFSET";
    }

    public static class Messages {

        public static final String START_ACTIVITY = "/start";
        public static final String CLOSE_ACTIVITY = "/close";
        public static final String NEW_TEST_VALUE = START_ACTIVITY + "/value/test";
        public static final String LUMOS = "/lumos";
        public static final String NOX = "/nox";
        public static final String BRIGHTNESS_OFFSET = "/brightness_offset";
    }
}

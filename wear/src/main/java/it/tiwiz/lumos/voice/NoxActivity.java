package it.tiwiz.lumos.voice;

import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import it.tiwiz.common.constants.Defaults;
import it.tiwiz.common.managers.Preferences;
import it.tiwiz.lumos.R;
import it.tiwiz.lumos.skeleton.ScreenBaseActivity;
import it.tiwiz.lumos.skeleton.TimerWearActivity;

public class NoxActivity extends TimerWearActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getCaption() {
        return getString(R.string.nox_activity);
    }

    @Override
    protected int getBrightness() {
        return Preferences.getNoxValue(this);
    }
}

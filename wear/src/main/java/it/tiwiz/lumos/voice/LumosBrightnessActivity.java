package it.tiwiz.lumos.voice;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import it.tiwiz.common.managers.Preferences;
import it.tiwiz.lumos.R;
import it.tiwiz.lumos.skeleton.ScreenBaseActivity;

import static android.view.View.*;
import static it.tiwiz.common.constants.Defaults.Preferences.*;

public class LumosBrightnessActivity extends ScreenBaseActivity implements OnClickListener, OnLongClickListener{

    ImageButton mBtnIncreaseBrightness, mBtnDecreaseBrightness;
    ProgressBar mBarSquareProgress;
    final Context mContext = this;
    int OFFSET;
    int mScreenBrightness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lumos_brightness);
        mScreenBrightness = getScreenBrightness();

        OFFSET = Preferences.getBrightnessOffsetValue(this);

        mBtnIncreaseBrightness = (ImageButton) findViewById(R.id.btnIncreaseBrightness);
        mBtnIncreaseBrightness.setOnClickListener((OnClickListener) mContext);
        mBtnIncreaseBrightness.setOnLongClickListener((OnLongClickListener) mContext);
        mBtnDecreaseBrightness = (ImageButton) findViewById(R.id.btnDecreaseBrightness);
        mBtnDecreaseBrightness.setOnClickListener((OnClickListener) mContext);
        mBtnDecreaseBrightness.setOnLongClickListener((OnLongClickListener) mContext);

        mBarSquareProgress = (ProgressBar) findViewById(R.id.brightnessBar);

        updateBrightnessUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBrightnessUI();
    }

    @Override
    public void onClick(View clickedView) {
        if (clickedView.getId() == R.id.btnIncreaseBrightness) {
            increaseBrightness();
        } else if (clickedView.getId() == R.id.btnDecreaseBrightness) {
            decreaseBrightness();
        }
    }

    public void updateBrightness() {
        setScreenBrightness(mScreenBrightness);
        updateBrightnessUI();
    }

    /**
     * The null check is mandatory in order to avoid
     * the {@link java.lang.NullPointerException} when
     * the app is resumed.
     *
     * We call this method because we want to make the app
     * react on screen luminosity even if the value has not been
     * changed inside the app itself
     */
    public void updateBrightnessUI() {
        if(mBarSquareProgress != null) {
            mBarSquareProgress.setProgress(mScreenBrightness);
        }
    }

    private void increaseBrightness() {
        if (mScreenBrightness < (MAX_BRIGHTNESS - OFFSET)){
            mScreenBrightness += OFFSET;
        } else {
            mScreenBrightness = MAX_BRIGHTNESS;
        }

        updateBrightness();
    }

    private void decreaseBrightness() {
        if (mScreenBrightness > OFFSET) {
            mScreenBrightness -= OFFSET;
        } else {
            mScreenBrightness = MIN_BRIGHTNESS;
        }

        updateBrightness();
    }

    @Override
    public boolean onLongClick(View clickedView) {
        if (clickedView.getId() == R.id.btnIncreaseBrightness) {
            mScreenBrightness = MAX_BRIGHTNESS;
        } else if (clickedView.getId() == R.id.btnDecreaseBrightness) {
            mScreenBrightness = MIN_BRIGHTNESS;
        }

        updateBrightness();
        return true;
    }
}

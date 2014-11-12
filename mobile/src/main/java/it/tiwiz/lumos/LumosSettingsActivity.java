package it.tiwiz.lumos;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import it.tiwiz.common.constants.Errors;
import it.tiwiz.common.constants.Keys;
import it.tiwiz.common.managers.PayloadConverter;
import it.tiwiz.common.managers.Preferences;

import static android.widget.SeekBar.*;
import static android.widget.ViewSwitcher.*;
import static com.google.android.gms.common.api.GoogleApiClient.*;
import static com.google.android.gms.wearable.MessageApi.*;


public class LumosSettingsActivity extends ActionBarActivity implements OnSeekBarChangeListener,
        ConnectionCallbacks, OnConnectionFailedListener,
        ResultCallback<MessageApi.SendMessageResult>, ViewFactory, OnClickListener {

    private static final String isHelpBeingShownKey = "is_help_being_shown";

    private SeekBar mLumosSeekBar, mNoxSeekbar, mBrightnessOffsetSeekbar;
    private int mLumosValue, mNoxValue, mBrightnessOffsetValue;
    private TextSwitcher mLumosTextValue, mNoxTextValue, mBrightnessOffsetTextValue;
    private GoogleApiClient mGoogleApiClient;
    private Node mConnectedNode;
    private View mContentLayout, mHelpLayout;
    private Button mHelpButton;

    private Animation mHelpAnimateIn, mHelpAnimateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lumos_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        init();
        updateViews(savedInstanceState);
    }

    private void init() {
        findElementsFromTheContentView();
        initTextSwitchers();
        loadSavedData();
        initSeekbars();
        initEventListeners();
        initGoogleApiClient();
        initHelpViewAnimationsAndCallbacks();
    }

    private void updateViews(Bundle savedInstanceState) {
        boolean wasHelpBeingShown;

        if (savedInstanceState != null) {
            wasHelpBeingShown = savedInstanceState.getBoolean(isHelpBeingShownKey, false);
            if (wasHelpBeingShown) {
                mHelpLayout.setVisibility(VISIBLE);
            }
        }
    }

    private void findElementsFromTheContentView() {

        mLumosSeekBar = (SeekBar) findViewById(R.id.lumosSeekBar);
        mNoxSeekbar = (SeekBar) findViewById(R.id.noxSeekBar);
        mBrightnessOffsetSeekbar = (SeekBar) findViewById(R.id.brightnessOffsetSeekBar);

        mLumosTextValue = (TextSwitcher) findViewById(R.id.lumosTextValue);
        mNoxTextValue = (TextSwitcher) findViewById(R.id.noxTextValue);
        mBrightnessOffsetTextValue = (TextSwitcher) findViewById(R.id.brightnessOffsetTextValue);

        mContentLayout = findViewById(R.id.contentLayout);
        mHelpLayout = findViewById(R.id.helpLayout);

        mHelpButton = (Button) findViewById(R.id.helpButton);

    }

    private void initTextSwitchers() {

        final Animation animIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        final Animation animOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        mLumosTextValue.setInAnimation(animIn);
        mLumosTextValue.setOutAnimation(animOut);
        mLumosTextValue.setFactory(this);

        mNoxTextValue.setInAnimation(animIn);
        mNoxTextValue.setOutAnimation(animOut);
        mNoxTextValue.setFactory(this);

        mBrightnessOffsetTextValue.setInAnimation(animIn);
        mBrightnessOffsetTextValue.setOutAnimation(animOut);
        mBrightnessOffsetTextValue.setFactory(this);

    }

    private void loadSavedData() {

        mLumosValue = Preferences.getLumosValue(this);
        mNoxValue = Preferences.getNoxValue(this);
        mBrightnessOffsetValue = Preferences.getBrightnessOffsetValue(this);

        mLumosTextValue.setText(String.valueOf(mLumosValue));
        mNoxTextValue.setText(String.valueOf(mNoxValue));
        mBrightnessOffsetTextValue.setText(String.valueOf(mBrightnessOffsetValue));

    }

    private void initSeekbars() {

        mLumosSeekBar.setProgress(mLumosValue);
        mNoxSeekbar.setProgress(mNoxValue);
        mBrightnessOffsetSeekbar.setProgress(mBrightnessOffsetValue);

    }



    private void initEventListeners() {

        mLumosSeekBar.setOnSeekBarChangeListener(this);
        mNoxSeekbar.setOnSeekBarChangeListener(this);
        mBrightnessOffsetSeekbar.setOnSeekBarChangeListener(this);

        mHelpButton.setOnClickListener(this);
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void initHelpViewAnimationsAndCallbacks() {
        mHelpAnimateIn = AnimationUtils.loadAnimation(this, R.anim.anim_in);
        mHelpAnimateIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                findViewById(R.id.helpLayout).setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mHelpAnimateOut = AnimationUtils.loadAnimation(this, R.anim.anim_out);
        mHelpAnimateOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.helpLayout).setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final boolean isHelpBeingShown = mHelpLayout.getVisibility() == VISIBLE;
        outState.putBoolean(isHelpBeingShownKey, isHelpBeingShown);
    }

    private void openWearActivity() {

        sendGenericMessageToWear(Keys.Messages.START_ACTIVITY, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNewData();
        synchronizeWearData();
    }

    private void saveNewData() {
        Preferences.saveLumosValue(this, mLumosValue);
        Preferences.saveNoxValue(this, mNoxValue);
        Preferences.saveBrightnessOffsetValue(this, mBrightnessOffsetValue);
    }

    private void synchronizeWearData() {
        sendCloseActivityMessage();
        sendLumosValueForSync();
        sendNoxValueForSync();
        sendBrightnessOffsetValueForSync();
    }

    private void sendCloseActivityMessage() {
        sendGenericMessageToWear(Keys.Messages.CLOSE_ACTIVITY, null);
    }

    private void sendLumosValueForSync() {
        byte[] lumosValue = PayloadConverter.intToByteArray(mLumosSeekBar.getProgress());
        sendGenericMessageToWear(Keys.Messages.LUMOS, lumosValue);
    }

    private void sendNoxValueForSync() {
        byte[] noxValue = PayloadConverter.intToByteArray(mNoxSeekbar.getProgress());
        sendGenericMessageToWear(Keys.Messages.NOX, noxValue);
    }

    private void sendBrightnessOffsetValueForSync() {
        byte[] offsetValue = PayloadConverter.intToByteArray(mBrightnessOffsetSeekbar.getProgress());
        sendGenericMessageToWear(Keys.Messages.BRIGHTNESS_OFFSET, offsetValue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onBackPressed() {
        if (mHelpLayout.getVisibility() == VISIBLE) {
            hideView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (seekBar.equals(mLumosSeekBar)) {
                mLumosTextValue.setText(String.valueOf(progress));
            } else if (seekBar.equals(mNoxSeekbar)) {
                mNoxTextValue.setText(String.valueOf(progress));
            } else if (seekBar.equals(mBrightnessOffsetSeekbar)) {
                mBrightnessOffsetTextValue.setText(String.valueOf(progress));
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /* do nothing */
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.equals(mLumosSeekBar)) {
            updateLumosValueFromUserInput(mLumosSeekBar.getProgress());
        } else if (seekBar.equals(mNoxSeekbar)) {
            updateNoxValueFromUserInput(mNoxSeekbar.getProgress());
        }
    }

    private void updateLumosValueFromUserInput(int newLumosValue) {
        mLumosValue = newLumosValue;
        sendNewTestLuminosityMessageToWear(newLumosValue);
    }

    private void updateNoxValueFromUserInput(int newNoxValue) {
        mNoxValue = newNoxValue;
        sendNewTestLuminosityMessageToWear(newNoxValue);
    }

    private void sendNewTestLuminosityMessageToWear(int newLuminosityValue) {
        byte[] payload = PayloadConverter.intToByteArray(newLuminosityValue);
        sendGenericMessageToWear(Keys.Messages.NEW_TEST_VALUE, payload);
    }

    private void sendGenericMessageToWear(String path, byte[] payload) {
        if (isEverythingSetForMessageSending()) {
            /* TODO Remove when layout are done
            Wearable.MessageApi.sendMessage(mGoogleApiClient, mConnectedNode.getId(),
                    path, payload).setResultCallback(this);
                    */
        }
    }

    private boolean isEverythingSetForMessageSending() {
        //Oh, I love DeMorgan
        return !(mConnectedNode == null || mGoogleApiClient == null || !mGoogleApiClient.isConnected());
    }

    @Override
    public void onConnected(Bundle bundle) {
        resolveConnectedNode();
    }

    private void resolveConnectedNode() {
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult nodes) {
                for (Node node : nodes.getNodes()) {
                    mConnectedNode = node;
                }
                openWearActivity();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        /* do nothing */
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        onError(Errors.CONNECTION_FAILED);
    }

    @Override
    public void onResult(SendMessageResult sendMessageResult) {
        if (!sendMessageResult.getStatus().isSuccess()) {
            onError(Errors.FAILED_SENDING_MESSAGE);
        }
    }

    private void onError(int errorCode) {

    }

    @Override
    public View makeView() {
        TextView tmpTextView = new TextView(this);
        //tmpTextView.setTextAppearance(this, R.style.AppTheme_TextView);
        tmpTextView.setGravity(Gravity.CENTER);
        tmpTextView.setTypeface(Typeface.DEFAULT);

        return tmpTextView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.helpButton) {
            animateHelpView();
        }
    }

    private void animateHelpView() {
        if (mHelpLayout.getVisibility() == VISIBLE) {
            hideView();
        } else {
            showHelpView();
        }
    }

    private void showHelpView() {
        mHelpLayout.startAnimation(mHelpAnimateIn);
    }

    private void hideView() {
        mHelpLayout.startAnimation(mHelpAnimateOut);
    }
}

package it.tiwiz.lumos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import it.tiwiz.lumos.skeleton.Intents;
import it.tiwiz.lumos.skeleton.ScreenBaseActivity;

public class LumosWearTestActivity extends ScreenBaseActivity {

    private final DataReceiver mDataReceiver = new DataReceiver();
    private final IntentFilter mNewTestValueFilter = new IntentFilter(Intents.NEW_TEST_VALUE);
    private final IntentFilter mCloseActivityFilter = new IntentFilter(Intents.CLOSE_TEST_ACTIVITY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lumos_settings);

        LocalBroadcastManager.getInstance(this).registerReceiver(mDataReceiver, mNewTestValueFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(mDataReceiver, mCloseActivityFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDataReceiver);
    }

    private final class DataReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intents.NEW_TEST_VALUE) && intent.hasExtra(Intents.NEW_TEST_VALUE_EXTRA)) {
                setScreenBrightness(intent.getIntExtra(Intents.NEW_TEST_VALUE_EXTRA, 100));
            } else if (intent.getAction().equals(Intents.CLOSE_TEST_ACTIVITY)) {
                finish();
            }
        }
    }
}

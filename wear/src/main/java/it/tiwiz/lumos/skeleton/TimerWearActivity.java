package it.tiwiz.lumos.skeleton;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.support.wearable.view.WatchViewStub;
import android.view.View;

import it.tiwiz.lumos.R;

public abstract class TimerWearActivity extends ScreenBaseActivity implements DelayedConfirmationView.DelayedConfirmationListener{

    private DelayedConfirmationView timerButton;
    final DelayedConfirmationView.DelayedConfirmationListener mListener = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                timerButton = (DelayedConfirmationView) stub.findViewById(R.id.delayed_confirm);
                timerButton.setTotalTimeMs(30000);
                timerButton.start();
                timerButton.setListener(mListener);
            }
        });
    }

    protected abstract String getCaption();

    protected abstract int getBrightness();

    @Override
    public void onTimerFinished(View view) {
        //user did not cancel the action
        /*
        Intent acceptedIntent = getConfirmationActivityIntent(ConfirmationActivity.SUCCESS_ANIMATION);
        setScreenBrightness(getBrightness());
        startActivity(acceptedIntent);
        ((DelayedConfirmationView) view).setListener(null);
        finish();
        */
    }

    @Override
    public void onTimerSelected(View view) {
        //user canceled the action
        Intent refusedIntent = getConfirmationActivityIntent(ConfirmationActivity.FAILURE_ANIMATION);
        startActivity(refusedIntent);
        ((DelayedConfirmationView) view).setListener(null);
        finish();
    }

    protected Intent getConfirmationActivityIntent(int animation) {
        Intent confirmationActivityIntent = new Intent(this, ConfirmationActivity.class);
        confirmationActivityIntent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, animation);
        return confirmationActivityIntent;
    }
}

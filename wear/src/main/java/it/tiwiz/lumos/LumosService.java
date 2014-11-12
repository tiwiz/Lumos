package it.tiwiz.lumos;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import it.tiwiz.common.constants.Keys;
import it.tiwiz.common.managers.PayloadConverter;
import it.tiwiz.common.managers.Preferences;
import it.tiwiz.lumos.skeleton.Intents;

public class LumosService extends WearableListenerService{

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        final String messagePath = messageEvent.getPath();

        if (messagePath.equals(Keys.Messages.START_ACTIVITY)) {
            startTestActivity();
        } else if (messagePath.equals(Keys.Messages.NEW_TEST_VALUE)) {
            sendBrightnessValueForTest(messageEvent.getData());
        } else if (messagePath.equals(Keys.Messages.CLOSE_ACTIVITY)) {
            closeActivity();
        } else if (messagePath.equals(Keys.Messages.LUMOS)) {
            saveLumosData(messageEvent.getData());
        } else if (messagePath.equals(Keys.Messages.NOX)) {
            saveNoxData(messageEvent.getData());
        } else if (messagePath.equals(Keys.Messages.BRIGHTNESS_OFFSET)) {
            saveBrightnessOffsetData(messageEvent.getData());
        }
    }

    private void startTestActivity() {
        Intent startIntent = new Intent(this, LumosWearTestActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
    }

    private void sendBrightnessValueForTest(byte[] payload) {
        try {
            int convertedValue = PayloadConverter.byteArrayToInt(payload);
            Intent changeBrightness = new Intent(Intents.NEW_TEST_VALUE);
            changeBrightness.putExtra(Intents.NEW_TEST_VALUE_EXTRA, convertedValue);
            LocalBroadcastManager.getInstance(this).sendBroadcast(changeBrightness);
        }catch (Exception e) {
            //TODO Add report
            Log.d("DEBUG", e.getMessage());
        }
    }

    private void closeActivity() {
        Intent closeActivity = new Intent(Intents.CLOSE_TEST_ACTIVITY);
        LocalBroadcastManager.getInstance(this).sendBroadcast(closeActivity);
    }

    private void saveLumosData(byte[] data) {
        int lumosValue = PayloadConverter.byteArrayToInt(data);
        Preferences.saveLumosValue(this, lumosValue);
    }

    private void saveNoxData(byte[] data) {
        int noxValue = PayloadConverter.byteArrayToInt(data);
        Preferences.saveNoxValue(this, noxValue);
    }

    private void saveBrightnessOffsetData(byte[] data) {
        int brightnessOffsetValue = PayloadConverter.byteArrayToInt(data);
        Preferences.saveBrightnessOffsetValue(this, brightnessOffsetValue);
    }
}

package com.example.tomtaylor.fitness_app_01;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SensorService extends IntentService {

    public ActivityDataStore mDbHelper;

    public SensorService() {
        super("SensorService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            String currentActivity = handleDetectedActivities(result.getProbableActivities());

            ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
            Bundle bundle = new Bundle();
            bundle.putString("currentActivity", currentActivity);
            resultReceiver.send(18, bundle);
        }
    }

    private String handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for (DetectedActivity activity : probableActivities) {
            switch(activity.getType()) {
                case DetectedActivity.ON_FOOT: {
                    if(activity.getConfidence() > 75) {
                        return "ON_FOOT";
                    }
                    break;
                }
                case DetectedActivity.RUNNING: {
                    if(activity.getConfidence() > 75) {
                        return "RUNNING";
                    }
                    break;
                }
                case DetectedActivity.WALKING: {
                    if(activity.getConfidence() > 75) {
                        return "WALKING";
                    }
                    break;
                }
                case DetectedActivity.STILL: {
                    if(activity.getConfidence() > 75) {
                        return "STILL";
                    }
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    if(activity.getConfidence() > 75) {
                        return "UNKNOWN";
                    }
                    break;
                }
            }
        }
        return null;
    }
}

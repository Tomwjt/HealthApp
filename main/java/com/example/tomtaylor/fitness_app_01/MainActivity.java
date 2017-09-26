package com.example.tomtaylor.fitness_app_01;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;
    private Handler handler = new Handler();
    private ActivityOperations dbHandler;

    TextView activityTextView;
    EditText activityNameEditText;
    EditText amountOfTimeEditText;
    EditText dayOfActivityEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        //Declare Input Views
        activityNameEditText = (EditText) findViewById(R.id.activityNameEditText);
        amountOfTimeEditText = (EditText) findViewById(R.id.amountOfTimeEditText);
        dayOfActivityEditText = (EditText) findViewById(R.id.dayOfActivityEditText);

        //Declare Output Views
        activityTextView = (TextView) findViewById(R.id.activityOutput);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ResultReceiver myResultReceiver = new MyResultReceiver(null);
        Intent intent = new Intent(this, SensorService.class);
        intent.putExtra("receiver", myResultReceiver);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient, 3000, pendingIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void deleteAllActivities(View view) {

        ActivityDataStore mDbHelper = new ActivityDataStore(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        db.delete(ActivityDatabaseDDL.ActivityTable.TABLE_NAME, null, null);

    }

    public void createActivity(View view) {

        FitnessActivity activity = new FitnessActivity();

        activity.setName(activityNameEditText.getText().toString());
        activity.setAmountOfTime(amountOfTimeEditText.getText().toString());
        activity.setDay(dayOfActivityEditText.getText().toString());

        dbHandler = new ActivityOperations(getApplicationContext());
        dbHandler.open();

        FitnessActivity returnedActivity = dbHandler.addActivity(activity);

        Toast successMessage = Toast.makeText(getApplicationContext(),
                "New activity started\n" + "Name: " +returnedActivity.getName() +"\n"
                +"Amount of Time: "+returnedActivity.getAmountOfTime()+"\n"
                +"Day of Activity: "+returnedActivity.getDay(),
                Toast.LENGTH_LONG);
        successMessage.show();

        dbHandler.close();


    }

    private class MyResultReceiver extends ResultReceiver {

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if(resultCode == 18 && resultData != null) {
                final String currentActivity = resultData.getString("currentActivity");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        activityTextView.setText(currentActivity);
                    }
                });
            }
        }
    }
}

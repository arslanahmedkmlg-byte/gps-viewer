package com.arslan.gpsviewer;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();

        ((TextView) findViewById(R.id.tvDId)).setText("ID: " + b.getInt("id"));
        ((TextView) findViewById(R.id.tvDLat)).setText("Latitude: " + b.getDouble("latitude"));
        ((TextView) findViewById(R.id.tvDLng)).setText("Longitude: " + b.getDouble("longitude"));
        ((TextView) findViewById(R.id.tvDAlt)).setText("Altitude: " + b.getDouble("altitude") + " m");
        ((TextView) findViewById(R.id.tvDAcc)).setText("Accuracy: " + b.getDouble("accuracy") + " m");
        ((TextView) findViewById(R.id.tvDTs)).setText("Timestamp: " + b.getString("timestamp"));
        ((TextView) findViewById(R.id.tvDRec)).setText("Received: " + b.getString("received"));
        ((TextView) findViewById(R.id.tvDId)).setText("Device: " + b.getString("device_id"));
    }
}

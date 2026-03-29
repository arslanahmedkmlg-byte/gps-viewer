package com.arslan.gpsviewer;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "http://144.24.113.211:5000/logs";
    private static final int REFRESH_INTERVAL = 5000;

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private TextView tvStatus, tvCount;
    private Handler handler = new Handler();
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        tvStatus = findViewById(R.id.tvStatus);
        tvCount = findViewById(R.id.tvCount);

        adapter = new LogAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchLogs();
    }

    private void fetchLogs() {
        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(API_URL).build();
                Response response = client.newCall(request).execute();
                String body = response.body().string();

                JSONArray array = new JSONArray(body);
                List<LogEntry> logs = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    logs.add(new LogEntry(
                        obj.getInt("id"),
                        obj.optString("device_id", "unknown"),
                        obj.getDouble("latitude"),
                        obj.getDouble("longitude"),
                        obj.isNull("altitude") ? 0.0 : obj.getDouble("altitude"),
			obj.isNull("accuracy") ? 0.0 : obj.getDouble("accuracy"),
                        obj.optString("timestamp", ""),
                        obj.optString("received", "")
                    ));
                }

                runOnUiThread(() -> {
                    adapter.updateLogs(logs);
                    tvStatus.setText("Last updated: " + new java.util.Date().toString());
                    tvCount.setText(logs.size() + " logs");
                });

            } catch (Exception e) {
                runOnUiThread(() -> tvStatus.setText("Error: " + e.getMessage()));
            }

            handler.postDelayed(this::fetchLogs, REFRESH_INTERVAL);
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

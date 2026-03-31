package com.arslan.gpsviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<LogEntry> logs;
    private Context context;

    public LogAdapter(Context context, List<LogEntry> logs) {
        this.context = context;
        this.logs = logs;
    }

    public void updateLogs(List<LogEntry> newLogs) {
        this.logs = newLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogEntry log = logs.get(position);
        holder.tvId.setText("#" + log.id);
        holder.tvDevice.setText(log.device_id);
        holder.tvTime.setText(formatLocalTime(log.timestamp));
        holder.tvLatLng.setText(String.format("%.6f, %.6f", log.latitude, log.longitude));
        holder.tvAccuracy.setText(String.format("Accuracy: %.1fm  Altitude: %.1fm", log.accuracy, log.altitude));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", log.id);
            intent.putExtra("device_id", log.device_id);
            intent.putExtra("latitude", log.latitude);
            intent.putExtra("longitude", log.longitude);
            intent.putExtra("altitude", log.altitude);
            intent.putExtra("accuracy", log.accuracy);
            intent.putExtra("timestamp", log.timestamp);
            intent.putExtra("received", log.received);
            context.startActivity(intent);
        });
        holder.btnMap.setOnClickListener(v -> {
            Uri uri = Uri.parse("geo:" + log.latitude + "," + log.longitude + "?q=" + log.latitude + "," + log.longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        });
    }

    private String formatLocalTime(String utcTimestamp) {
        if (utcTimestamp == null || utcTimestamp.isEmpty()) return "";
        try {
            SimpleDateFormat utcFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            utcFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = utcFmt.parse(utcTimestamp);
            SimpleDateFormat localFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            localFmt.setTimeZone(TimeZone.getDefault());
            return localFmt.format(date);
        } catch (Exception e) {
            return utcTimestamp.length() >= 16 ? utcTimestamp.substring(0, 16) : utcTimestamp;
        }
    }

    @Override
        return logs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvDevice, tvTime, tvLatLng, tvAccuracy;
        ImageButton btnMap;

        ViewHolder(View view) {
            super(view);
            tvId = view.findViewById(R.id.tvId);
            tvDevice = view.findViewById(R.id.tvDevice);
            tvTime = view.findViewById(R.id.tvTime);
            tvLatLng = view.findViewById(R.id.tvLatLng);
            tvAccuracy = view.findViewById(R.id.tvAccuracy);
            btnMap = view.findViewById(R.id.btnMap);
        }
    }
}

package com.arslan.gpsviewer;

public class LogEntry {
    public int id;
    public String device_id;
    public double latitude;
    public double longitude;
    public double altitude;
    public double accuracy;
    public String timestamp;
    public String received;

    public LogEntry(int id, String device_id, double latitude, double longitude,
                    double altitude, double accuracy, String timestamp, String received) {
        this.id = id;
        this.device_id = device_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
        this.received = received;
    }
}

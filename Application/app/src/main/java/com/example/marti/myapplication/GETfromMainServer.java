package com.example.marti.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GETfromMainServer extends Service {
    public GETfromMainServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

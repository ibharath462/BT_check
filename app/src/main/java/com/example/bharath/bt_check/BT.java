package com.example.bharath.bt_check;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BT extends Service {
    public BT() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

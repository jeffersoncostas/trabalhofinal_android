package com.example.jeffe.trabalho_final.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public IBinder  onBind(Intent intent) {
        Toast.makeText(this, "Precisamos da sua permissão de localização!", Toast.LENGTH_SHORT).show();
        return null;
    }
}

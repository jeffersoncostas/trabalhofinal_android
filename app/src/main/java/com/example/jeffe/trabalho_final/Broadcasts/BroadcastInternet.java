package com.example.jeffe.trabalho_final.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.jeffe.trabalho_final.R;
import com.muddzdev.styleabletoast.StyleableToast;

public class BroadcastInternet extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(isOnline(context)){
            StyleableToast.makeText(context, "Você está online", Toast.LENGTH_LONG, R.style.myToastRight).show();
        }
        else{
            StyleableToast.makeText(context, "Você está offline, algumas funcionalidades não estarão disponíveis", Toast.LENGTH_LONG, R.style.myToastError).show();
        }
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null

        return (netInfo != null && netInfo.isConnected());
    }
}

package com.example.jeffe.trabalho_final;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeffe.trabalho_final.Requests.FirebaseRequests;
import com.example.jeffe.trabalho_final.Services.LocationService;
import com.example.jeffe.trabalho_final.Toast.MyToast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;
import static java.util.logging.Logger.global;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    @InjectView(R.id.locIcon)
    ImageView locIcon;
    @InjectView(R.id.input_location)
    TextView _inputLocation;

    private SignupActivity mContext;
    public boolean permis;
    private LocationRequest mLocationRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        locIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permis = true;
                } else {
                    permis = false;
                    StyleableToast.makeText(getBaseContext(), "Precisamos da sua permissão de localização!", Toast.LENGTH_LONG, R.style.myToastError).show();
                }
                break;
        }
    }

    public boolean checkPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,  Manifest.permission.ACCESS_FINE_LOCATION},
                2);

        String requiredPermission = "android.permission.ACCESS_COARSE_LOCATION";
        String requiredPermission2 = "android.permission.ACCESS_FINE_LOCATION";
        int checkVal = getBaseContext().checkCallingOrSelfPermission(requiredPermission);
        int checkVal2 = getBaseContext().checkCallingOrSelfPermission(requiredPermission2);

        Log.d("aa", "bbb:" + permis);

        if (checkVal == PackageManager.PERMISSION_GRANTED && checkVal2 == PackageManager.PERMISSION_GRANTED){
            permis = true;
            return true;
        } else  {
            return false;
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        if (checkPermissions()) {

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gps_enabled && !network_enabled) {
                StyleableToast.makeText(getBaseContext(), "Localização não está ativada", Toast.LENGTH_LONG, R.style.myToastError).show();
            }

            mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();

            SettingsClient settingsClient = LocationServices.getSettingsClient(this);
            settingsClient.checkLocationSettings(locationSettingsRequest);

            getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    try {
                        onLocationChanged(locationResult.getLastLocation());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, Looper.myLooper());
        } else {
            StyleableToast.makeText(getBaseContext(), "Você não concedeu as permissões necessárias", Toast.LENGTH_LONG, R.style.myToastError).show();
        }
    }

    public void onLocationChanged(Location location) throws IOException {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        String address = addresses.get(0).getAddressLine(0);
        _inputLocation.setText(address);
    }

    public void signup() {
        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando conta...");
        progressDialog.show();

        if (!validate()) {
            onSignupFailed(progressDialog);
        } else {
            final String name = _nameText.getText().toString();
            final String email = _emailText.getText().toString();
            final String password = _passwordText.getText().toString();
            final String location = _inputLocation.getText().toString();
            FirebaseRequests.GetInstance().CreateAccount(name, email, password, location, this, progressDialog);
        }
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        StyleableToast.makeText(getBaseContext(), "Conta criada com sucesso!", Toast.LENGTH_LONG, R.style.myToastRight).show();
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
        finish();
    }

    public void onSignupFailed(ProgressDialog progressDialog) {
        StyleableToast.makeText(getBaseContext(), "Houve um erro na criação da conta", Toast.LENGTH_LONG, R.style.myToastError).show();
        _signupButton.setEnabled(true);
        progressDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String loc = _inputLocation.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Coloque, no mínimo, 3 caracteres");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Coloque um endereço de e-mail válido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Digite entre 4 e 10 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (loc.isEmpty()) {
            _inputLocation.setError("Sua localização é necessária");
            valid = false;
        } else {
            _inputLocation.setError(null);
        }
        return valid;
    }
}
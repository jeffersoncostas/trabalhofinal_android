package com.example.jeffe.trabalho_final;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.MyBuilds;
import com.example.jeffe.trabalho_final.Requests.FirebaseRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {

    public TextView numeroBuilds;
    public TextView userName;
    public TextView userAdress;
    public TextView userDesc;
    public TextView userFriendList;
    public TextView userEmail;
    private FirebaseAuth auth;
    public ImageView userPic;
    public Context mContext;
    private Uri picUri;
    public boolean isInitializedFriend = false;
    public Usuario user;
    private static PerfilFragment uniqueInstance = null;
    private static PerfilFragment pF;
    public static MainActivity mainActivity;
    public boolean cameraActive;
    final int REQUEST_IMAGE_CAPTURE = 1;
    final int PIC_CROP = 2;
    Uri fileUri;
    String photoPath = "";

    public PerfilFragment() {
        isInitializedFriend = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraActive = true;
                } else {
                    cameraActive = false;
                    StyleableToast.makeText(mainActivity, "Permissão para usar a câmera foi negada.", Toast.LENGTH_LONG, R.style.myToastError).show();
                }
                return;
            }
            // add other cases for more permissions
        }
    }

    public void onStart() {
        super.onStart();
    }

    public static PerfilFragment newInstance(MainActivity mActivity) {
        if(uniqueInstance == null){
            mainActivity = mActivity;
            uniqueInstance = new PerfilFragment();
        }
        return uniqueInstance;
    }

    public void initializeWithFriend(Usuario usuario){
        usuario = user;
        isInitializedFriend = true;
        Log.d("funfo", "sim!!!");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("picUri", picUri);
    }

    @Override
    public void onViewStateRestored(@NonNull Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            picUri = savedInstanceState.getParcelable("picUri");
        }

    }

    public void dispatchTakePictureIntent() {
        ActivityCompat.requestPermissions(mainActivity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},1);

        String requiredPermission = "android.permission.CAMERA";
        String requiredPermission2 = "android.permission.READ_EXTERNAL_STORAGE";
        int checkVal = getContext().checkCallingOrSelfPermission(requiredPermission);
        int checkVal2 = getContext().checkCallingOrSelfPermission(requiredPermission2);

        if (checkVal == PackageManager.PERMISSION_GRANTED && checkVal2 == PackageManager.PERMISSION_GRANTED){
            cameraActive = true;
        }

        if (cameraActive == true) {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                if (takePhotoIntent.resolveActivity(mainActivity.getPackageManager()) != null) {

                    String fileName = System.currentTimeMillis() + ".jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);

                    fileUri = mainActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (ActivityNotFoundException anfe) {
                //display an error message
                String errorMessage = "Seu aparelho não tem suporte para a câmera";
                StyleableToast.makeText(mainActivity, errorMessage, Toast.LENGTH_LONG, R.style.myToastError).show();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private String getPath(Uri selectedImaeUri) {
        String[] projection = {
                MediaStore.Images.Media.DATA
        };

        Cursor cursor = mainActivity.managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }
        return selectedImaeUri.getPath();
    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(mainActivity.getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 72;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;



        BitmapFactory.Options o2 = new BitmapFactory.Options();

        o2.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(mainActivity.getContentResolver()
                .openInputStream(selectedImage), null, o2);

        return bitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        picUri = data.getData();

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                photoPath = getPath(fileUri);
                System.out.println("Image Path : " + photoPath);
                Bitmap b = null;

                try {
                    b = decodeUri(fileUri);
                    Log.d("porra", "aff:" + b);
                } catch (FileNotFoundException e) {
                    Log.d("porra", "aff2");
                    e.printStackTrace();
                }

                userPic.setImageBitmap(getCircularBitmap(b));
            }

        } else if(requestCode == PIC_CROP){ }
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

    String mCurrentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Carregando perfil...");
        progressDialog.show();

        FirebaseRequests.GetInstance().GetUserProfile(this, progressDialog);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPic = getView().findViewById(R.id.userPic);
        pF = this;
        final ImageView button = view.findViewById(R.id.userPic);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        auth = FirebaseAuth.getInstance();
        Button logoutBtn = (Button) getView().findViewById(R.id.btnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                FirebaseRequests.GetInstance().Logout(pF);
            }
        });
    }

    public void getProfileData(DataSnapshot dataSnapshot){
            String userNameValue = dataSnapshot.child("userName").getValue(String.class);
            String userLocValue = dataSnapshot.child("userLocalization").getValue(String.class);
            String userDescriptionValue = dataSnapshot.child("userDescription").getValue(String.class);
            String userPictureValue = dataSnapshot.child("userPicture").getValue(String.class);
            Long userFriendListValue = dataSnapshot.child("userFriendList").getChildrenCount();
            Long userBuildNValue = dataSnapshot.child("userBuilds").getChildrenCount();
            String userEmailValue = dataSnapshot.child("userEmail").getValue(String.class);

            userName = getView().findViewById(R.id.tv_name);
            userName.setText(userNameValue);

            userAdress = getView().findViewById(R.id.tv_address);
            userAdress.setText(userLocValue);

            userEmail = getView().findViewById(R.id.tv_email);
            userEmail.setText(userEmailValue);

            if (userDescriptionValue == null) {
                userDescriptionValue = "Esse usuário não possui descrição";
            }

            userDesc = getView().findViewById(R.id.tv_desc);
            userDesc.setText(userDescriptionValue);

            numeroBuilds = getView().findViewById(R.id.numberBuild);

            FirebaseRequests.GetInstance().getListaBuildsSize(this);

            if (userFriendListValue == 0) {
                userFriendListValue = Long.valueOf(0);
            }

            userFriendList = getView().findViewById(R.id.tv_friends);
            userFriendList.setText(userFriendListValue.toString());
        }
}

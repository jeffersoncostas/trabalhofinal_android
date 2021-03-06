package com.example.jeffe.trabalho_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeffe.trabalho_final.Amigos.AmigosAdapter;
import com.example.jeffe.trabalho_final.Amigos.AmigosFragment;
import com.example.jeffe.trabalho_final.Broadcasts.BroadcastInternet;
import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.BuildListsFragment;
import com.example.jeffe.trabalho_final.Build.EditBuildFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.Build.MyBuilds;
import com.example.jeffe.trabalho_final.Noticias.NoticiasFragment;
import com.example.jeffe.trabalho_final.Requests.FirebaseRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageView testeImg;

    private TextView perfilNumeroBuilds;

    private MainActivity mContext;
    private FirebaseAuth auth;
    private AmigosAdapter amigosAdapter;

    @InjectView(R.id.deleteIcon) ImageView delIcon;

    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment noticiasFragment = NoticiasFragment.newInstance();
                    openFragment(noticiasFragment);
                    return true;
                case R.id.navigation_dashboard:
                    Fragment buildFragment = BuildFragment.newInstance();
                    openFragment(buildFragment);
                    return true;
                case R.id.navigation_notifications:
                    Fragment perfilFragment = PerfilFragment.newInstance(mContext);
                    openFragment(perfilFragment);
                    return true;
            }
            return false;
        }
    };

    public void initializeBottomNavigation(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        BuildListsFragment.newInstance(mContext);
        EditBuildFragment.newInstance();
        AmigosFragment.newInstance(mContext);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Log.d("logado", "user: " + auth.getUid());
        } else {
            // TODO: usuário não logado, voltar pro login
        }

       initializeBottomNavigation();
        addBroadcastInternet();

    }


    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClickBuild(View view){
        Fragment buildFragment = BuildListsFragment.newInstance();
        openFragment(buildFragment);
    }

    public void onClickFriendsList(View view){
        Fragment amigosFragment = AmigosFragment.newInstance();
        openFragment(amigosFragment);

    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        ColorDrawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);

    }

    public void deleteFriend(DatabaseReference databaseReference) {
        Log.d("sss", "a: " + databaseReference);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    public void onClickFloatButton(View view){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_newbuild, null);

        popupView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.popup_animation));

        Button btnSalvar = popupView.findViewById(R.id.bttn_salvarbuild);
        final EditText inputPopup = popupView.findViewById(R.id.buildNameInput);
        final TextView titulo = popupView.findViewById(R.id.titlePopUpBuild);

        if(EditBuildFragment.newInstance().onEditBuild){
            titulo.setText("Renomeie esta build");
        }

        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

        applyDim(root, 0.5f);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.setElevation(5);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -90);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(EditBuildFragment.newInstance().onEditBuild){
                    BuildCompleta buildEditada = new BuildCompleta(EditBuildFragment.newInstance().buildList,inputPopup.getText().toString());
                    buildEditada.setBuildId(EditBuildFragment.newInstance().buildCompleta.getBuildId());
                    FirebaseRequests.GetInstance().EditBuild(buildEditada);
                }
                else{
                    List<Item> novaBuild = BuildFragment.newInstance().buildList;
                    MyBuilds.getInstance().enviarNovaBuild(novaBuild,inputPopup.getText().toString());
                }


                popupWindow.dismiss();
                clearDim(root);
                Fragment buildsList = BuildListsFragment.newInstance(mContext);
                openFragment(buildsList);

            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

    }


    public void addFriend(View view){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_addfriend, null);

        popupView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.popup_animation));

        Button btnAdd = popupView.findViewById(R.id.btn_addfriend);
        final EditText inputPopup = popupView.findViewById(R.id.friendEmailInput);
        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
        applyDim(root, 0.5f);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.setElevation(5);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -90);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Adicionando...");
                progressDialog.show();


                databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (snapshot.child("userEmail").getValue(String.class).equals(inputPopup.getText().toString())) {
                                //amigo existe

                                Log.d("entrou:", "no if");

                                String userId = auth.getUid();
                                DatabaseReference userRef = databaseUsers.child(userId);
                                DatabaseReference friendListRef = userRef.child("userFriendList");
                                friendListRef.push().setValue(snapshot.getKey());

                                StyleableToast.makeText(getBaseContext(), "Amigo adicionado.", Toast.LENGTH_LONG, R.style.myToastError).show();

                                progressDialog.dismiss();
                                popupWindow.dismiss();

                                break;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    public void addBroadcastInternet(){
        BroadcastInternet broadcastInternet = new BroadcastInternet();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        this.registerReceiver(broadcastInternet, filter);
    }
}

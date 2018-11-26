package com.example.jeffe.trabalho_final;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.MyBuilds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PerfilFragment extends Fragment {

    public TextView numeroBuilds;
    public TextView userName;
    public TextView userAdress;
    public TextView userDesc;
    public TextView userFriendList;
    public TextView userEmail;
    private FirebaseAuth auth;
    public Context mContext;


    FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid());

    public boolean isInitializedFriend = false;
    public Usuario user;

    private static PerfilFragment uniqueInstance = null;

    public static MainActivity mainActivity;

    public PerfilFragment() {
        isInitializedFriend = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onStart() {
        super.onStart();
    }

    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(Context context) {

        if(uniqueInstance == null){
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userNameValue = dataSnapshot.child("userName").getValue(String.class);
                String userLocValue = dataSnapshot.child("userLocalization").getValue(String.class);
                String userDescriptionValue = dataSnapshot.child("userDescription").getValue(String.class);
                String userPictureValue = dataSnapshot.child("userPicture").getValue(String.class);
                String userFriendListValue = dataSnapshot.child("userFriendList").getValue(String.class);
                String userBuildNValue = dataSnapshot.child("userBuilds").getValue(String.class);
                String userEmailValue = dataSnapshot.child("userEmail").getValue(String.class);

                userName = view.findViewById(R.id.tv_name);
                userName.setText(userNameValue);
                userAdress = view.findViewById(R.id.tv_address);
                userAdress.setText(userLocValue);
                userEmail = view.findViewById(R.id.tv_email);
                userEmail.setText(userEmailValue);

                Log.d("a", "sa: " + userDescriptionValue);

                if (userDescriptionValue == null) {
                    Log.d("a", "sa2: " + userDescriptionValue);
                    userDescriptionValue = "Esse usuário não possui descrição";
                }

                Log.d("aa", "u: " + userDescriptionValue);

                userDesc = view.findViewById(R.id.tv_desc);
                userDesc.setText(userDescriptionValue);

                numeroBuilds = view.findViewById(R.id.numberBuild);

                if (userBuildNValue == null) {
                    userBuildNValue = "0";
                }

                numeroBuilds.setText(userBuildNValue);

                if (userFriendListValue == null) {
                    userFriendListValue = "0";
                }

                userFriendList = view.findViewById(R.id.tv_friends);
                userFriendList.setText(userFriendListValue);
                //   numeroBuilds.setText(String.valueOf( MyBuilds.getInstance().getBuildsSize()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

        Button logoutBtn = (Button) getView().findViewById(R.id.btnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                FirebaseAuth firebaseAuth;
                FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null){
                            startActivity(new Intent(mContext, LoginActivity.class)); // TA TRAVANDO O APP
                        }
                        else {
                        }
                    }
                };

                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(authStateListener);

                firebaseAuth.signOut();
            }
        });

        }
}

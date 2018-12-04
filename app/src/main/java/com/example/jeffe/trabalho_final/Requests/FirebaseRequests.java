package com.example.jeffe.trabalho_final.Requests;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildListsFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.LoginActivity;
import com.example.jeffe.trabalho_final.PerfilFragment;
import com.example.jeffe.trabalho_final.SignupActivity;
import com.example.jeffe.trabalho_final.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRequests {

    static FirebaseRequests uniqueInstance = null;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseUsers;
    DatabaseReference currentUserDatabase;

    final List<BuildCompleta> listaDeBuilds = new ArrayList<>();

    private FirebaseRequests(){
        init();
    }

    public static FirebaseRequests GetInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new FirebaseRequests();
        }
        return uniqueInstance;
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        if(currentUser != null){
            currentUserDatabase = databaseUsers.child(currentUser.getUid());

        }
    }



    public void Login(String email, String password, final LoginActivity loginActivity){

        final ProgressDialog progressDialog = new ProgressDialog(loginActivity);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    loginActivity.onLoginSuccess();
                }

                else{

                    loginActivity.onLoginFailed(progressDialog);

                }

            }
        });

    }

    public void CreateAccount(final String name, final String email, String password, final String location, final SignupActivity signupActivity){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(signupActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){

                    String id = databaseUsers.push().getKey();
                    currentUser = mAuth.getCurrentUser();
                    Usuario user = new Usuario(id,name,email,location);
                    databaseUsers.child(currentUser.getUid()).setValue(user);

                    signupActivity.onSignupSuccess();
                }
                else{
                    signupActivity.onSignupFailed(task);
                }

            }
        });
    }

    public Task CreateBuild(BuildCompleta novaBuild){
         VerifyBuild(novaBuild);
         String id = currentUserDatabase.child("userBuild").push().getKey();
         novaBuild.setBuildId(id);
         return  currentUserDatabase.child("userBuild").child(id).setValue(novaBuild);

    }

    public  void GetUserBuilds(final BuildListsFragment buildListsFragment){

        currentUserDatabase.child("userBuild").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Item>> typeListItem = new GenericTypeIndicator<List<Item>>() {};
                listaDeBuilds.clear();
                buildListsFragment.listaDeBuilds.clear();
                for (DataSnapshot build : dataSnapshot.getChildren()){
                    Log.d("data change","on get builds");
                    String id = build.getKey();
                    String nome = build.child("buildName").getValue(String.class);
                    List<Item> listaItemsBuild  = build.child("listaItemsBuild").getValue(typeListItem);

                    BuildCompleta buildCompleta = new BuildCompleta(listaItemsBuild,nome);
                    buildCompleta.setBuildId(id);
                    listaDeBuilds.add(buildCompleta);

                    buildListsFragment.listaDeBuilds.add(buildCompleta);
                    buildListsFragment.buildListsAdapter.notifyDataSetChanged();

                    Log.d("Lista builds","buildListFragment " + buildListsFragment.listaDeBuilds.get(0).getListaItemsBuild().get(0).getDeviceName());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    public void VerifyBuild(final BuildCompleta buildCompleta){


        currentUserDatabase.child("userBuild").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot build : dataSnapshot.getChildren()){
                    String id = build.getKey();

                    if(buildCompleta.getBuildId() == id){

                        build.getRef().setValue(buildCompleta);

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void GetUserProfile(final PerfilFragment perfilFragment){

        currentUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                perfilFragment.getProfileData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void Logout(final PerfilFragment perfilFragment){
        FirebaseAuth.AuthStateListener mAuthListener =  new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser() == null){
                    perfilFragment.backToLogin();
                }

            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        mAuth.signOut();

    }

    public void getListaBuildsSize(final PerfilFragment perfilFragment){
        currentUserDatabase.child("userBuild").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Item>> typeListItem = new GenericTypeIndicator<List<Item>>() {};
                listaDeBuilds.clear();
                for (DataSnapshot build : dataSnapshot.getChildren()){

                    String id = build.getKey();
                    String nome = build.child("buildName").getValue(String.class);
                    Log.d("aaa", "bbb:" + build.child("listaItemsBuild").getValue(typeListItem));
                    List<Item> listaItemsBuild = build.child("listaItemsBuild").getValue(typeListItem);

                    BuildCompleta buildCompleta = new BuildCompleta(listaItemsBuild, nome);
                    buildCompleta.setBuildId(id);
                    listaDeBuilds.add(buildCompleta);

                }
                perfilFragment.numeroBuilds.setText(Long.toString(listaDeBuilds.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void DeleteBuild(BuildCompleta buildCompleta, final BuildListsFragment buildListsFragment){
        currentUserDatabase.child("userBuild").child(buildCompleta.getBuildId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("apaguei,","toda");

                currentUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         if (dataSnapshot.hasChild("userBuild") == false){

                             buildListsFragment.listaDeBuilds.clear();
                             buildListsFragment.buildListsAdapter.notifyDataSetChanged();
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });
    }
}

package com.example.jeffe.trabalho_final.Firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import com.example.jeffe.trabalho_final.LoginActivity;
import com.example.jeffe.trabalho_final.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRequests {

    public static FirebaseRequests uniqueInstance = null;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference currentUserDatabase;


    private FirebaseRequests(){
        init();
    }

    public static FirebaseRequests GetInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new FirebaseRequests();
        }
        return uniqueInstance;
    }

    public void init(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        currentUserDatabase = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
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

    public void CreateAccount(String email, String password, String location, final SignupActivity signupActivity){

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(signupActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    signupActivity.onSignupSuccess();
                }
                else{
                    signupActivity.onSignupFailed();
                }

            }
        });
    }

}

package com.example.jeffe.trabalho_final;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.Amigos.AmigosAdapter;
import com.example.jeffe.trabalho_final.Build.BuildCompleta;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.EditBuildFragment;
import com.example.jeffe.trabalho_final.Requests.FirebaseRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class AmigoPerfilFragment extends Fragment {

    public static MainActivity mainActivity;
    public Usuario usuario = null;
    private static PerfilFragment pF;

    public TextView numeroBuilds;
    public TextView userName;
    public TextView userAdress;
    public TextView userDesc;
    public TextView userFriendList;
    public TextView userEmail;

    private static AmigoPerfilFragment uniqueInstance = null;

    public static AmigoPerfilFragment newInstance(PerfilFragment _perfilFragment, MainActivity mActivity) {
        if(uniqueInstance == null ){
            mainActivity = mActivity;
            uniqueInstance = new AmigoPerfilFragment();
        }

        return uniqueInstance;
    }

    public static AmigoPerfilFragment newInstance() {

        if(uniqueInstance == null ){
            uniqueInstance = new AmigoPerfilFragment();
        }
        return uniqueInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);



        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Carregando perfil...");
        progressDialog.show();
        getProfileData(progressDialog);

        getView().findViewById(R.id.btnLogout).setVisibility(getView().GONE);


    }

    public void getProfileData(ProgressDialog progressDialog){
//        String userNameValue = dataSnapshot.child("userName").getValue(String.class);
//        String userLocValue = dataSnapshot.child("userLocalization").getValue(String.class);
//        String userDescriptionValue = dataSnapshot.child("userDescription").getValue(String.class);
//        String userPictureValue = dataSnapshot.child("userPicture").getValue(String.class);
//        Long userFriendListValue = dataSnapshot.child("userFriendList").getChildrenCount();
//        Long userBuildNValue = dataSnapshot.child("userBuilds").getChildrenCount();
//        String userEmailValue = dataSnapshot.child("userEmail").getValue(String.class);

        userName = getView().findViewById(R.id.tv_name);
        userName.setText(usuario.getUserName());

        userAdress = getView().findViewById(R.id.tv_address);
        userAdress.setText(usuario.getUserLocalization());

        userEmail = getView().findViewById(R.id.tv_email);
        userEmail.setText(usuario.getUserEmail());

        if (usuario.getUserDescription() == null) {
            usuario.setUserDescription("Esse usuário não possui descrição");
        }

        userDesc = getView().findViewById(R.id.tv_desc);
        userDesc.setText(usuario.getUserDescription());

        numeroBuilds = getView().findViewById(R.id.numberBuild);

        //FirebaseRequests.GetInstance().getListaBuildsSize(pF);
        int friendsListSize = 0;
        if (usuario.getFriendsList() != null) {
            friendsListSize = usuario.getFriendsList().size();
        }

        userFriendList = getView().findViewById(R.id.tv_friends);
        userFriendList.setText(Integer.toString(friendsListSize));

        progressDialog.dismiss();
    }

}


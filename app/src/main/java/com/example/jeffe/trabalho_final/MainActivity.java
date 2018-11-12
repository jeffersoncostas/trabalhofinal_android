package com.example.jeffe.trabalho_final;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jeffe.trabalho_final.Amigos.AmigosFragment;
import com.example.jeffe.trabalho_final.Build.BuildFragment;
import com.example.jeffe.trabalho_final.Build.BuildListsFragment;
import com.example.jeffe.trabalho_final.Build.Item;
import com.example.jeffe.trabalho_final.Build.MyBuilds;
import com.example.jeffe.trabalho_final.Noticias.NoticiasFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ImageView testeImg;

    private TextView perfilNumeroBuilds;

    private MainActivity mContext;

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



       initializeBottomNavigation();

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
        Fragment buildFragment = AmigosFragment.newInstance();
        openFragment(buildFragment);

    }

    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        ColorDrawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);

    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    public void onDelIconClick(View view) {

    }


    public void onClickFloatButton(View view){
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_newbuild, null);

        popupView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.popup_animation));

        Button btnSalvar = popupView.findViewById(R.id.bttn_salvarbuild);
        final EditText inputPopup = popupView.findViewById(R.id.buildNameInput);

        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

        applyDim(root, 0.5f);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.setElevation(5);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -90);

      /*  popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("oii", "aaa");

                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    clearDim(root);
                    return true;
                }
                return false;
            }
        });*/

        // dismiss the popup
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Item> novaBuild = BuildFragment.newInstance().buildList;
                MyBuilds.getInstance().enviarNovaBuild(novaBuild,inputPopup.getText().toString());

                popupWindow.dismiss();
                clearDim(root);
                Fragment buildsList = BuildListsFragment.newInstance(mContext);
                openFragment(buildsList);

            }
        });

    }
}

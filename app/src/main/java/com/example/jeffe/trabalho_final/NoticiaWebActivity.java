package com.example.jeffe.trabalho_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class NoticiaWebActivity extends AppCompatActivity {

    ImageView testeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_web);

        testeImg = (ImageView) findViewById(R.id.imageView);

        try {
            Glide.with(this).load("https://web2.hirez.com/smite-media//wp-content/uploads/2018/11/PatchNotes-PatchBadge-UpdateNotes-FeatImg-283x213-3.jpg").into(testeImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

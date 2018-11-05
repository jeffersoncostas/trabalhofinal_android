package com.example.jeffe.trabalho_final.Noticias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jeffe.trabalho_final.R;


public class NoticiaWebActivity extends AppCompatActivity {

    ImageView testeImg;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_web);

        webView = findViewById(R.id.webViewNews);
        webView.loadUrl("https://www.google.com");

    }
}

package com.example.jeffe.trabalho_final.Noticias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jeffe.trabalho_final.R;


public class NoticiaWebActivity extends AppCompatActivity {

    ImageView testeImg;
    WebView webView;

    String noticiaSlug;

    String noticiaUrl = "https://www.smitegame.com/news/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_web);


        webView = findViewById(R.id.webViewNews);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        noticiaSlug= getIntent().getStringExtra("noticiaSlug");
        Log.d("slug",noticiaSlug);

        webView.loadUrl(noticiaUrl+noticiaSlug);

    }
}

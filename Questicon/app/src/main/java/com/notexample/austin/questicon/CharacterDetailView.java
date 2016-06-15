package com.notexample.austin.questicon;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CharacterDetailView extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SearchWithWebview();

        final String picasso = getIntent().getStringExtra("url2");
        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load("https://us.battle.net/static-render/us/" + picasso).into(imageView);


    }

    public void SearchWithWebview() {

        String nameChar = getIntent().getStringExtra("nameChar");
        String nameRealm = getIntent().getStringExtra("nameRealm");


        WebView webViewCharacter = (WebView) findViewById(R.id.webViewCharDetail);


//        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
        webViewCharacter.setWebViewClient(new WebViewClient());
        webViewCharacter.getSettings().setBuiltInZoomControls(true);
        webViewCharacter.getSettings().setSupportZoom(true);
        webViewCharacter.setInitialScale(100);
        webViewCharacter.getSettings().getLoadsImagesAutomatically();
        webViewCharacter.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webViewCharacter.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            webViewCharacter.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webViewCharacter.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webViewCharacter.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

//        webViewCharacter.setWebViewClient(new WebViewClient() {
//
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                pd.setMessage("Loading your character, just one second more...");
//            }
//
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (pd != null && pd.isShowing()) {
//                    pd.dismiss();
//                }
//            }
//        });

        webViewCharacter.loadUrl("http://us.battle.net/wow/en/character/" + nameRealm + "/" + nameChar + "/advanced");


    }
}

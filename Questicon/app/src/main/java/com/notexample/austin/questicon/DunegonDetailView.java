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

public class DunegonDetailView extends AppCompatActivity {

    WebView dungeonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dunegon_detail_view);


        dungeonName = (WebView) findViewById(R.id.webViewDungeonDetail);


        final String name = getIntent().getStringExtra("name");


//        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
        dungeonName.setWebViewClient(new WebViewClient());
        dungeonName.getSettings().setBuiltInZoomControls(true);
        dungeonName.getSettings().setSupportZoom(true);
        dungeonName.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        dungeonName.getSettings().getLoadsImagesAutomatically();
        dungeonName.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        dungeonName.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            dungeonName.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            dungeonName.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        dungeonName.setWebViewClient(new WebViewClient() {
//
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                pd.setMessage("Loading dungeon info, just one second more...");
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

        dungeonName.loadUrl("http://wow.gamepedia.com/" + name);


    }
}

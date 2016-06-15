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
import android.widget.TextView;

public class BossesDetailView extends AppCompatActivity {

    WebView bossesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bosses_detail_view);


        bossesName = (WebView) findViewById(R.id.webViewBossesDetail);


        final String name = getIntent().getStringExtra("name");


//        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
        bossesName.setWebViewClient(new WebViewClient());
        bossesName.getSettings().setBuiltInZoomControls(true);
        bossesName.getSettings().setSupportZoom(true);
        bossesName.getSettings().getLoadsImagesAutomatically();
        bossesName.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        bossesName.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        bossesName.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            bossesName.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            bossesName.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        bossesName.setWebViewClient(new WebViewClient() {
//
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                pd.setMessage("Loading boss info, just one second more...");
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

        bossesName.loadUrl("http://wow.gamepedia.com/" + name);


    }
}

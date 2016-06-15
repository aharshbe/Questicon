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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MountDetailView extends AppCompatActivity {

    WebView mountWebView;
    ImageView imageViewMount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mount_detail_view);


        mountWebView = (WebView) findViewById(R.id.webViewMountDetail);
        imageViewMount = (ImageView) findViewById(R.id.imageViewMount);


        final String name = getIntent().getStringExtra("name");
        final String url = getIntent().getStringExtra("imageurl");

        Picasso.with(this).load("http://wow.zamimg.com/images/wow/icons/large/" + url + ".jpg").into(imageViewMount);


//        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
        mountWebView.setWebViewClient(new WebViewClient());
        mountWebView.getSettings().setBuiltInZoomControls(true);
        mountWebView.getSettings().setSupportZoom(true);
        mountWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mountWebView.getSettings().getLoadsImagesAutomatically();
        mountWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mountWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            mountWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mountWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
//        mountWebView.setWebViewClient(new WebViewClient() {
//
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                pd.setMessage("Loading mount info, just one second more...");
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

        mountWebView.loadUrl("http://wow.gamepedia.com/" + name);


    }
}

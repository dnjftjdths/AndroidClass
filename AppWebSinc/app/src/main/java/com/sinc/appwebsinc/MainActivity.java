package com.sinc.appwebsinc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private WebView mWebview ;
    private Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webviewSet();
    }

    public class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void webviewSet() {
        mWebview = (WebView)findViewById(R.id.webview);
        mWebview.loadUrl("http://10.149.179.78:8088/index.sinc");
        //////////// brower load
        mWebview.setWebViewClient(new WebViewClientClass());
        ////////////script
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setSupportZoom(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setDisplayZoomControls(false);
        //////
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);

        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebview.invalidate();
            }
        });
        ////
        // mWebview.addJavascriptInterface(new AndroidBridge(), "sinc");
        //// window.alert
        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("dialog")
                        .setMessage(message)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()){
            mWebview.goBack();
            return true ;
        }else if( (keyCode == KeyEvent.KEYCODE_BACK) && !mWebview.canGoBack() ){
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK :
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("프로그램 종료")
                            .setMessage("프로그램 종료하시겠습니까?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            })
                            .setNegativeButton("CANCEL", null).show() ;
                    break ;
            }
            return true ;
        }
        return super.onKeyDown(keyCode, event);
    }
}

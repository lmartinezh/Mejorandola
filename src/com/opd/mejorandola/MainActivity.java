package com.opd.mejorandola;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public WebView webView; 
	public String url;
	static final String tag = "mio";
	
    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url="https://cursos.mejorando.la";
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        webView = (WebView) findViewById(R.id.webView);
        verMiWeb wv = new verMiWeb(); 
        //webView.setWebChromeClient(new WebChromeClient());
        //webView.setWebViewClient(new WebViewClient());
        
        //Configuramos algunos parametros necesarios en el web view
        WebSettings webSettings = webView.getSettings(); 
        webSettings.setJavaScriptEnabled(true); 
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false); 
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //Prevenimos el scroll en el web view
        webView.setScrollbarFadingEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        
        //Cargamos el archivo Html
        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
        	   public void onProgressChanged(WebView view, int progress) {
            	 activity.setProgress(progress * 1000);
        	   }
        	 });
        webView.setWebViewClient(wv);
      
        
        webView.loadUrl(url);
        
        
    }
    //Para mostrar cargando al cargar una url
    private class verMiWeb extends WebViewClient { 
    	Toast t = Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_LONG);
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		view.loadUrl(url);
    	return true;
    	}
    	public void onPageStarted(WebView view, String url, Bitmap favicon){
    		t.show();
    		}
    	public void onPageFinished(WebView view, String url){
    		t.cancel();
    	}
    }
    //Eventos del boton atras
    boolean backtwice=false;
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK) { 
			if (backtwice) {
                backtwice=false; 
                finish();
             }
             else
             {
                backtwice=true; 
                Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show();
             }
			return true;
        }
       
        return super.onKeyDown(keyCode, event);
    }
        
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }

        super.onBackPressed();
    }
    
    
     @Override

   public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
    
}

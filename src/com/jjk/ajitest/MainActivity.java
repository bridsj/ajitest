package com.jjk.ajitest;

//import my.demo.R;

import java.lang.reflect.ReflectPermission;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//n
import android.webkit.JavascriptInterface;


public class MainActivity extends Activity {
	private WebView weev;
	private TextView myTextView;	
	final Handler myHandler = new Handler();
    EditText toload;

	JavaScriptInterface jsi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        weev = (WebView)findViewById(R.id.webViewJK);
        myTextView = (TextView)findViewById(R.id.textView1);
        toload = (EditText) findViewById(R.id.editTextIP);
        toload.setText("http://192.168.1.128:8787/aji/n");
        
        //gibblets
        jsi = new JavaScriptInterface(this);
        weev.getSettings().setJavaScriptEnabled(true);
        weev.addJavascriptInterface(jsi, "jjkjjk");
        
        weev.getSettings().setPluginState(PluginState.ON);
        weev.getSettings().setAllowFileAccess(true);
        SecurityManager manager =new SecurityManager();
        if(manager!=null){
        manager.checkPermission(new ReflectPermission("suppressAccessChecks"));
        }
	}

	@SuppressWarnings("deprecation")
	public void launch( View view ) {
        //Wv.loadUrl("file:///android_asset/www/index.html"); 
        String tl = toload.getText().toString();
        weev.loadUrl( tl );
	}


    public class JavaScriptInterface {
		Context mContext;
	    JavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(mContext, description, Toast.LENGTH_SHORT).show();
        }
	    
	    @JavascriptInterface
    	public void log(String msg){
    		Log.d("AJSI MESSAGE: ", msg);
    	}
	    
	    @JavascriptInterface
	    public void showToast(String webMessage){	    	
	    	final String msgeToast = webMessage;	    	
	    	 myHandler.post(new Runnable() {
	             @Override
	             public void run() {
	                 // This gets executed on the UI thread so it can safely modify Views
	                 myTextView.setText(msgeToast);
	             }
	         });

	       Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
	    }
    }

}

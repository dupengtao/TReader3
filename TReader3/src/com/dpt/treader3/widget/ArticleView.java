package com.dpt.treader3.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ArticleView {

    private WebView mWebView;
	private Context context;

    public ArticleView(Context context,WebView webView) {
        this.mWebView = webView;
        this.context = context;
        initArticleView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initArticleView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        loadLocal();
        mWebView.addJavascriptInterface(new InJavaScriptArticle(), "dpt");
        mWebView.setWebViewClient(new WebViewClient(){

        	
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		//view.loadUrl(url);
        		return true;
        	}
        	
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
//				view.loadUrl("javascript:window.dpt.checkContext("  
//                        + "document.getElementById('content').innerHTML);");  
			}
        	
        });
    }

    public void setArticleContext(String context) {
        mWebView.loadUrl("javascript:setArticleContext('" + context + "')");
        checkContext();
    }

    public void setArticleTitle(String title) {
        mWebView.loadUrl("javascript:setArticleTitle('" + title + "')");
    }

    public void setArticlePrompt(String prompt) {
        mWebView.loadUrl("javascript:setArticlePrompt('" + prompt + "')");
    }
    
    public void checkContext(){
    	mWebView.loadUrl("javascript:window.dpt.checkContext("  
                + "document.getElementById('content').innerHTML);");  
    }
    public void nightMode(){
    	mWebView.loadUrl("javascript:setNightMode()");  
    }

    public void scroll2Top() {
        mWebView.scrollTo(0, 0);
    }

    public void clear() {
    	mWebView.loadUrl("javascript:setArticleContext('')");
        setArticleTitle("loading");
        setArticlePrompt("loading");
    }

    public void loadurl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadLocal() {
        loadurl("file:///android_asset/test.html");
    }
    
    final class InJavaScriptArticle {
    	@JavascriptInterface
        public void checkContext(String html) {
            if(TextUtils.isEmpty(html.trim())){
            	Toast.makeText(context, "内容解析失败", Toast.LENGTH_SHORT).show();
            }
        }  
    }  
}

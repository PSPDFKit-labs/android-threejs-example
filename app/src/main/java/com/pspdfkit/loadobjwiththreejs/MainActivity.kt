package com.pspdfkit.loadobjwiththreejs

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler


private const val BASE_URL = "https://appassets.androidplatform.net/assets/www"

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setup webView
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView = findViewById(R.id.three_web_view)
        webView.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            allowContentAccess = true
        }
        loadScene()
    }

    private fun loadScene() {
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .build()
        webView.loadUrl("$BASE_URL/index.html")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                loadObjModel()
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
            }

            @SuppressWarnings("deprecation") // for API < 21
            override fun shouldInterceptRequest(
                view: WebView?,
                url: String?
            ): WebResourceResponse {
                return assetLoader.shouldInterceptRequest(Uri.parse(url))!!
            }

            @RequiresApi(21)
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(request!!.url)
            }
        }
    }

    private fun loadObjModel() {
        val action = "javascript:loadModel('$BASE_URL/models/','PEACE_LILLY_5K','base')"
        webView.loadUrl(action)
    }
}

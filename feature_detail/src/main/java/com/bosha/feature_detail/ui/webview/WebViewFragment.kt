package com.bosha.feature_detail.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.bosha.feature_detail.R

class WebViewFragment: Fragment(R.layout.fragment_webview) {

    private val movieId: String by lazy {
        requireNotNull(requireArguments().getString("id"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebViewSettings(view)
    }

    private fun setUpWebViewSettings(view: View){
        val webView = view.findViewById<WebView>(R.id.webview)
        val loader = view.findViewById<ProgressBar>(R.id.vw_pb_loading)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    loader.visibility = View.GONE
                    webView.visibility = View.VISIBLE
                }
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
            }
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        }
        webView.webViewClient = WebViewClient() //если открывается браузер
        webView.settings.javaScriptEnabled = true

        webView.loadUrl("$BASE_URL_WEB_VIEW$movieId")
    }

    companion object {
        const val BASE_URL_WEB_VIEW = "https://www.themoviedb.org/movie/"
    }
}
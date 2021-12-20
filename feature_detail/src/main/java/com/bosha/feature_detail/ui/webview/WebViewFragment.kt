package com.bosha.feature_detail.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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

    //перенести вебвью в фрагмент
    private fun setUpWebViewSettings(view: View){
        val webView = view.findViewById<WebView>(R.id.webview)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100)
                Toast.makeText(requireContext(), "loaded", Toast.LENGTH_SHORT).show() // заменить на норм лоадер
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

        webView.loadUrl("https://www.themoviedb.org/movie/$movieId")
    }
}
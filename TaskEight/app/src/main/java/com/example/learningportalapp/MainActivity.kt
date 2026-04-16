package com.example.learningportalapp

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar
    lateinit var enterURL: EditText

    val homeURL = "https://www.google.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        webView = findViewById<WebView>(R.id.webView)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val backBtn = findViewById<Button>(R.id.backBtn)
        val forwardBtn = findViewById<Button>(R.id.forwardBtn)
        val reloadBtn = findViewById<Button>(R.id.reloadBtn)
        val homeBtn = findViewById<Button>(R.id.homeBtn)
        val goBtn = findViewById<Button>(R.id.goBtn)

        val googleBtn = findViewById<Button>(R.id.googleBtn)
        val youtubeBtn = findViewById<Button>(R.id.youtubeBtn)
        val wikipediaBtn = findViewById<Button>(R.id.wikipediaBtn)
        val khanacademyBtn = findViewById<Button>(R.id.khanacdemyBtn)
        val torrentBDBtn = findViewById<Button>(R.id.torrentBDBtn)
        val aiubBtn = findViewById<Button>(R.id.aiubBtn)

        enterURL = findViewById(R.id.enterURL)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?){
                progressBar.visibility = View.VISIBLE
                enterURL.setText(url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                enterURL.setText(url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest, error: WebResourceError) {
                view?.loadUrl("file:///android_asset/offline.html")
            }
        }

        webView.webChromeClient = WebChromeClient()
        loadURL(homeURL)

        backBtn.setOnClickListener {
            if(webView.canGoBack()) {
                webView.goBack()
            } else {
                Toast.makeText(this, "No back history", Toast.LENGTH_SHORT).show()
            }
        }

        forwardBtn.setOnClickListener {
            if(webView.canGoForward()) {
                webView.goForward()
            }
        }

        reloadBtn.setOnClickListener {
            webView.reload()
        }

        homeBtn.setOnClickListener {
            loadURL(homeURL)
        }

        goBtn.setOnClickListener {
            loadFromInput()
        }

        enterURL.setOnKeyListener { _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                loadFromInput()
                true
            } else {
                false
            }
        }

        googleBtn.setOnClickListener {
            loadURL("https://www.google.com")
        }

        youtubeBtn.setOnClickListener {
            loadURL("https://www.youtube.com")
        }

        wikipediaBtn.setOnClickListener {
            loadURL("https://www.wikipedia.org")
        }

        khanacademyBtn.setOnClickListener {
            loadURL("https://www.khanacademy.org")
        }

        torrentBDBtn.setOnClickListener {
            loadURL("https://www.torrentbd.com")
        }

        aiubBtn.setOnClickListener {
            loadURL("https://www.aiub.edu")
        }

        onBackPressedDispatcher.addCallback(this@MainActivity,
            object : androidx.activity.OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                }
            }
        )
    }

    private fun loadFromInput() {
        var url = enterURL.text.toString()

        if(!url.startsWith("http://")) {
            url = "https://$url"
        }
        loadURL(url)
    }

    private fun loadURL(url: String) {
        if(isConnected()) {
            webView.loadUrl(url)
        } else {
            webView.loadUrl("file:///android_asset/offline.html")
        }
    }

    private fun isConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetworkInfo

        return network != null && network.isConnected
    }
}
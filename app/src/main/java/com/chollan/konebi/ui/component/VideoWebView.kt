package com.chollan.konebi.ui.component

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoWebView(videoUri: String, modifier: Modifier = Modifier) {
    val window = (LocalContext.current as Activity).window

    AndroidView(factory = {
        WebView(it).apply {
            setBackgroundColor(Color.Transparent.toArgb())

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()

            var mCustomView: View? = null
            var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null
            var mOriginalSystemUiVisibility = 0

            webChromeClient = object : WebChromeClient() {
                override fun onShowCustomView(
                    view: View?,
                    callback: CustomViewCallback?
                ) {
                    if (mCustomView != null) {
                        callback?.onCustomViewHidden()
                        return
                    }
                    mCustomView = view
                    mCustomViewCallback = callback

                    mOriginalSystemUiVisibility = window.decorView.systemUiVisibility

                    window.decorView.systemUiVisibility = (
                            View.SYSTEM_UI_FLAG_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_IMMERSIVE)

                    val decor: FrameLayout = window.decorView as FrameLayout
                    decor.addView(mCustomView, FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT))
                }

                override fun onHideCustomView() {
                    val decor: FrameLayout = window.decorView as FrameLayout
                    decor.removeView(mCustomView)
                    mCustomView = null
                    window.decorView.systemUiVisibility = mOriginalSystemUiVisibility
                    mCustomViewCallback?.onCustomViewHidden()
                    mCustomViewCallback = null
                }
            }
            loadUrl(videoUri)
        }
    }, modifier = modifier)

}
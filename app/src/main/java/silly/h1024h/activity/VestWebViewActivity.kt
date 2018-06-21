package silly.h1024h.activity

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_vestwebview.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseActivity


class VestWebViewActivity : BaseActivity() {
    override fun setLayoutView(): Int {
        return R.layout.activity_vestwebview
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true
        webview.settings.setSupportZoom(true)
        webview.settings.loadWithOverviewMode = true
        webview.settings.blockNetworkImage = false
        // 设置webview不外跳
        webview.webViewClient = WebViewClient()
        webview.settings.setSupportMultipleWindows(false)
        // 5.0以上webview无法显示图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webview.loadUrl("https://pixabay.com/")
    }


    override fun initData() {

    }

    override fun initEvent() {

    }
}

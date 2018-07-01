package silly.h1024h

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.meituan.android.walle.WalleChannelReader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.tinker.TinkerManager.getApplication
import com.tencent.bugly.crashreport.CrashReport
import silly.h1024h.utils.Init


class App : Application() {

    private val TAG = "App"

    override fun onCreate() {
        super.onCreate()
        Init.init(this)
        Init.initDb(this)
        // 多渠道
        val channel = WalleChannelReader.getChannel(this)
        Bugly.setAppChannel(this, channel)
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        // 热修复
        Bugly.init(this, "12e38fe984", BuildConfig.DEBUG)
        // 异常上报
        CrashReport.initCrashReport(this, "12e38fe984", BuildConfig.DEBUG)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)


        // 安装tinker
        Beta.installTinker()
    }
}


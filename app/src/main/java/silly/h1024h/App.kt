package silly.h1024h

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import silly.h1024h.utils.Init

class App : Application() {

    private val TAG = "App"

    override fun onCreate() {
        super.onCreate()
        Init.init(this)
        Init.initDb(this)
    }
}


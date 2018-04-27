package silly.h1024h.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
object Init {
    lateinit var ctx: Application

    fun init(app: Application) {
        this.ctx = app
    }
}
package silly.h1024h

import android.app.Application
import silly.h1024h.utils.Init

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Init.init(this)
        Init.initDb(this)
    }
}
package silly.h1024h

import android.app.Application
import silly.h1024h.utils.Init

/**
 * Created by SillySnnall on 2018/4/25.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Init.init(this)
    }
}
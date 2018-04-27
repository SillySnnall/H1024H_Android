package silly.h1024h.utils

import android.util.Log
import silly.h1024h.BuildConfig

/**
 * Created by SillySnnall on 2018/4/26.
 */
object LogUtil {
    private const val TAG = "LogUtil"
    fun v(str: String) {
        if (BuildConfig.DEBUG) Log.v(TAG, str)
    }

    fun d(str: String) {
        if (BuildConfig.DEBUG) Log.d(TAG, str)
    }

    fun i(str: String) {
        if (BuildConfig.DEBUG) Log.i(TAG, str)
    }

    fun w(str: String) {
        if (BuildConfig.DEBUG) Log.w(TAG, str)
    }

    fun e(str: String) {
        if (BuildConfig.DEBUG) Log.e(TAG, str)
    }
}
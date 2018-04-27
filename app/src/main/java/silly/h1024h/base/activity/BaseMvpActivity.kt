package silly.h1024h.base.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle

@SuppressLint("Registered")
abstract class BaseMvpActivity<P> : BaseActivity() {
    var mPersenter: P? = null
    abstract fun setPersenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        mPersenter = setPersenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPersenter = null
    }


}
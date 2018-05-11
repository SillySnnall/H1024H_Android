package silly.h1024h.base.fragment

import android.os.Bundle

abstract class BaseMvpFragment<P> : BaseFragment() {
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
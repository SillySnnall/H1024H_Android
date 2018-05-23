package silly.h1024h.base.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import silly.h1024h.view.SillyDialog

/**
 * Created by SillySnnall on 2018/4/26.
 */
abstract class BaseActivity: FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutView())
        initView()
        initData()
        initEvent()
    }
    abstract fun setLayoutView():Int

    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()
}
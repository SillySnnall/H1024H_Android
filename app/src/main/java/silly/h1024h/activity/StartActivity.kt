package silly.h1024h.activity

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_start.*
import silly.h1024h.R
import silly.h1024h.R.id.start_root
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.contract.DetailsContract
import silly.h1024h.contract.StartContract
import silly.h1024h.persenter.StartPersenter
import java.util.*


class StartActivity : BaseMvpActivity<StartContract.Presenter>(), StartContract.View {
    override fun initSuccess() {
        initSuccess = true
        if (isJump) jumpMain()
    }

    override fun setPersenter(): StartContract.Presenter {
        return StartPersenter(this)
    }

    private var timer: Timer? = Timer()
    private var isJump = false
    private var initSuccess = false

    override fun setLayoutView(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        timer?.schedule(object : TimerTask() {
            override fun run() {
                isJump = true
                if (initSuccess) jumpMain()
            }
        }, 2000)
    }


    override fun initData() {
        mPersenter?.getMoreList()
    }

    override fun initEvent() {
        start_root.setOnClickListener {
            if (isJump) jumpMain()
        }
    }

    private fun jumpMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        // 一定设置为null，否则定时器不会被回收
        timer = null
    }
}

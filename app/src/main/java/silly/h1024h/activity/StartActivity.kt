package silly.h1024h.activity

import android.content.Intent
import kotlinx.android.synthetic.main.activity_start.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseActivity
import java.util.*


class StartActivity : BaseActivity() {

    private var timer: Timer? = Timer()
    private var isJump = false

    override fun setLayoutView(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        timer?.schedule(object : TimerTask() {
            override fun run() {
                isJump = true
                jumpMain()
            }
        }, 2000)
    }


    override fun initData() {

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

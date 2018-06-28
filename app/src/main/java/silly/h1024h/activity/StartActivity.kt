package silly.h1024h.activity

import kotlinx.android.synthetic.main.activity_start.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.contract.StartContract
import silly.h1024h.persenter.StartPersenter
import java.util.*


class StartActivity : BaseMvpActivity<StartContract.Presenter>(), StartContract.View {
    override fun initEvent() {

    }

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
                runOnUiThread({
                    isJump = true
                    if (initSuccess) jumpMain()
                })
            }
        }, 2000)
    }


    override fun initData() {
        mPersenter?.getURL()
    }

    private fun jumpMain() {
        loading.text = "初始化完成"
//        when (mPersenter?.getServerType()) {
//            0 -> {
//            }
//            1 -> {
//                startActivity(Intent(this, VestWebViewActivity::class.java))
//                finish()
//            }
//            2 -> {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
//            }
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        // 一定设置为null，否则定时器不会被回收
        timer = null
    }
}

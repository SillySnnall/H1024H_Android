package silly.h1024h.activity

import android.content.Intent
import com.silly.sillypermission.SillyPermission
import com.silly.sillypermission.SillyPermission.PERMISSION_PHONE
import com.silly.sillypermission.SillyPermission.PERMISSION_STORAGE
import com.silly.sillypermission.SillyPermissionCall
import kotlinx.android.synthetic.main.activity_start.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.contract.StartContract
import silly.h1024h.http.HttpConfig.URL_MAIN
import silly.h1024h.http.HttpConfig.URL_SERVICE
import silly.h1024h.http.HttpConfig.URL_VEST
import silly.h1024h.persenter.StartPersenter
import silly.h1024h.utils.Util.installAPK
import java.util.*


class StartActivity : BaseMvpActivity<StartContract.Presenter>(), StartContract.View {
    override fun downloadAPK(url: String) {
        loading.text = "正在更新......"
        mPersenter?.updateAPK(url, downloading = {
            progress.progress = it!!
        }, success = {
            installAPK(it!!)
        })
    }

    override fun initEvent() {}

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

    }


    override fun initData() {
        // 申请多个权限
        SillyPermission.requestPermission(this, SillyPermissionCall {
            if (!it) return@SillyPermissionCall
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread({
                        isJump = true
                        if (initSuccess) jumpMain()
                    })
                }
            }, 2000)
            mPersenter?.getURL()
        }, PERMISSION_PHONE, PERMISSION_STORAGE)
    }

    private fun jumpMain() {
        loading.text = "初始化完成"
//        when (mPersenter?.getServerType()) {
//            1 -> startActivity(Intent(this, VestWebViewActivity::class.java))
//            2 -> startActivity(Intent(this, MainActivity::class.java))
//        }
        when (mPersenter?.getServerType()) {
            1 -> URL_SERVICE = URL_VEST
            2 -> URL_SERVICE = URL_MAIN
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        // 一定设置为null，否则定时器不会被回收
        timer = null
    }

    // 必须在Activity中重写 onActivityResult 做回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SillyPermission.onActivityResultPermission(this, requestCode)
    }

    // 必须在Activity中重写 onRequestPermissionsResult 做回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        SillyPermission.onRequestPermissionsResultPermission(this, requestCode, permissions, grantResults)
    }

}

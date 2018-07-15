package silly.h1024h.activity

import android.content.Context
import android.view.inputmethod.InputMethodManager
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_find_pwd.*
import silly.h1024h.contract.FindPwdContract
import silly.h1024h.persenter.FindPwdPersenter
import silly.h1024h.utils.ToastUtil
import silly.h1024h.utils.Util
import java.util.*


/**
 * 找回密码
 */
class FindPwdActivity : BaseMvpActivity<FindPwdContract.Presenter>(), FindPwdContract.View {

    private var timer: Timer? = null
    private val totalNum = 60
    private var numCount = totalNum

    override fun findSuccess() {
        finish()
    }

    override fun setPersenter(): FindPwdContract.Presenter {
        return FindPwdPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_find_pwd
    }

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initEvent() {
        // 返回
        pre.setOnClickListener {
            finish()
        }
        // 点击空白键盘消失
        root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(root.windowToken, 0)
        }
        // 获取验证码
        send_code.setOnClickListener {
            val account = account.text.toString()
            if (account.isEmpty()) {
                ToastUtil.toast("帐号不能为空")
                return@setOnClickListener
            }
            val email = email.text.toString()
            if (email.isEmpty()) {
                ToastUtil.toast("邮箱不能为空")
                return@setOnClickListener
            }
            if (!Util.isEmail(email)) {
                ToastUtil.toast("邮箱格式不正确")
                return@setOnClickListener
            }
            if (send_code.text.toString() != "获取") {
                ToastUtil.toast("验证码已发送")
                return@setOnClickListener
            }
            numCount = totalNum
            send_code.text = numCount.toString()
            if (timer == null) {
                timer = Timer()
                timer?.schedule(timerTask, 0, 1000)
            }
            mPersenter?.getCode(account, email)
        }
        // 找回密码
        commit.setOnClickListener {
            val account = account.text.toString()
            if (account.isEmpty()) {
                ToastUtil.toast("帐号不能为空")
                return@setOnClickListener
            }
            val email = email.text.trim().toString()
            if (email.isEmpty()) {
                ToastUtil.toast("邮箱不能为空")
                return@setOnClickListener
            }
            if (!Util.isEmail(email)) {
                ToastUtil.toast("邮箱格式不正确")
                return@setOnClickListener
            }
            val code = code.text.trim().toString()
            if (code.isEmpty()) {
                ToastUtil.toast("验证码不能为空")
                return@setOnClickListener
            }
            val newPwd = new_pwd.text.trim().toString()
            if (newPwd.isEmpty()) {
                ToastUtil.toast("新密码不能为空")
                return@setOnClickListener
            }
            mPersenter?.findPwd(account, email, code, newPwd)
        }
    }

    private val timerTask = object : TimerTask() {
        override fun run() {
            runOnUiThread({
                if (--numCount == 0 || send_code.text.toString() == "获取") {
                    send_code.text = "获取"
                    return@runOnUiThread
                }
                send_code.text = (numCount).toString()
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
    }
}
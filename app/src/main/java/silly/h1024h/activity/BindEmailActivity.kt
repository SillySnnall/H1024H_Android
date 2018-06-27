package silly.h1024h.activity

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_bind_email.*
import kotlinx.android.synthetic.main.no_bind_email.*
import kotlinx.android.synthetic.main.yes_bind_email.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.common.SpCommon.EMAIL
import silly.h1024h.contract.BindEmailContract
import silly.h1024h.persenter.BindEmailPersenter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil
import silly.h1024h.utils.Util
import java.util.*

/**
 * 绑定邮箱
 */
class BindEmailActivity : BaseMvpActivity<BindEmailContract.Presenter>(), BindEmailContract.View {
    private var timer: Timer? = null
    private val totalNum = 60
    private var numCount = totalNum

    override fun commitSuccess() {
        email_text.text = SpUtil.getString(EMAIL)
        isBindEmail()
    }

    override fun setPersenter(): BindEmailContract.Presenter {
        return BindEmailPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_bind_email
    }

    override fun initView() {
        email_text.text = SpUtil.getString(EMAIL)
        isBindEmail()
    }

    override fun initData() {

    }

    override fun initEvent() {
        // 返回
        pre.setOnClickListener {
            finish()
        }
        // 点击空白键盘消失
        bind_email_root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(bind_email_root.windowToken, 0)
        }
        // 获取验证码
        send_code.setOnClickListener {
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
            mPersenter?.sendCode(email)
        }
        // 提交
        commit.setOnClickListener {
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
            mPersenter?.commitBind(email, code)
        }
        // 更换邮箱
        replce.setOnClickListener {
            no_bind_email.visibility = View.VISIBLE
            yes_bind_email.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
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

    private fun isBindEmail() {
        if (SpUtil.getString(EMAIL)?.isEmpty()!!) {
            no_bind_email.visibility = View.VISIBLE
            yes_bind_email.visibility = View.GONE
        } else {
            no_bind_email.visibility = View.GONE
            yes_bind_email.visibility = View.VISIBLE
        }
    }
}

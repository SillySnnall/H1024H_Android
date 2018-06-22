package silly.h1024h.activity

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import silly.h1024h.R
import silly.h1024h.R.id.*
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.contract.HomeContract
import silly.h1024h.contract.RegisterContract
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.fragment.HomeFragment
import silly.h1024h.fragment.MeFragment
import silly.h1024h.fragment.RecommendFragment
import silly.h1024h.persenter.DetailsPersenter
import silly.h1024h.persenter.RegisterPersenter
import silly.h1024h.utils.ToastUtil.toast

/**
 * Created by SillySnnall on 2018/6/22.
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.Presenter>(), RegisterContract.View {
    override fun registerSuccess() {
        EventBus.getDefault().post(EventBusMessage(EventBusConstant.LOGIN))
        finish()
    }

    override fun setPersenter(): RegisterContract.Presenter {
        return RegisterPersenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()

    }


    override fun setLayoutView(): Int {
        return R.layout.activity_register
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
        register_root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(register_root.windowToken, 0)
        }

        // 注册
        register.setOnClickListener {
            // 判空
            if (account.text.trim().isEmpty()) {
                toast("帐号不能为空")
                return@setOnClickListener
            }
            if (password.text.trim().isEmpty()) {
                toast("密码不能为空")
                return@setOnClickListener
            }
            if (password_sure.text.trim().isEmpty()) {
                toast("确认密码不能为空")
                return@setOnClickListener
            }
            // 确认密码
            if (password.text.trim().toString() != password_sure.text.trim().toString()) {
                toast("两次输入密码不同")
                return@setOnClickListener
            }
            mPersenter?.register(account.text.trim().toString(), password.text.trim().toString())
        }
    }

}
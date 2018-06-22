package silly.h1024h.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.loading_view.*
import kotlinx.android.synthetic.main.no_login.*
import kotlinx.android.synthetic.main.yes_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import silly.h1024h.R
import silly.h1024h.activity.RegisterActivity
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.common.SpCommon.ACCOUNT
import silly.h1024h.common.SpCommon.TOKEN
import silly.h1024h.contract.MeContract
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.persenter.MePersenter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil.toast
import silly.h1024h.utils.Util.isLogin

class MeFragment : BaseMvpFragment<MeContract.Presenter>(), MeContract.View {
    override fun fail() {
        loading.visibility = View.GONE
    }

    override fun loginSuccess() {
        loading.visibility = View.GONE
        EventBus.getDefault().post(EventBusMessage(EventBusConstant.LOGIN))
    }

    override fun setPersenter(): MeContract.Presenter {
        return MePersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun initData() {

    }

    override fun initEvent() {
        // 注册新用户
        register.setOnClickListener { startActivity(Intent(context, RegisterActivity::class.java)) }
        // 点击空白键盘消失
        no_login.setOnClickListener {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(no_login.windowToken, 0)
        }
        // 注册
        login.setOnClickListener {
            // 判空
            if (account.text.trim().isEmpty()) {
                toast("帐号不能为空")
                return@setOnClickListener
            }
            if (password.text.trim().isEmpty()) {
                toast("密码不能为空")
                return@setOnClickListener
            }
            loading.visibility = View.VISIBLE
            mPersenter?.login(account.text.trim().toString(), password.text.trim().toString())
        }

        loading.setOnClickListener {
            // 拦截点击事件,加载时不可点击
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (EventBusConstant.LOGIN == event.type) {
            if (isLogin()) {
                no_login.visibility = View.GONE
                yes_login.visibility = View.VISIBLE
            } else {
                no_login.visibility = View.VISIBLE
                yes_login.visibility = View.GONE
            }
        }
    }
}

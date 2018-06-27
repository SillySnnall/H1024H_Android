package silly.h1024h.activity

import android.content.Context
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_change_pwd.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.contract.ChangePwdContract
import silly.h1024h.persenter.ChangePwdPersenter
import silly.h1024h.utils.ToastUtil

/**
 * 修改密码
 */
class ChangePwdActivity : BaseMvpActivity<ChangePwdContract.Presenter>(), ChangePwdContract.View {

    override fun commitSuccess() {
       finish()
    }

    override fun setPersenter(): ChangePwdContract.Presenter {
        return ChangePwdPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_change_pwd
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
        // 忘记密码
        find_password.setOnClickListener {

        }
        // 提交
        commit.setOnClickListener {
            val oldPwd = old_pwd.text.trim().toString()
            if (oldPwd.isEmpty()) {
                ToastUtil.toast("旧密码不能为空")
                return@setOnClickListener
            }
            val newPwd = new_pwd.text.trim().toString()
            if (newPwd.isEmpty()) {
                ToastUtil.toast("新密码不能为空")
                return@setOnClickListener
            }
            mPersenter?.changePwd(oldPwd, newPwd)
        }
    }
}

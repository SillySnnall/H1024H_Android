package silly.h1024h.persenter

import silly.h1024h.base.entity.BaseResult
import silly.h1024h.contract.FindPwdContract
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class FindPwdPersenter(private val mView: FindPwdContract.View) : FindPwdContract.Presenter {
    override fun findPwd(account: String, email: String, code: String, newPwd: String) {
        HttpManager.post(Parameter.findPwd(account, email, code, newPwd), BaseResult::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
            mView.findSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }

    /**
     * 获取验证码
     */
    override fun getCode(account: String, email: String) {
        HttpManager.post(Parameter.getCode(account, email), BaseResult::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }
}
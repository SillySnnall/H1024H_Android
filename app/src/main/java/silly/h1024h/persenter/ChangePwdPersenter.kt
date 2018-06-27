package silly.h1024h.persenter

import silly.h1024h.base.entity.BaseResult
import silly.h1024h.common.SpCommon.ACCOUNT
import silly.h1024h.contract.ChangePwdContract
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil

class ChangePwdPersenter(private val mView: ChangePwdContract.View) : ChangePwdContract.Presenter {
    override fun changePwd(oldPwd: String, newPwd: String) {
        HttpManager.post(Parameter.changePwd(SpUtil.getString(ACCOUNT)!!, oldPwd, newPwd), BaseResult::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
            ToastUtil.toast(it.data.toString())
            mView.commitSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }
}
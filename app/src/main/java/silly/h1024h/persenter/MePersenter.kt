package silly.h1024h.persenter

import silly.h1024h.common.SpCommon.ACCOUNT
import silly.h1024h.common.SpCommon.TOKEN
import silly.h1024h.contract.MeContract
import silly.h1024h.entity.UserData
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil

class MePersenter(private val mView: MeContract.View) : MeContract.Presenter {
    override fun login(account: String, password: String) {
        HttpManager.post(Parameter.login(account, password), UserData::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                mView.fail()
                return@post
            }
            SpUtil.putString(ACCOUNT, it.data?.account!!)
            SpUtil.putString(TOKEN, it.data.token)
            mView.loginSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
            mView.fail()
        })
    }

}
package silly.h1024h.persenter

import org.greenrobot.eventbus.EventBus
import silly.h1024h.R.attr.type
import silly.h1024h.R.id.code
import silly.h1024h.base.entity.BaseResult
import silly.h1024h.common.Common
import silly.h1024h.common.SpCommon
import silly.h1024h.common.SpCommon.ACCOUNT
import silly.h1024h.common.SpCommon.EMAIL
import silly.h1024h.common.SpCommon.TOKEN
import silly.h1024h.contract.BindEmailContract
import silly.h1024h.contract.HomeContract
import silly.h1024h.contract.OpinionContract
import silly.h1024h.entity.*
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil

class BindEmailPersenter(private val mView: BindEmailContract.View) : BindEmailContract.Presenter {
    override fun sendCode(email: String) {
        HttpManager.post(Parameter.sendCode(SpUtil.getString(ACCOUNT)!!, email), BaseResult::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }

    override fun commitBind(email: String, code: String) {
        HttpManager.post(Parameter.bindEmail(SpUtil.getString(ACCOUNT)!!, email, code), BaseResult::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
            ToastUtil.toast(it.data.toString())
            getUser()
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }

    private fun getUser() {
        HttpManager.post(Parameter.getUser(SpUtil.getString(ACCOUNT)!!), UserData::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
            SpUtil.putString(ACCOUNT, it.data?.account!!)
            SpUtil.putString(TOKEN, it.data.token)
            SpUtil.putString(EMAIL, it.data.email)
            mView.commitSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }
}
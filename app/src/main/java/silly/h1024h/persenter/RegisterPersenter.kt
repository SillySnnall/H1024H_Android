package silly.h1024h.persenter

import org.greenrobot.eventbus.EventBus
import silly.h1024h.R.attr.type
import silly.h1024h.common.Common
import silly.h1024h.common.SpCommon.ACCOUNT
import silly.h1024h.common.SpCommon.EMAIL
import silly.h1024h.common.SpCommon.TOKEN
import silly.h1024h.contract.DetailsContract
import silly.h1024h.contract.RegisterContract
import silly.h1024h.entity.ImgResData
import silly.h1024h.entity.UserData
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil

class RegisterPersenter(private val mView: RegisterContract.View) : RegisterContract.Presenter {

    /**
     * 注册
     */
    override fun register(account: String, password: String) {
        HttpManager.post(Parameter.register(account, password), UserData::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                return@post
            }
            SpUtil.putString(ACCOUNT, it.data?.account!!)
            SpUtil.putString(TOKEN, it.data.token)
            SpUtil.putString(EMAIL, it.data.email)
            mView.registerSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }
}
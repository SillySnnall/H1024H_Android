package silly.h1024h.persenter

import org.greenrobot.eventbus.EventBus
import silly.h1024h.R.attr.type
import silly.h1024h.base.entity.BaseResult
import silly.h1024h.common.Common
import silly.h1024h.common.SpCommon.ACCOUNT
import silly.h1024h.contract.HomeContract
import silly.h1024h.contract.OpinionContract
import silly.h1024h.entity.*
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.SpUtil
import silly.h1024h.utils.ToastUtil

class OpinionPersenter(private val mView: OpinionContract.View) : OpinionContract.Presenter {
    override fun commitOpinion(content: String) {
        HttpManager.post(Parameter.opinion(SpUtil.getString(ACCOUNT)!!, content), BaseResult::class.java, success = {
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
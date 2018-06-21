package silly.h1024h.persenter

import silly.h1024h.base.entity.BaseResult
import silly.h1024h.contract.StartContract
import silly.h1024h.entity.ImgResData
import silly.h1024h.http.HttpConfig.MAIN_URL
import silly.h1024h.http.HttpConfig.URL_SERVICE_CODING
import silly.h1024h.http.HttpConfig.URL_TEST
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    override fun getServerType(): Int {
        return serverType
    }

    /**
     * 0 ->服务器失效
     * 1 ->马甲服务器
     * 2 ->正式服务器
     */
    private var serverType = 0


    override fun switchURL() {
        HttpManager.post(Parameter.getMianUrl(), BaseResult::class.java, success = {
            serverType = it?.data.toString().toInt()
            mView.initSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
            mView.initSuccess()
        })

    }
}
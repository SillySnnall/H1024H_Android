package silly.h1024h.persenter

import silly.h1024h.base.entity.BaseResult
import silly.h1024h.contract.StartContract
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.Init
import silly.h1024h.utils.ToastUtil
import silly.h1024h.utils.Util

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    override fun updateAPK(downloading: (Int?) -> Unit) {
        HttpManager.download("https://d.qiezzi.com/qiezi-clinic.apk",
                Init.ctx.getExternalFilesDir("apk").absolutePath,
                success = {},
                downloading = {
                    downloading(it!!)
                },
                fail = {})
    }

    override fun getServerType(): Int {
        return serverType
    }

    /**
     * 0 ->服务器失效
     * 1 ->马甲服务器
     * 2 ->正式服务器
     */
    private var serverType = 0


    override fun getURL() {
        HttpManager.post(Parameter.getMianUrl(Util.getVersionCode().toString(),""), BaseResult::class.java, success = {
            if (it?.msg != 0) return@post
            serverType = it.data.toString().toInt()
            mView.initSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
            mView.initSuccess()
        })
    }


}
package silly.h1024h.persenter

import com.meituan.android.walle.WalleChannelReader
import silly.h1024h.contract.StartContract
import silly.h1024h.entity.MainUrlData
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.Init
import silly.h1024h.utils.ToastUtil
import silly.h1024h.utils.Util

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    override fun updateAPK(url: String, downloading: (Int?) -> Unit, success: (String?) -> Unit) {
        HttpManager.download(url, Init.ctx.getExternalFilesDir("apk").absolutePath,
                success = {
                    success(it!!)
                },
                downloading = {
                    downloading(it!!)
                },
                fail = {
                    updateAPK(url, downloading, success)
                })
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
        HttpManager.post(Parameter.getMianUrl(Util.getVersionCode().toString(), WalleChannelReader.getChannel(Init.ctx)
                ?: "app"), MainUrlData::class.java, success = {
            if (it?.msg != 0) {
                ToastUtil.toast(it?.param!!)
                getURL()
                return@post
            }
            if (it.data?.apk_url?.isNotEmpty()!!) {
                mView.downloadAPK(it.data.apk_url)
                return@post
            }
            serverType = it.data.type
            mView.initSuccess()
        }, fail = {
            ToastUtil.toast(it!!)
            getURL()
        })
    }
}
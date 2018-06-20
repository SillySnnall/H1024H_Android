package silly.h1024h.persenter

import silly.h1024h.contract.StartContract
import silly.h1024h.http.HttpConfig.MAIN_URL
import silly.h1024h.http.HttpConfig.URL
import silly.h1024h.http.HttpConfig.URL_SERVICE_CODING
import silly.h1024h.http.HttpConfig.URL_TEST
import silly.h1024h.http.HttpManager
import silly.h1024h.utils.ToastUtil

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    override fun switchURL() {
//        HttpManager.get(MAIN_URL,
//                success = {
//                    URL_SERVICE_CODING = if ("true" == it?.trim()) URL else URL_TEST
//                    mView.initSuccess()
//                },
//                fail = {
//                    ToastUtil.toast(it!!)
//                })
        mView.initSuccess()
    }
}
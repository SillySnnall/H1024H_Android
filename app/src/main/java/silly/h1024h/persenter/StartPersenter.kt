package silly.h1024h.persenter

import android.util.Log
import silly.h1024h.common.CommonList.moreList
import silly.h1024h.contract.RecommendContract
import silly.h1024h.contract.StartContract
import silly.h1024h.entity.ImgResData
import silly.h1024h.entity.More
import silly.h1024h.http.HttpConfig.URL_SERVICE_CODING
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    override fun getMoreList() {
        HttpManager.get("$URL_SERVICE_CODING/cover_url.txt",
                success = {
                    val split = it?.split("\n")
                    for (str in split!!) {
                        val split1 = str.split("=")
                        moreList.add(More(split1[0], split1[1]))
                    }
                    mView.initSuccess()
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

}
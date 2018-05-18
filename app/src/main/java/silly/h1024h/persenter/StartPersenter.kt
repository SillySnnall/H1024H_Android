package silly.h1024h.persenter

import android.util.Log
import silly.h1024h.contract.RecommendContract
import silly.h1024h.contract.StartContract
import silly.h1024h.db.dao.MoreDao
import silly.h1024h.entity.ImgResData
import silly.h1024h.entity.More
import silly.h1024h.http.HttpConfig.URL_SERVICE_CODING
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.Init
import silly.h1024h.utils.ToastUtil

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    private val moreDao = MoreDao(Init.ctx)
    override fun getMoreList() {
        HttpManager.get("$URL_SERVICE_CODING/cover_url.txt",
                success = {
                    val split = it?.split("\n")
                    if (moreDao.queryForAll().size != split?.size) {
                        for (str in split!!) {
                            val split1 = str.split("=")
                            val more = More(split1[0], split1[1])
                            moreDao.createOrUpdate(more)
                        }
                    }
                    mView.initSuccess()
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

}
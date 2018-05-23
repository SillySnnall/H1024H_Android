package silly.h1024h.persenter

import silly.h1024h.common.Common.COVER_URL
import silly.h1024h.common.Common.TB_COVER_URL
import silly.h1024h.contract.StartContract
import silly.h1024h.db.dao.ResDataDao
import silly.h1024h.entity.ResData
import silly.h1024h.http.HttpConfig.MAIN_URL
import silly.h1024h.http.HttpConfig.URL
import silly.h1024h.http.HttpConfig.URL_SERVICE_CODING
import silly.h1024h.http.HttpConfig.URL_TEST
import silly.h1024h.http.HttpManager
import silly.h1024h.utils.ToastUtil

class StartPersenter(private val mView: StartContract.View) : StartContract.Presenter {
    override fun switchURL() {
        HttpManager.get(MAIN_URL,
                success = {
                    URL_SERVICE_CODING = if ("true" == it?.trim()) URL else URL_TEST
                    getMoreList()
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

    private val resDataDao = ResDataDao(TB_COVER_URL)
    private fun getMoreList() {
        HttpManager.get("$URL_SERVICE_CODING/$COVER_URL",
                success = {
                    val split = it?.split("\n")
                    if (resDataDao.queryForAll().size != split?.size) {
                        resDataDao.deleteAll()
                        for (str in split!!) {
                            if (str.isEmpty()) continue
                            val split1 = str.split("=")
                            val more = ResData(split1[0], split1[1])
                            resDataDao.createByFile(more)
                        }
                    }
                    mView.initSuccess()
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

}
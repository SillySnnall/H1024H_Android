package silly.h1024h.persenter

import silly.h1024h.common.Common
import silly.h1024h.common.Common.TB_COVER_URL
import silly.h1024h.contract.HomeContract
import silly.h1024h.db.dao.ResDataDao
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ImgResData
import silly.h1024h.entity.ResData
import silly.h1024h.http.HttpConfig.URL_SERVICE_CODING
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class HomePersenter(private val mView: HomeContract.View) : HomeContract.Presenter {

    private val resDataList = arrayListOf<ResData>()
    private var pageNum = 0
    private var itemCount = 20
    private var resDataDao = ResDataDao(TB_COVER_URL)

    init {
        val queryForAll = resDataDao.queryForAll()
        if (queryForAll.isNotEmpty() && Common.type_file.isEmpty()) {
            Common.type_file = queryForAll[0].file
        }
    }

    override fun setFile(file: String) {
        Common.type_file = file
    }

    override fun getList(): ArrayList<ResData> {
        return resDataList
    }

    override fun getResDataDao(): ResDataDao {
        return resDataDao
    }

    /**
     * 网路获取全部
     */
    override fun getCoverImg(isLoad: Int) {
        if (Common.type_file.isEmpty()) return
        val tabName = "tb_" + Common.type_file.replace("_url.txt", "")
        val coverDao = ResDataDao(tabName)
        if (coverDao.queryForAll().isNotEmpty()) {
            getCover(isLoad, coverDao)
        } else {
            HttpManager.get("$URL_SERVICE_CODING/url/${Common.type_file}",
                    success = {
                        val split = it?.split("\n")
                        if (coverDao.queryForAll().size != split?.size) {
                            coverDao.deleteAll()
                            for (str in split!!) {
                                if (str.isEmpty()) continue
                                val split1 = str.split("=")
                                val more = ResData(split1[0], Common.type_file, split1[2], split1[1])
                                coverDao.createByNetUrl(more)
                            }
                        }
                        getCover(isLoad, coverDao)
                    },
                    fail = {
                        ToastUtil.toast(it!!)
                    })
        }
    }

    override fun hotCount(irType: String) {
        HttpManager.post(Parameter.hotCount(irType), ImgResData::class.java, success = {}, fail = {})
    }

    /**
     * 封面分页获取
     */
    private fun getCover(isLoad: Int, coverDao: ResDataDao) {
        if (isLoad == 0) {
            pageNum = 0
            resDataList.clear()
        } else pageNum += itemCount
        val queryPaging = coverDao.queryPaging(pageNum, itemCount)
        resDataList.addAll(queryPaging)
        mView.refresh(isLoad)
    }
}
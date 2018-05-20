package silly.h1024h.persenter

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
    private var itemCount = 15
    private var file = "3agirl_url.txt"
    private var resDataDao = ResDataDao(TB_COVER_URL)

    init {
        val queryForAll = resDataDao.queryForAll()
        if (queryForAll.isNotEmpty()) {
            file = queryForAll[0].file
        }
    }

    override fun setFile(file: String) {
        this.file = file
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
        HttpManager.get("$URL_SERVICE_CODING/url/$file",
                success = {
                    val split = it?.split("\n")
                    val tabName = "tb_" + file.replace("_url.txt", "")
                    val coverDao = ResDataDao(tabName)
                    if (coverDao.queryForAll().size != split?.size) {
                        for (str in split!!) {
                            if (str.isEmpty()) continue
                            val split1 = str.split("=")
                            val more = ResData(split1[0], split1[1],split1[2])
                            coverDao.createByFile(more)
                        }
                    }
                    getCover(isLoad,coverDao)
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

    override fun hotCount(irType: String) {
        HttpManager.post(Parameter.hotCount(irType), ImgResData::class.java, success = {}, fail = {})
    }

    /**
     * 获取分页获取
     */
    private fun getCover(isLoad: Int,coverDao:ResDataDao) {
        if (isLoad == 0) {
            pageNum = 0
            resDataList.clear()
        } else pageNum += itemCount
        val queryPaging = coverDao.queryPaging(pageNum, itemCount)
        resDataList.addAll(queryPaging)
        mView.refresh(isLoad)
    }
}
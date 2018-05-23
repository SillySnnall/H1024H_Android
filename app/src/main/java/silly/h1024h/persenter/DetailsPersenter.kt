package silly.h1024h.persenter

import org.greenrobot.eventbus.EventBus
import silly.h1024h.R.id.refreshloadview
import silly.h1024h.common.Common
import silly.h1024h.contract.DetailsContract
import silly.h1024h.db.dao.ResDataDao
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ImgResData
import silly.h1024h.entity.ResData
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.http.HttpConfig
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class DetailsPersenter(private val mView: DetailsContract.View) : DetailsContract.Presenter {

    private var pageNum = 0
    private var itemCount = 20

    override fun getCoverImgDetailed(isLoad: Int, file: String) {
        val tabName = "tb_" + file.replace(".txt", "").replace("/","")
        val detailedDao = ResDataDao(tabName)
        if(detailedDao.queryForAll().isNotEmpty()){
            getDetailed(isLoad, detailedDao)
        }else{
            HttpManager.get("${HttpConfig.URL_SERVICE_CODING}/img/$file",
                    success = {
                        val split = it?.split("\n")
                        if (detailedDao.queryForAll().size != split?.size) {
                            detailedDao.deleteAll()
                            for (str in split!!) {
                                if (str.isEmpty()) continue
                                val split1 = str.split("=")
                                val more = ResData(split1[0], "", split1[1])
                                detailedDao.createByImgUrl(more)
                            }
                        }
                        getDetailed(isLoad, detailedDao)
                    },
                    fail = {
                        ToastUtil.toast(it!!)
                    })
        }
    }

    /**
     * 具体图片分页获取
     */
    private fun getDetailed(isLoad: Int, detailedDao: ResDataDao) {
        if (isLoad == 0) {
            pageNum = 0
            Common.imgResList.clear()
        } else pageNum += itemCount
        val queryPaging = detailedDao.queryPaging(pageNum, itemCount)
        Common.imgResList.addAll(queryPaging)
        EventBus.getDefault().post(EventBusMessage(EventBusConstant.REFRESH_VIEWPAGER))
        mView.refresh(isLoad)
    }


}
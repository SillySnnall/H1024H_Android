package silly.h1024h.persenter

import org.greenrobot.eventbus.EventBus
import silly.h1024h.common.Common
import silly.h1024h.contract.DetailsContract
import silly.h1024h.entity.ImgResData
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class DetailsPersenter(private val mView: DetailsContract.View) : DetailsContract.Presenter {

    private var pageNum = 0
    private var itemCount = 20

    /**
     * 网路获取详细图片
     */
    override fun getCoverImgDetailed(isLoad: Int, table: String,type: String) {
        if (table.isEmpty()) return
        if (isLoad == 0) {
            pageNum = 0
            Common.imgResList.clear()
        } else pageNum += itemCount
        HttpManager.post(Parameter.getCoverImgDetailed(table, type, pageNum, itemCount), ImgResData::class.java, success = {
            Common.imgResList.addAll(it?.data!!)
            EventBus.getDefault().post(EventBusMessage(EventBusConstant.REFRESH_VIEWPAGER))
            mView.refresh(isLoad)
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }
}
package silly.h1024h.persenter

import org.greenrobot.eventbus.EventBus
import silly.h1024h.R.id.refreshloadview
import silly.h1024h.common.Common
import silly.h1024h.contract.DetailsContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ImgResData
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class DetailsPersenter(private val mView: DetailsContract.View) : DetailsContract.Presenter {

    private var pageNum = 0
    private var itemCount = 15

    override fun getCoverImgDetailed(isLoad: Int, irType: Int) {
        if (isLoad == 0) pageNum = 0 else pageNum += itemCount
        HttpManager.post(Parameter.getCoverImgDetailed(irType, pageNum, itemCount), ImgResData::class.java,
                success = {
                    if (it?.msg == 0) {
                        if (isLoad == 0) {
                            Common.imgResList.clear()
                        }
                        for (imgRes in it.data!!) {
                            Common.imgResList.add(imgRes)
                        }
                        EventBus.getDefault().post(EventBusMessage(EventBusConstant.REFRESH_VIEWPAGER))
                        mView.refresh(isLoad)
                    } else ToastUtil.toast(it?.param!!)
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

}
package silly.h1024h.persenter

import silly.h1024h.common.Common
import silly.h1024h.contract.RecommendContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ImgResData
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class RecommendPersenter(private val mView: RecommendContract.View) : RecommendContract.Presenter {
    /**
     * 热门计数
     */
    override fun hotCount(type: String) {
        HttpManager.post(Parameter.hotCount(type), ImgResData::class.java, success = {}, fail = {})
    }

    override fun getList(): List<ImgRes> {
        return resDataList
    }

    private val resDataList = arrayListOf<ImgRes>()
    private var pageNum = 0
    private var itemCount = 3

    /**
     * 网路获取封面
     */
    override fun getHot(isLoad: Int) {
        if (Common.table.isEmpty()) return
        if (isLoad == 0) {
            pageNum = 0
            resDataList.clear()
        } else pageNum += itemCount
        HttpManager.post(Parameter.getHotImg(pageNum, itemCount), ImgResData::class.java, success = {
            resDataList.addAll(it?.data!!)
            mView.refresh(isLoad)
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }
}
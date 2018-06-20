package silly.h1024h.persenter

import silly.h1024h.common.Common
import silly.h1024h.contract.HomeContract
import silly.h1024h.entity.*
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class HomePersenter(private val mView: HomeContract.View) : HomeContract.Presenter {


    private val resDataList = arrayListOf<ImgRes>()
    private val typeList = arrayListOf<Type>()
    private var pageNum = 0
    private var itemCount = 20

    init {

    }

    var onlyOne = true
    /**
     * 获取分类列表
     */
    override fun getTypeListData() {
        if (onlyOne) {
            onlyOne = false
            if (typeList.isEmpty()) {
                HttpManager.post(Parameter.getTypeList(), TypeList::class.java, success = {
                    typeList.clear()
                    typeList.addAll(it?.data!!)
                    Common.table = typeList[0].type
                    mView.showList()
                    onlyOne = true
                }, fail = {
                    ToastUtil.toast(it!!)
                    onlyOne = true
                })
            } else {
                mView.showList()
                onlyOne = true
            }
        }
    }

    override fun setType(type: String) {
        Common.table = type
    }

    override fun getList(): List<ImgRes> {
        return resDataList
    }

    override fun getTypeList(): List<Type> {
        return typeList
    }

    /**
     * 网路获取封面
     */
    override fun getCoverImg(isLoad: Int) {
        if (Common.table.isEmpty()) return
        if (isLoad == 0) {
            pageNum = 0
            resDataList.clear()
        } else pageNum += itemCount
        HttpManager.post(Parameter.getCoverImg(Common.table, pageNum, itemCount), ImgResData::class.java, success = {
            resDataList.addAll(it?.data!!)
            mView.refresh(isLoad)
        }, fail = {
            ToastUtil.toast(it!!)
        })
    }

    /**
     * 热门计数
     */
    override fun hotCount(type: String) {
        HttpManager.post(Parameter.hotCount(type), ImgResData::class.java, success = {}, fail = {})
    }
}
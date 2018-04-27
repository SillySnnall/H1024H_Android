package silly.h1024h.persenter

import kotlinx.android.synthetic.main.activity_main.*
import silly.h1024h.R.id.refreshloadview
import silly.h1024h.contract.DetailsContract
import silly.h1024h.contract.MainContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ImgResData
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class DetailsPersenter(private val mView: DetailsContract.View) : DetailsContract.Presenter {

    private val imgList = arrayListOf<ImgRes>()
    private val imgUrlList = arrayListOf<String>()
    private var pageNum = 0
    private var itemCount = 15

    override fun getList(): ArrayList<ImgRes> {
        return imgList
    }

    override fun getUrlList(): List<String> {
        return imgUrlList
    }

    override fun getCoverImgDetailed(isLoad: Int, irType: Int) {
        if (isLoad == 0) pageNum = 0 else pageNum += itemCount
        HttpManager.post(Parameter.getCoverImgDetailed(irType, pageNum, itemCount), ImgResData::class.java,
                success = {
                    if (it?.msg == 0) {
                        if (isLoad == 0) {
                            imgList.clear()
                            imgUrlList.clear()
                        }
                        for (imgRes in it.data!!) {
                            imgList.add(imgRes)
                            imgUrlList.add(imgRes.irUrl)
                        }
                        mView.refresh(isLoad)
                    } else ToastUtil.toast(it?.param!!)
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

}
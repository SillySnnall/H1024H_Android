package silly.h1024h.persenter

import kotlinx.android.synthetic.main.activity_main.*
import silly.h1024h.R.id.refreshloadview
import silly.h1024h.contract.MainContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ImgResData
import silly.h1024h.http.HttpManager
import silly.h1024h.parameters.Parameter
import silly.h1024h.utils.ToastUtil

class MainPersenter(private val mView: MainContract.View) : MainContract.Presenter {

    private val imgList = arrayListOf<ImgRes>()
    private var pageNum = 0
    private var itemCount = 15

    override fun getList(): ArrayList<ImgRes> {
        return imgList
    }

    override fun getCoverImg(isLoad: Int) {
        if (isLoad == 0) pageNum = 0 else pageNum += itemCount
        HttpManager.post(Parameter.getCoverImg(pageNum, itemCount), ImgResData::class.java,
                success = {
                    if (it?.msg == 0) {
                        if (isLoad == 0) imgList.clear()
                        imgList.addAll(it.data!!)
                        mView.refresh(isLoad)
                    } else ToastUtil.toast(it?.param!!)
                },
                fail = {
                    ToastUtil.toast(it!!)
                })
    }

}
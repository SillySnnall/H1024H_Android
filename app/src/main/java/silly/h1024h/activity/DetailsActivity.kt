package silly.h1024h.activity

import android.content.Intent
import android.view.View
import com.google.gson.Gson
import silly.h1024h.R
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.common.IntentName
import silly.h1024h.common.IntentName.IR_TYPE
import silly.h1024h.contract.DetailsContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.persenter.DetailsPersenter
import silly.h1024h.utils.LogUtil
import silly.h1024h.view.RefreshLoadView
import silly.h1024h.view.image.ImageActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseMvpActivity<DetailsContract.Presenter>(), DetailsContract.View {

    private var irType = 0

    override fun refresh(isLoad: Int) {
        if (isLoad == 0) refreshloadview.refreshComplete()
        else refreshloadview.loadComplete()
    }

    override fun setPersenter(): DetailsContract.Presenter {
        return DetailsPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_details
    }

    override fun initView() {
        irType = intent.getStringExtra(IR_TYPE).toInt()
    }

    override fun initData() {
        refreshloadview.init(recylerview, RecyclerAdapter(mPersenter?.getList()!!), 2, RefreshLoadView.LAYOUTMANAGER_VERTICAL, true)
        mPersenter?.getCoverImgDetailed(0, irType)
    }

    override fun initEvent() {
        refreshloadview.setOnRefreshListener {
            mPersenter?.getCoverImgDetailed(0, irType)
        }

        refreshloadview.addOnLoadListener {
            mPersenter?.getCoverImgDetailed(1, irType)

        }
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ImgRes> {
            override fun onItemClick(view: View?, data: ImgRes?, position: Int) {
                val toJson = Gson().toJson(mPersenter?.getList()!!)
                LogUtil.e(toJson)
                startActivity(Intent(this@DetailsActivity, ImageActivity::class.java)
                        .putExtra(IntentName.IMG_LIST, Gson().toJson(mPersenter?.getUrlList()!!))
                        .putExtra(IntentName.IMG_LIST_POSITION, position))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        refreshloadview.removeOnLoadListener()
    }

}
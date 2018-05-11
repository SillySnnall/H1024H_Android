package silly.h1024h.activity

import android.content.Intent
import android.view.View
import silly.h1024h.R
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.common.IntentName
import silly.h1024h.common.IntentName.IR_TYPE
import silly.h1024h.contract.DetailsContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.persenter.DetailsPersenter
import kotlinx.android.synthetic.main.activity_details.*
import silly.h1024h.common.Common
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import silly.h1024h.eventbus.EventBusConstant.GET_COVERIMG_DETAILED
import silly.h1024h.eventbus.EventBusMessage
import java.util.concurrent.Executors


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
        EventBus.getDefault().register(this)
        irType = intent.getStringExtra(IR_TYPE).toInt()
    }

    override fun initData() {
        refreshloadview.init(recylerview, RecyclerAdapter(Common.imgResList), 2)
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
                startActivity(Intent(this@DetailsActivity, ImageActivity::class.java)
                        .putExtra(IntentName.IMG_LIST_POSITION, position))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        refreshloadview.removeOnLoadListener()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (GET_COVERIMG_DETAILED == event.type) {
            mPersenter?.getCoverImgDetailed(1, irType)
        }
    }
}
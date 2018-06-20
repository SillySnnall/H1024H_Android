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
import silly.h1024h.persenter.DetailsPersenter
import kotlinx.android.synthetic.main.activity_details.*
import silly.h1024h.common.Common
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import silly.h1024h.entity.ImgRes
import silly.h1024h.eventbus.EventBusConstant.GET_COVERIMG_DETAILED
import silly.h1024h.eventbus.EventBusMessage


class DetailsActivity : BaseMvpActivity<DetailsContract.Presenter>(), DetailsContract.View {

    private var type = ""

    override fun refresh(isLoad: Int) {
        if (isLoad == 0) refreshloadview.refreshComplete()
        else refreshloadview.loadComplete()
        loading.visibility = View.GONE
    }

    override fun setPersenter(): DetailsContract.Presenter {
        return DetailsPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_details
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        type = intent.getStringExtra(IR_TYPE)
    }

    override fun initData() {
        loading.visibility = View.VISIBLE
        refreshloadview.init(recylerview, RecyclerAdapter(Common.imgResList), 2)
        mPersenter?.getCoverImgDetailed(0, type)
    }

    override fun initEvent() {
        pre.setOnClickListener {
            finish()
        }

        refreshloadview.setOnRefreshListener {
            mPersenter?.getCoverImgDetailed(0, type)
        }

        refreshloadview.addOnLoadListener {
            mPersenter?.getCoverImgDetailed(1, type)

        }
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ImgRes> {
            override fun onItemClick(view: View?, data: ImgRes?, position: Int) {
                startActivity(Intent(this@DetailsActivity, ImageActivity::class.java)
                        .putExtra(IntentName.IMG_LIST_POSITION, position))
            }
        })
        loading.setOnClickListener {
            // 拦截点击事件,加载时不可点击
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        refreshloadview.removeOnLoadListener()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (GET_COVERIMG_DETAILED == event.type) {
            mPersenter?.getCoverImgDetailed(1, type)
        }
    }
}
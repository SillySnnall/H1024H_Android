package silly.h1024h.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import silly.h1024h.R
import silly.h1024h.activity.DetailsActivity
import silly.h1024h.adapter.MoreAdapter
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.common.IntentName
import silly.h1024h.contract.HomeContract
import silly.h1024h.db.dao.ResDataDao
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ResData
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.persenter.HomePersenter
import silly.h1024h.view.SillyDialog

class HomeFragment : BaseMvpFragment<HomeContract.Presenter>(), HomeContract.View {

    private lateinit var moreDialog: SillyDialog

    override fun setPersenter(): HomeContract.Presenter {
        return HomePersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun initData() {
        loading.visibility = View.VISIBLE
        initMoreListView()
        refreshloadview.init(recylerview, RecyclerAdapter(mPersenter?.getList()!!), 2)
        mPersenter?.getCoverImg(0)
    }

    override fun initEvent() {
        refreshloadview.setOnRefreshListener {
            mPersenter?.getCoverImg(0)
        }

        refreshloadview.addOnLoadListener {
            mPersenter?.getCoverImg(1)

        }
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ResData> {
            override fun onItemClick(view: View?, data: ResData?, position: Int) {
//                mPersenter?.hotCount(data?.img_url!!)
                var replace = data?.net_url?.replace(".html", "")
                replace = "${data?.file?.replace("_url.txt", "")}/${replace?.substring(replace.lastIndexOf("/") + 1, replace.length)}.txt"
                startActivity(Intent(context, DetailsActivity::class.java).putExtra(IntentName.IR_TYPE, replace))
            }
        })
        loading.setOnClickListener {
            // 拦截点击事件,加载时不可点击
        }
    }

    override fun refresh(isLoad: Int) {
        if (isLoad == 0) refreshloadview.refreshComplete()
        else refreshloadview.loadComplete()
        loading.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        refreshloadview.removeOnLoadListener()
        moreDialog.dismissCancel()
        EventBus.getDefault().unregister(this)
    }


    private fun initMoreListView() {
        moreDialog = SillyDialog(activity).loadLayout(R.layout.dialog_more).setGCCanceledOnTouchOutside(true)
        val dialog_recyclerview = moreDialog.getView<RecyclerView>(R.id.dialog_recyclerview)
        dialog_recyclerview.layoutManager = LinearLayoutManager(activity)
        val moreAdapter = MoreAdapter(mPersenter?.getResDataDao()?.queryForAll()!!)
        dialog_recyclerview.adapter = moreAdapter
        moreDialog.setCanceledListener {
            moreDialog.dismiss()
        }

        moreAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ResData> {
            override fun onItemClick(view: View?, data: ResData?, position: Int) {
                moreDialog.dismiss()
                mPersenter?.setFile(data?.file!!)
                loading.visibility = View.VISIBLE
                mPersenter?.getCoverImg(0)
            }
        })
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (EventBusConstant.MORE_DIALOG_SHOW == event.type) {
            moreDialog.show()
        }
    }
}

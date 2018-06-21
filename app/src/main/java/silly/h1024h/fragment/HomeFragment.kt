package silly.h1024h.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.Type
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.persenter.HomePersenter
import silly.h1024h.view.SillyDialog

class HomeFragment : BaseMvpFragment<HomeContract.Presenter>(), HomeContract.View {
    override fun showList() {
        moreAdapter.notifyDataSetChanged()
        mPersenter?.getCoverImg(0)
    }

    private lateinit var moreDialog: SillyDialog
    private lateinit var moreAdapter: MoreAdapter

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
        mPersenter?.getTypeListData()
        initMoreListView()
        refreshloadview.init(recylerview, RecyclerAdapter(mPersenter?.getList()!!), 2)
    }

    override fun initEvent() {
        refreshloadview.setOnRefreshListener {
            mPersenter?.getCoverImg(0)
        }

        refreshloadview.addOnLoadListener {
            mPersenter?.getCoverImg(1)

        }
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ImgRes> {
            override fun onItemClick(view: View?, data: ImgRes?, position: Int) {
                mPersenter?.hotCount(data?.type!!)
                startActivity(Intent(context, DetailsActivity::class.java).putExtra(IntentName.IR_TYPE, data?.type)
                        .putExtra(IntentName.IR_TABLE, mPersenter?.getTable()))
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
        moreDialog = SillyDialog(activity!!, R.style.list_dialog).loadLayout(R.layout.dialog_more).setGCCanceledOnTouchOutside(true)
        val dialog_recyclerview = moreDialog.getView<RecyclerView>(R.id.dialog_recyclerview)
        dialog_recyclerview.layoutManager = LinearLayoutManager(activity)
        moreAdapter = MoreAdapter(mPersenter?.getTypeList()!!)
        dialog_recyclerview.adapter = moreAdapter
        moreDialog.setCanceledListener {
            moreDialog.dismiss()
        }

        moreAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Type> {
            override fun onItemClick(view: View?, data: Type?, position: Int) {
                moreDialog.dismiss()
                mPersenter?.setTable(data?.type!!)
                loading.visibility = View.VISIBLE
                mPersenter?.getCoverImg(0)
            }
        })
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (EventBusConstant.MORE_DIALOG_SHOW == event.type) {
            if (mPersenter?.getTypeList()?.isEmpty()!!) mPersenter?.getTypeListData()
            else moreDialog.show()
        }
    }
}

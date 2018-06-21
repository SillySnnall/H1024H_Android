package silly.h1024h.fragment

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.fragment_recommend.*
import silly.h1024h.R
import silly.h1024h.R.attr.type
import silly.h1024h.activity.DetailsActivity
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.common.Common
import silly.h1024h.common.IntentName
import silly.h1024h.contract.RecommendContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.persenter.RecommendPersenter

class RecommendFragment : BaseMvpFragment<RecommendContract.Presenter>(), RecommendContract.View {

    override fun setPersenter(): RecommendContract.Presenter {
        return RecommendPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_recommend
    }

    override fun initView() {

    }

    override fun initData() {
        loading.visibility = View.VISIBLE
        mPersenter?.getHot(0)
        refreshloadview.init(recylerview, RecyclerAdapter(mPersenter?.getList()!!), 2)
    }

    override fun initEvent() {
        refreshloadview.setOnRefreshListener {
            mPersenter?.getHot(0)
        }

        refreshloadview.addOnLoadListener {
            mPersenter?.getHot(1)

        }
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ImgRes> {
            override fun onItemClick(view: View?, data: ImgRes?, position: Int) {
                mPersenter?.hotCount(data?.type!!)
                startActivity(Intent(context, DetailsActivity::class.java).putExtra(IntentName.IR_TYPE, data?.type)
                        .putExtra(IntentName.IR_TABLE, data?.table_name))
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
    }
}

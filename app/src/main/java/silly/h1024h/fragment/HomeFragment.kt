package silly.h1024h.fragment

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.fragment_home.*
import silly.h1024h.R
import silly.h1024h.activity.DetailsActivity
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.common.IntentName
import silly.h1024h.contract.HomeContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.persenter.HomePersenter

class HomeFragment : BaseMvpFragment<HomeContract.Presenter>(), HomeContract.View {

    override fun setPersenter(): HomeContract.Presenter {
        return HomePersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

    }

    override fun initData() {
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
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ImgRes> {
            override fun onItemClick(view: View?, data: ImgRes?, position: Int) {
                mPersenter?.hotCount(data?.irType!!)
                startActivity(Intent(context, DetailsActivity::class.java).putExtra(IntentName.IR_TYPE, data?.irType))
            }
        })
    }

    override fun refresh(isLoad: Int) {
        if (isLoad == 0) refreshloadview.refreshComplete()
        else refreshloadview.loadComplete()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        refreshloadview.removeOnLoadListener()
    }
}

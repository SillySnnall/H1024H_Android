package silly.h1024h.fragment

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.fragment_home.*
import silly.h1024h.R
import silly.h1024h.R.id.recylerview
import silly.h1024h.activity.DetailsActivity
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.common.IntentName
import silly.h1024h.contract.HomeContract
import silly.h1024h.contract.MeContract
import silly.h1024h.contract.RecommendContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.persenter.HomePersenter
import silly.h1024h.persenter.MePersenter
import silly.h1024h.persenter.RecommendPersenter
import silly.h1024h.view.RefreshLoadView

class MeFragment : BaseMvpFragment<MeContract.Presenter>(), MeContract.View {

    override fun setPersenter(): MeContract.Presenter {
        return MePersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}

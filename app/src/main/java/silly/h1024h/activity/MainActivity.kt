package silly.h1024h.activity

import android.content.Intent
import android.view.View
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import silly.h1024h.adapter.RecyclerAdapter
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.common.IntentName.IR_TYPE
import silly.h1024h.contract.MainContract
import silly.h1024h.entity.ImgRes
import silly.h1024h.persenter.MainPersenter
import silly.h1024h.view.RefreshLoadView.LAYOUTMANAGER_VERTICAL


class MainActivity : BaseMvpActivity<MainContract.Presenter>(), MainContract.View {

    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun setPersenter(): MainContract.Presenter {
        return MainPersenter(this)
    }


    override fun initView() {

    }

    override fun initData() {
        refreshloadview.init(recylerview, RecyclerAdapter(mPersenter?.getList()!!), 2, LAYOUTMANAGER_VERTICAL, true)
        mPersenter?.getCoverImg(0)
    }

    override fun initEvent() {
        refreshloadview.setOnRefreshListener {
            mPersenter?.getCoverImg(0)
        }

        refreshloadview.addOnLoadListener {
            mPersenter?.getCoverImg(1)

        }
        refreshloadview.getAdapter<RecyclerAdapter>().setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ImgRes>{
            override fun onItemClick(view: View?, data: ImgRes?, position: Int) {
                startActivity(Intent(this@MainActivity,DetailsActivity::class.java).putExtra(IR_TYPE,data?.irType))
            }
        })
    }



    override fun refresh(isLoad:Int) {
        if(isLoad == 0) refreshloadview.refreshComplete()
        else refreshloadview.loadComplete()
    }

    override fun onDestroy() {
        super.onDestroy()
        refreshloadview.removeOnLoadListener()
    }
}

package silly.h1024h.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import silly.h1024h.R
import kotlinx.android.synthetic.main.layout_bottom.*
import kotlinx.android.synthetic.main.layout_top.*
import silly.h1024h.adapter.MoreAdapter
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.common.CommonList.moreList
import silly.h1024h.fragment.HomeFragment
import silly.h1024h.fragment.MeFragment
import silly.h1024h.fragment.RecommendFragment
import silly.h1024h.view.SillyDialog


class MainActivity : BaseActivity() {

    override fun onDestroy() {
        super.onDestroy()
        moreDialog.dismissCancel()
    }

    private lateinit var moreDialog: SillyDialog

    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        selectBottom(0)
        selectFragment(0)
        setTop(0)
    }

    override fun initData() {
        initMoreListView()
    }

    override fun initEvent() {
        bottom0.setOnClickListener {
            if (bottom0.isSelected) return@setOnClickListener
            selectBottom(0)
            selectFragment(0)
            setTop(0)
        }
        bottom1.setOnClickListener {
            if (bottom1.isSelected) return@setOnClickListener
            selectBottom(1)
            selectFragment(1)
            setTop(1)
        }
        bottom2.setOnClickListener {
            if (bottom2.isSelected) return@setOnClickListener
            selectBottom(2)
            selectFragment(2)
            setTop(2)
        }
        top_more.setOnClickListener {
            moreDialog.show()
        }
    }

    private fun selectBottom(bottomIndex: Int) {
        bottom0.isSelected = false
        bottom1.isSelected = false
        bottom2.isSelected = false
        when (bottomIndex) {
            0 -> bottom0.isSelected = true
            1 -> bottom1.isSelected = true
            2 -> bottom2.isSelected = true
        }
    }

    private fun selectFragment(fragmentIndex: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        when (fragmentIndex) {
            0 -> beginTransaction.replace(R.id.frameLayout, RecommendFragment())
            1 -> beginTransaction.replace(R.id.frameLayout, HomeFragment())
            2 -> beginTransaction.replace(R.id.frameLayout, MeFragment())
        }
        beginTransaction.commit()
    }

    private fun setTop(bottomIndex: Int) {
        when (bottomIndex) {
            0 -> {
                top_title.text = "推荐"
                top_more.visibility = View.GONE
            }
            1 -> {
                top_title.text = "首页"
                top_more.visibility = View.VISIBLE
            }
            2 -> {
                top_title.text = "我的"
                top_more.visibility = View.GONE
            }
        }
    }

    private fun initMoreListView() {
        moreDialog = SillyDialog(this).loadLayout(R.layout.dialog_more).setGCCanceledOnTouchOutside(true)
        val dialog_recyclerview = moreDialog.getView<RecyclerView>(R.id.dialog_recyclerview)
        dialog_recyclerview.layoutManager = LinearLayoutManager(this)
        val moreAdapter = MoreAdapter(moreList)
        dialog_recyclerview.adapter = moreAdapter
        moreDialog.setCanceledListener {
            moreDialog.dismiss()
        }
        moreAdapter.setOnItemClickListener { view, data, position ->
            moreDialog.dismiss()
//            ---------------------
        }
    }
}

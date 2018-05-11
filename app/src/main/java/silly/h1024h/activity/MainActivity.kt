package silly.h1024h.activity

import silly.h1024h.R
import kotlinx.android.synthetic.main.layout_bottom.*
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.fragment.HomeFragment
import silly.h1024h.fragment.MeFragment
import silly.h1024h.fragment.RecommendFragment


class MainActivity : BaseActivity() {

    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        selectBottom(0)
        selectFragment(0)
    }

    override fun initData() {

    }

    override fun initEvent() {
        bottom0.setOnClickListener {
            if (bottom0.isSelected) return@setOnClickListener
            selectBottom(0)
            selectFragment(0)
        }
        bottom1.setOnClickListener {
            if (bottom1.isSelected) return@setOnClickListener
            selectBottom(1)
            selectFragment(1)
        }
        bottom2.setOnClickListener {
            if (bottom2.isSelected) return@setOnClickListener
            selectBottom(2)
            selectFragment(2)
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

    private fun selectFragment(fragmentIndex:Int){
        val beginTransaction = supportFragmentManager.beginTransaction()
        when (fragmentIndex) {
            0 -> beginTransaction.replace(R.id.frameLayout, RecommendFragment())
            1 -> beginTransaction.replace(R.id.frameLayout, HomeFragment())
            2 -> beginTransaction.replace(R.id.frameLayout, MeFragment())
        }
        beginTransaction.commit()
    }
}

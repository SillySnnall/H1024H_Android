package silly.h1024h.activity

import android.annotation.SuppressLint
import android.view.View
import com.tencent.bugly.crashreport.CrashReport
import silly.h1024h.R
import kotlinx.android.synthetic.main.layout_bottom.*
import kotlinx.android.synthetic.main.layout_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.eventbus.EventBusConstant
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.fragment.HomeFragment
import silly.h1024h.fragment.MeFragment
import silly.h1024h.fragment.RecommendFragment
import silly.h1024h.utils.Util


class MainActivity : BaseActivity() {

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    override fun setLayoutView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        EventBus.getDefault().register(this)
        initFragment()
        selectBottom(0)
        setTop(0)
    }


    override fun initData() {

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
            EventBus.getDefault().post(EventBusMessage(EventBusConstant.LOGIN))
        }
        top_more.setOnClickListener {
            EventBus.getDefault().post(EventBusMessage(EventBusConstant.MORE_DIALOG_SHOW))
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

    private var recommendFragment: RecommendFragment? = null
    private var homeFragment: HomeFragment? = null
    private var meFragment: MeFragment? = null
    @SuppressLint("CommitTransaction")
    private fun initFragment() {
        if (recommendFragment == null) recommendFragment = RecommendFragment()
        if (homeFragment == null) homeFragment = HomeFragment()
        if (meFragment == null) meFragment = MeFragment()
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, recommendFragment)
                .add(R.id.frameLayout, homeFragment).add(R.id.frameLayout, meFragment)
                .hide(homeFragment).hide(meFragment).show(recommendFragment).commit()
    }

    private fun selectFragment(fragmentIndex: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
                .hide(recommendFragment).hide(homeFragment).hide(meFragment)
        when (fragmentIndex) {
            0 -> beginTransaction.show(recommendFragment)
            1 -> beginTransaction.show(homeFragment)
            2 -> beginTransaction.show(meFragment)
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
                top_title.text = if (Util.isLogin()) "我的" else "登录"
                top_more.visibility = View.GONE
            }
        }
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (EventBusConstant.LOGIN == event.type) {
            top_title.text = if (Util.isLogin()) "我的" else "登录"
        }
    }

}

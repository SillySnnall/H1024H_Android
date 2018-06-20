package silly.h1024h.activity

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_image.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import silly.h1024h.R
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.common.Common
import silly.h1024h.common.IntentName.IMG_LIST_POSITION
import silly.h1024h.entity.ImgRes
import silly.h1024h.eventbus.EventBusConstant.GET_COVERIMG_DETAILED
import silly.h1024h.eventbus.EventBusConstant.REFRESH_VIEWPAGER
import silly.h1024h.eventbus.EventBusMessage
import silly.h1024h.fragment.ImageFragment


class ImageActivity : BaseActivity() {


    override fun setLayoutView(): Int {
        return R.layout.activity_image
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun initData() {
        viewpager.adapter = ImagePagerAdapter(supportFragmentManager, Common.imgResList)
        val imgListPosition = intent.getIntExtra(IMG_LIST_POSITION, 0)
        viewpager.currentItem = imgListPosition
    }

    override fun initEvent() {
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                // viewpager最后一页，加载数据
                if (Common.imgResList.size - 1 == position) {
                    EventBus.getDefault().post(EventBusMessage(GET_COVERIMG_DETAILED))
                }
            }
        })
    }

    private inner class ImagePagerAdapter(fm: FragmentManager, var imaList: List<ImgRes>?) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return if (imaList == null) 0 else imaList!!.size
        }

        override fun getItem(position: Int): android.support.v4.app.Fragment {
            return ImageFragment.newInstance(position)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe// 需要加这个注解，否则会报错
    fun onEventMainThread(event: EventBusMessage) {
        if (REFRESH_VIEWPAGER == event.type) {
            viewpager.adapter?.notifyDataSetChanged()
        }
    }
}

package silly.h1024h.view.image

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_image.*

import silly.h1024h.R
import silly.h1024h.base.activity.BaseActivity
import silly.h1024h.common.IntentName.IMG_LIST
import silly.h1024h.common.IntentName.IMG_LIST_POSITION


class ImageActivity : BaseActivity() {


    override fun setLayoutView(): Int {
        return R.layout.activity_image
    }

    override fun initView() {

    }

    override fun initData() {
        val imaList = Gson().fromJson<List<String>>(intent.getStringExtra(IMG_LIST), object : TypeToken<List<String>>() {}.type)
        viewpager.adapter = ImagePagerAdapter(supportFragmentManager, imaList)
        val imgListPosition = intent.getIntExtra(IMG_LIST_POSITION,0)
        viewpager.currentItem = imgListPosition
    }

    override fun initEvent() {

    }

    private inner class ImagePagerAdapter(fm: FragmentManager, var imaList: List<String>?) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return if (imaList == null) 0 else imaList!!.size
        }

        override fun getItem(position: Int): android.support.v4.app.Fragment {
            return ImageFragment.newInstance(imaList?.get(position)!!)
        }

    }
}

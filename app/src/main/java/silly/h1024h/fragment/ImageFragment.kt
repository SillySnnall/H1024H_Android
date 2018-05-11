package silly.h1024h.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_image.*
import silly.h1024h.R
import silly.h1024h.common.Common
import silly.h1024h.http.HttpConfig

class ImageFragment : Fragment() {
    private var mPosition: Int = 0
    private fun ImageFragment() {

    }

    companion object {
        fun newInstance(position: Int): ImageFragment {
            val imageFragment = ImageFragment()
            imageFragment.mPosition = position
            return imageFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(context, R.layout.fragment_image, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context).load(HttpConfig.URL_SERVICE + Common.imgResList[mPosition].irUrl).into(image)
    }
}
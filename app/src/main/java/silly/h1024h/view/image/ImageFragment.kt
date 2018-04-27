package silly.h1024h.view.image

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_image.*
import silly.h1024h.R
import silly.h1024h.http.HttpConfig
import silly.h1024h.utils.Init

/**
 * Created by SillySnnall on 2018/4/26.
 */
class ImageFragment : Fragment() {
    private var mImgUrl: String? = null
    private fun ImageFragment() {

    }

    companion object {
        fun newInstance(imgUrl: String): ImageFragment {
            val imageFragment = ImageFragment()
            imageFragment.mImgUrl = imgUrl
            return imageFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(context, R.layout.fragment_image, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(Init.ctx).load(HttpConfig.URL_SERVICE + mImgUrl).into(image)
    }
}
package silly.h1024h.fragment

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import silly.h1024h.R
import silly.h1024h.common.Common

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
        val image = view.findViewById<ImageView>(R.id.image)
        val loading = view.findViewById<ProgressBar>(R.id.loading)
        val uri = Uri.parse(Common.imgResList[mPosition].url)
        Glide.with(context).load(uri).into(object : GlideDrawableImageViewTarget(image) {
            override fun onResourceReady(resource: GlideDrawable?, animation: GlideAnimation<in GlideDrawable>?) {
                super.onResourceReady(resource, animation)
                loading.visibility = View.GONE
            }
        })
    }
}
package silly.h1024h.adapter

import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import silly.h1024h.R
import silly.h1024h.entity.ImgRes
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import silly.h1024h.R.id.loading
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.adapter.RecyclerViewHolder
import silly.h1024h.common.Common
import silly.h1024h.http.HttpConfig
import silly.h1024h.http.HttpManager
import silly.h1024h.http.httputil.ImageloadUtil
import silly.h1024h.utils.Init


class RecyclerAdapter(data: List<ImgRes>) : BaseRecyclerViewAdapter<ImgRes>(R.layout.adapter_img, data) {

    override fun convert(holder: RecyclerViewHolder?, item: ImgRes?, position: Int) {
        holder?.setVisible(R.id.loading, item?.visible!!)
        val imageView = holder?.getView<ImageView>(R.id.image)
        val uri = Uri.parse(item?.url)
        Glide.with(Init.ctx).load(uri).centerCrop().placeholder(R.color.color_bfbfbf).into(object : GlideDrawableImageViewTarget(imageView!!) {
            override fun onResourceReady(resource: GlideDrawable?, animation: GlideAnimation<in GlideDrawable>?) {
                super.onResourceReady(resource, animation)
                item?.visible = false
                holder?.setVisible(R.id.loading, item?.visible!!)
            }
        })
    }
}


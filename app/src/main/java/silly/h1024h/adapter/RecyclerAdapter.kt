package silly.h1024h.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.ViewTreeObserver
import com.bumptech.glide.Glide
import silly.h1024h.R
import silly.h1024h.entity.ImgRes
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.adapter.RecyclerViewHolder
import silly.h1024h.http.HttpConfig
import silly.h1024h.utils.Init
import silly.h1024h.utils.LogUtil
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.R.attr.bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth




class RecyclerAdapter(data: List<ImgRes>) : BaseRecyclerViewAdapter<ImgRes>(R.layout.adapter_main, data) {

    override fun convert(holder: RecyclerViewHolder?, item: ImgRes?, position: Int) {
        val imageView = holder?.getView<ImageView>(R.id.image)
//        Glide.with(Init.ctx).load(HttpConfig.URL_SERVICE + item?.irUrl).into(imageView!!)
        //获取图片显示在ImageView后的宽高
        Glide.with(Init.ctx)
                .load(HttpConfig.URL_SERVICE + item?.irUrl)
                .asBitmap()//强制Glide返回一个Bitmap对象
                .listener(object : RequestListener<String, Bitmap> {
                    override fun onException(e: Exception, model: String, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: String, target: Target<Bitmap>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        val layoutParams = imageView?.layoutParams
                        layoutParams?.height = resource.height
                        imageView?.layoutParams = layoutParams
                        return false
                    }
                }).into(imageView)
    }
}


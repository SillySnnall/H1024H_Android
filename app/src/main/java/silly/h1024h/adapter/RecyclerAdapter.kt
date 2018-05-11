package silly.h1024h.adapter

import com.bumptech.glide.Glide
import silly.h1024h.R
import silly.h1024h.entity.ImgRes
import android.widget.ImageView
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.adapter.RecyclerViewHolder
import silly.h1024h.http.HttpConfig
import silly.h1024h.utils.Init


class RecyclerAdapter(data: List<ImgRes>) : BaseRecyclerViewAdapter<ImgRes>(R.layout.adapter_img, data) {

    override fun convert(holder: RecyclerViewHolder?, item: ImgRes?, position: Int) {
        val imageView = holder?.getView<ImageView>(R.id.image)
        Glide.with(Init.ctx).load(HttpConfig.URL_SERVICE + item?.irUrl).centerCrop()
                .placeholder(R.color.color_bfbfbf).into(imageView!!)
    }
}


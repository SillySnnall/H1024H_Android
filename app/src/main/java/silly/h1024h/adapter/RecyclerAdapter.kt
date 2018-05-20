package silly.h1024h.adapter

import android.net.Uri
import com.bumptech.glide.Glide
import silly.h1024h.R
import silly.h1024h.entity.ImgRes
import android.widget.ImageView
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.adapter.RecyclerViewHolder
import silly.h1024h.entity.ResData
import silly.h1024h.http.HttpConfig
import silly.h1024h.utils.Init


class RecyclerAdapter(data: List<ResData>) : BaseRecyclerViewAdapter<ResData>(R.layout.adapter_img, data) {

    override fun convert(holder: RecyclerViewHolder?, item: ResData?, position: Int) {
        val imageView = holder?.getView<ImageView>(R.id.image)
        val uri = Uri.parse(item?.img_url)
        Glide.with(Init.ctx).load(uri).centerCrop().placeholder(R.color.color_bfbfbf).into(imageView!!)
    }
}


package silly.h1024h.adapter

import com.bumptech.glide.Glide
import silly.h1024h.R
import silly.h1024h.entity.ImgRes
import android.widget.ImageView
import android.widget.TextView
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.adapter.RecyclerViewHolder
import silly.h1024h.entity.More
import silly.h1024h.http.HttpConfig
import silly.h1024h.utils.Init


class MoreAdapter(data: List<More>) : BaseRecyclerViewAdapter<More>(R.layout.adapter_more, data) {
    override fun convert(holder: RecyclerViewHolder?, item: More?, position: Int) {
        holder?.setText(R.id.item_text,item?.name)
    }
}


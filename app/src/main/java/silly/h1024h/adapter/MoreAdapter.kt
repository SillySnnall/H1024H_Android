package silly.h1024h.adapter

import silly.h1024h.R
import silly.h1024h.base.adapter.BaseRecyclerViewAdapter
import silly.h1024h.base.adapter.RecyclerViewHolder
import silly.h1024h.entity.ResData


class MoreAdapter(data: List<ResData>) : BaseRecyclerViewAdapter<ResData>(R.layout.adapter_more, data) {
    override fun convert(holder: RecyclerViewHolder?, item: ResData?, position: Int) {
        holder?.setText(R.id.item_text,item?.name)
    }
}


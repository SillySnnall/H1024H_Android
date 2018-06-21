package silly.h1024h.contract

import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.Type

interface HomeContract {
    interface View {
        fun refresh(isLoad: Int)

        fun showList()
    }

    interface Presenter {
        /**
         * 加载更多为1，刷新为0
         */
        fun getCoverImg(isLoad: Int)

        fun getList(): List<ImgRes>

        fun getTypeList(): List<Type>

        fun getTypeListData()

        fun hotCount(type: String)

        fun setTable(table: String)

        fun getTable(): String
    }
}
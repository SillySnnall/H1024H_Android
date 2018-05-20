package silly.h1024h.contract

import silly.h1024h.db.dao.ResDataDao
import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.ResData

interface HomeContract {
    interface View {
        fun refresh(isLoad: Int)
    }

    interface Presenter {
        /**
         * 加载更多为1，刷新为0
         */
        fun getCoverImg(isLoad: Int)

        fun getList(): List<ResData>

        fun hotCount(irType: String)

        fun setFile(file: String)

        fun getResDataDao(): ResDataDao
    }
}
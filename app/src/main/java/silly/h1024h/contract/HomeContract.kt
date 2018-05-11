package silly.h1024h.contract

import silly.h1024h.entity.ImgRes

interface HomeContract {
    interface View {
        fun refresh(isLoad:Int)
    }

    interface Presenter {
        /**
         * 加载更多为1，刷新为0
         */
        fun getCoverImg(isLoad:Int)

        fun getList():List<ImgRes>

        fun hotCount(irType: String)
    }
}
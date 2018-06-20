package silly.h1024h.contract

import silly.h1024h.entity.ImgRes


interface RecommendContract {
    interface View {
        fun refresh(isLoad: Int)
    }

    interface Presenter {
        fun getHot(isLoad: Int)
        fun getList(): List<ImgRes>

        fun hotCount(type: String)

    }
}
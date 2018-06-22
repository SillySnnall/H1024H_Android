package silly.h1024h.contract

import silly.h1024h.entity.ImgRes
import silly.h1024h.entity.Type

interface RegisterContract {
    interface View {
        fun registerSuccess()

    }

    interface Presenter {

        fun register(account: String, password: String)
    }
}
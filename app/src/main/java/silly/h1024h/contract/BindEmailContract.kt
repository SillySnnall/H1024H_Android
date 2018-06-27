package silly.h1024h.contract

/**
 * Created by SillySnnall on 2018/5/18.
 */
interface BindEmailContract {
    interface View {
        fun commitSuccess()
    }

    interface Presenter {
        fun commitBind(email:String,code: String)

        fun sendCode(email:String)
    }
}
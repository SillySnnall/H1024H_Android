package silly.h1024h.contract

/**
 * Created by SillySnnall on 2018/5/18.
 */
interface ChangePwdContract {
    interface View {
        fun commitSuccess()
    }

    interface Presenter {
        fun changePwd(oldPwd: String, newPwd: String)
    }
}
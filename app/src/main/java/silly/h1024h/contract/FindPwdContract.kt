package silly.h1024h.contract

interface FindPwdContract {
    interface View {
        fun findSuccess()
    }

    interface Presenter {
        /**
         * 获取验证码
         */
        fun getCode(account: String, email: String)

        /**
         * 找回密码
         */
        fun findPwd(account: String, email: String, code: String, newPwd: String)
    }
}
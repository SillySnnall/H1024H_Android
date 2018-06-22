package silly.h1024h.contract


interface MeContract {
    interface View {
        fun loginSuccess()

        fun fail()
    }

    interface Presenter {
        fun login(account: String, password: String)
    }
}
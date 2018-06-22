package silly.h1024h.utils

import silly.h1024h.common.SpCommon
import java.util.regex.Pattern

/**
 * Created by SillySnnall on 2018/6/22.
 */
object Util{
    /**
     * 帐号正则
     */
    fun accountRegex(account: String): Boolean {
        return Pattern.matches("^[A-Za-z0-9]{8,16}\$", account)
    }

    /**
     * 密码正则
     */
    fun passwordRegex(password: String): Boolean {
        return Pattern.matches("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{8,16}\$", password)
    }

    fun isLogin(): Boolean{
        return SpUtil.getString(SpCommon.ACCOUNT)?.isNotEmpty()!! && SpUtil.getString(SpCommon.TOKEN)?.isNotEmpty()!!
    }
}
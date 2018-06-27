package silly.h1024h.parameters


object Parameter {
    fun getCoverImg(table: String, pageNum: Int, itemCount: Int): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "get_cover_img"
        paramsMap["table_name"] = table
        paramsMap["pageNum"] = pageNum.toString()
        paramsMap["itemCount"] = itemCount.toString()
        return paramsMap
    }

    fun getCoverImgDetailed(table: String, type: String, pageNum: Int, itemCount: Int): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "get_cover_img_detailed"
        paramsMap["table_name"] = table
        paramsMap["type"] = type
        paramsMap["pageNum"] = pageNum.toString()
        paramsMap["itemCount"] = itemCount.toString()
        return paramsMap
    }

    fun hotCount(type: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "hot_count"
        paramsMap["type"] = type
        return paramsMap
    }

    fun getTypeList(): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "get_type_list"
        return paramsMap
    }

    fun getHotImg(pageNum: Int, itemCount: Int): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "get_hot"
        paramsMap["pageNum"] = pageNum.toString()
        paramsMap["itemCount"] = itemCount.toString()
        return paramsMap
    }

    fun getMianUrl(): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "get_main_url"
        return paramsMap
    }

    fun register(account: String, password: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "register"
        paramsMap["account"] = account
        paramsMap["password"] = password
        return paramsMap
    }

    fun login(account: String, password: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "login"
        paramsMap["account"] = account
        paramsMap["password"] = password
        return paramsMap
    }


    fun opinion(account: String, content: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "opinion"
        paramsMap["account"] = account
        paramsMap["content"] = content
        return paramsMap
    }

    fun getUser(account: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "get_user"
        paramsMap["account"] = account
        return paramsMap
    }

    fun sendCode(account: String, email: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "send_code"
        paramsMap["account"] = account
        paramsMap["email"] = email
        return paramsMap
    }

    fun bindEmail(account: String, email: String, code: String): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "bind_email"
        paramsMap["account"] = account
        paramsMap["email"] = email
        paramsMap["code"] = code
        return paramsMap
    }
}
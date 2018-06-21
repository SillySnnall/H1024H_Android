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
}
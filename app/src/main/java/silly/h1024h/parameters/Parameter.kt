package silly.h1024h.parameters

object Parameter {
    fun getCoverImg(pageNum: Int, itemCount: Int): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "getCoverImg"
        paramsMap["pageNum"] = pageNum.toString()
        paramsMap["itemCount"] = itemCount.toString()
        return paramsMap
    }

    fun getCoverImgDetailed(irType: Int, pageNum: Int, itemCount: Int): HashMap<String, String> {
        val paramsMap = HashMap<String, String>()
        paramsMap["ac"] = "getCoverImgDetailed"
        paramsMap["irType"] = irType.toString()
        paramsMap["pageNum"] = pageNum.toString()
        paramsMap["itemCount"] = itemCount.toString()
        return paramsMap
    }
}
package silly.h1024h.base.entity

/**
 * 封装返回的数据
 */
open class BaseResult<T>(val msg: Int = -1, val param: String = "", val data: T? = null){
    override fun toString(): String {
        return "BaseResult(msg=$msg, param='$param', data=$data)"
    }
}


// 公用 网络请求实体
open class BaseStringResult(val msg: Int = -1, val param: String = "", val data: Any? = null)
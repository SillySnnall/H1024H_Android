package silly.h1024h.entity

import java.io.Serializable

/**
 * Created by SillySnnall on 2018/5/18.
 * 分类列表
 */
data class ResData(
        var name: String = "",
        var file: String = "",
        var img_url: String = "",
        var net_url: String = "",
        var _id: Int = 0,
        var visible: Boolean = true) : Serializable
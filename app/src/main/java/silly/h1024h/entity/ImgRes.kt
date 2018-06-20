package silly.h1024h.entity

import silly.h1024h.base.entity.BaseResult

/**
 * Created by SillySnnall on 2018/4/25.
 */

class ImgResData : BaseResult<List<ImgRes>>()

data class ImgRes(
        var name: String = "",
        var url: String = "",
        var type: String = "",
        var table_name: String = "",

        // 临时字段
        var visible: Boolean = true
)


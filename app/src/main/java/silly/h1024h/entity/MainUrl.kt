package silly.h1024h.entity

import silly.h1024h.base.entity.BaseResult

/**
 * Url 实体类
 * Created by SillySnnall on 2018/4/25.
 */

class MainUrlData : BaseResult<MainUrl>()

data class MainUrl(
        var version_code: String = "",
        var channel: String = "",
        var type: Int = 0,
        var apk_url: String = ""
)


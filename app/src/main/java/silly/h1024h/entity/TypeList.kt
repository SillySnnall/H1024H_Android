package silly.h1024h.entity

import silly.h1024h.base.entity.BaseResult

/**
 * Created by SillySnnall on 2018/4/25.
 */

class TypeList : BaseResult<List<Type>>()

data class Type(var name: String = "", var type: String = "")


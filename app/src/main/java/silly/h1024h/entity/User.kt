package silly.h1024h.entity

import silly.h1024h.base.entity.BaseResult


class UserData : BaseResult<User>()

data class User(var account: String = "",
                var token: String = "",
                var email: String = "",
                var create_time: String = "")

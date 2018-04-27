package silly.h1024h.entity

import silly.h1024h.base.entity.BaseResult

/**
 * Created by SillySnnall on 2018/4/25.
 */

class ImgResData : BaseResult<List<ImgRes>>()

data class ImgRes(var irUrl: String = "", var irType: String = "")
//data class ImgRes(var irId: Int = 0,
//                  var irUrl:String = "",
//                  var irType: Int = 0,// 封面id
//                  var irCover: Int = 0,// 是否是封面，1是，0不是
//                  var irDetails:String = "",// 备注
//                  var pageNum: Int = 0,
//                  var itemCount: Int = 0,
//                  var urlJson:String = "")


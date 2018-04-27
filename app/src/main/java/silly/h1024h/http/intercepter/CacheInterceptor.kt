package co.bxvip.android.commonlib.http.intercepter

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import silly.h1024h.http.httputil.NetworkUtil
import silly.h1024h.utils.Init

/**
 * 缓存拦截器
 * Created by SillySnnall on 2018/4/6.
 */
class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        if (!NetworkUtil.isNetworkAvailable(Init.ctx)) {
            request = request?.newBuilder()
                    ?.cacheControl(CacheControl.FORCE_CACHE)
                    ?.build()
        }
        val response = chain?.proceed(request)
        if (NetworkUtil.isNetworkAvailable(Init.ctx)) {
            val maxAge = 0
            // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
            response?.newBuilder()
                    ?.header("Cache-Control", "public, max-age=$maxAge")
                    ?.removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    ?.build()
        } else {
            // 无网络时，设置超时为4周  只对get有用,post没有缓冲
            val maxStale = 60 * 60 * 24 * 28
            response?.newBuilder()
                    ?.header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    ?.removeHeader("nyn")
                    ?.build()
        }
        return response!!
    }
}
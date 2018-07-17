package silly.h1024h.http

import android.widget.ImageView
import com.google.gson.Gson
import com.tencent.bugly.proguard.ac
import okhttp3.Call
import silly.h1024h.http.HttpConfig.URL_SERVICE
import silly.h1024h.http.httputil.CallBackUtil
import silly.h1024h.http.httputil.DownloadUtil
import silly.h1024h.http.httputil.ImageloadUtil
import silly.h1024h.http.httputil.OkhttpUtil
import silly.h1024h.utils.DesUtil
import silly.h1024h.utils.LogUtil

object HttpManager {
    private var gson: Gson = Gson()
    fun <T> post(paramsMap: HashMap<String, String>, classOfT: Class<T>, success: (T?) -> Unit,
                 fail: (String?) -> Unit) {
        var requestCount = 3// 重试次数
        OkhttpUtil.okHttpPost(URL_SERVICE, paramsMap, object : CallBackUtil.CallBackString() {
            override fun onFailure(call: Call, e: Exception) {
                e.printStackTrace()
                if (requestCount-- <= 0) {
                    fail(e.toString())
                    return
                }
                post(paramsMap, classOfT, success, fail)
            }

            override fun onResponse(response: String) {
                try {
                    val decrypt = DesUtil.decrypt(response)
                    if (classOfT.name == "java.lang.String") success(decrypt as T)
                    else success(gson.fromJson(decrypt, classOfT))
                } catch (e: Exception) {
                    e.printStackTrace()
                    LogUtil.e(e.toString())
                }
            }
        })
    }

    fun get(url: String, success: (String?) -> Unit, fail: (String?) -> Unit) {
        OkhttpUtil.okHttpGet(url, object : CallBackUtil.CallBackString() {
            override fun onFailure(call: Call, e: Exception) {
                e.printStackTrace()
                fail(e.toString())
            }

            override fun onResponse(response: String) {
                try {
                    success(response)
                } catch (e: Exception) {
                    e.printStackTrace()
                    LogUtil.e(e.toString())
                }
            }
        })
    }

    fun download(url: String, saveDir: String, success: (String?) -> Unit, downloading: (Int?) -> Unit, fail: (String?) -> Unit) {
        download(url, saveDir, "", success, downloading, fail)
    }

    fun download(url: String, saveDir: String, fileName: String, success: (String?) -> Unit, downloading: (Int?) -> Unit, fail: (String?) -> Unit) {
        OkhttpUtil.okHttpDownloadFile(url, saveDir, fileName, object : DownloadUtil.OnDownloadListener {
            override fun onDownloadFailed(call: Call?, e: java.lang.Exception?) {
                fail(e.toString())
            }

            override fun onDownloadSuccess(filePath: String?) {
                success(filePath)
            }

            override fun onDownloading(progress: Int) {
                downloading(progress)
            }
        })
    }

    fun imageLoad(imageView: ImageView, url: String, type: Int, success: () -> Unit, loading: (Int?) -> Unit, fail: (String?) -> Unit) {
        OkhttpUtil.okHttpLoadImage(imageView, url, type, object : ImageloadUtil.OnLoadListener {
            override fun onSuccess() {
                success()
            }

            override fun onLoading(progress: Int) {
                loading(progress)
            }

            override fun onFailed(e: java.lang.Exception?) {
                fail(e.toString())
            }
        })
    }
}
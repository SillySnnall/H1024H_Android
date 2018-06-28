package silly.h1024h.http

import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import silly.h1024h.http.HttpConfig.URL_SERVICE
import silly.h1024h.http.httputil.CallBackUtil
import silly.h1024h.http.httputil.DownloadUtil
import silly.h1024h.http.httputil.OkhttpUtil
import silly.h1024h.http.httputil.OkhttpUtil.okHttpDownloadFile
import silly.h1024h.utils.Init
import silly.h1024h.utils.LogUtil
import silly.h1024h.utils.Util
import java.io.File

object HttpManager {
    private var gson: Gson = Gson()
    fun <T> post(paramsMap: HashMap<String, String>, classOfT: Class<T>, success: (T?) -> Unit,
                 fail: (String?) -> Unit) {
        val ac = paramsMap["ac"]
        paramsMap.remove("ac")
        OkhttpUtil.okHttpPost("$URL_SERVICE/$ac", paramsMap, object : CallBackUtil.CallBackString() {
            override fun onFailure(call: Call, e: Exception) {
                e.printStackTrace()
                fail(e.toString())
            }

            override fun onResponse(response: String) {
                try {
                    if (classOfT.name == "java.lang.String") success(response as T)
                    else success(gson.fromJson(response, classOfT))
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
        DownloadUtil.get().download(url, saveDir, object : DownloadUtil.OnDownloadListener {
            override fun onDownloadSuccess(filePath: String?) {
                success(filePath)
            }

            override fun onDownloading(progress: Int) {
                downloading(progress)
            }

            override fun onDownloadFailed(e: java.lang.Exception?) {
                fail(e.toString())
            }
        })
    }
}
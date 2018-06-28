package silly.h1024h.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import silly.h1024h.common.SpCommon
import java.io.File
import java.util.regex.Pattern

/**
 * Created by SillySnnall on 2018/6/22.
 */
object Util {
    /**
     * 帐号正则
     */
    fun accountRegex(account: String): Boolean {
        return Pattern.matches("^[A-Za-z0-9]{8,16}\$", account)
    }

    /**
     * 密码正则
     */
    fun passwordRegex(password: String): Boolean {
        return Pattern.matches("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{8,16}\$", password)
    }

    fun isLogin(): Boolean {
        return SpUtil.getString(SpCommon.ACCOUNT)?.isNotEmpty()!! && SpUtil.getString(SpCommon.TOKEN)?.isNotEmpty()!!
    }

    /**
     * 是否是邮箱
     */
    fun isEmail(email: String): Boolean {
        val pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
        return Pattern.matches(pattern, email)
    }

    /**
     * 安装APK
     */
    fun installAPK(apkPath: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                val fileUri = FileProvider.getUriForFile(Init.ctx, Init.ctx.applicationContext.packageName + ".fileProvider", File(apkPath))
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(Uri.fromFile(File(apkPath)), "application/vnd.android.package-archive")
            }
            Init.ctx.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.e(e.toString())
        }
    }

    /**
     * 获取本地版本号
     */
     fun getVersionCode(): Int {
        val packageManager = Init.ctx.packageManager
        var versionCode = 0
        try {
            val packageInfo = packageManager.getPackageInfo(Init.ctx.packageName, 0)
            versionCode = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            LogUtil.e(e.toString())
        }
        return versionCode
    }
}
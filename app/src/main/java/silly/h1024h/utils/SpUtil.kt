package silly.h1024h.utils

import android.R.id.edit
import android.annotation.SuppressLint
import android.preference.PreferenceManager
import android.content.SharedPreferences


/**
 * Created by SillySnnall on 2018/6/22.
 */
/**
 * Created by Administrator on 2016/5/3.
 */
object SpUtil {

    private var mSharedPreferences: SharedPreferences? = null

    private// mSharedPreferences = App.context.getSharedPreferences(
    // PREFERENCE_NAME, Context.MODE_PRIVATE);
    val preferneces: SharedPreferences?
        @Synchronized get() {
            if (mSharedPreferences == null) {
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(Init.ctx)
            }
            return mSharedPreferences
        }

    /**
     * 打印所有
     */
    fun print() {
        println(preferneces?.all)
    }


    /**
     * 清空保存在默认SharePreference下的所有数据
     */
    @SuppressLint("ApplySharedPref")
    fun clear() {
        preferneces?.edit()?.clear()?.commit()
    }


    /**
     * 保存字符串
     *
     * @return
     */
    @SuppressLint("ApplySharedPref")
    fun putString(key: String, value: String) {
        preferneces?.edit()?.putString(key, value)?.commit()
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */
    fun getString(key: String): String? {
        return preferneces?.getString(key, "")

    }


    /**
     * 保存整型值
     *
     * @return
     */
    @SuppressLint("ApplySharedPref")
    fun putInt(key: String, value: Int) {
        preferneces?.edit()?.putInt(key, value)?.commit()
    }

    /**
     * 读取整型值
     *
     * @param key
     * @return
     */
    fun getInt(key: String): Int {
        return preferneces?.getInt(key, 0)!!
    }


    /**
     * 保存布尔值
     *
     * @return
     */
    @SuppressLint("ApplySharedPref")
    fun putBoolean(key: String, value: Boolean?) {
        preferneces?.edit()?.putBoolean(key, value!!)?.commit()
    }

    @SuppressLint("ApplySharedPref")
    fun putLong(key: String, value: Long) {
        preferneces?.edit()?.putLong(key, value)?.commit()
    }

    fun getLong(key: String): Long {
        return preferneces?.getLong(key, 0)!!
    }

    /**
     * t 读取布尔值
     *
     * @param key
     * @return
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preferneces?.getBoolean(key, defValue)!!

    }


    /**
     * 移除字段
     *
     * @return
     */
    @SuppressLint("ApplySharedPref")
    fun removeString(key: String) {
        preferneces?.edit()?.remove(key)?.commit()
    }
}
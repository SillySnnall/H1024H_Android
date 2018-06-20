package silly.h1024h.db.dao

import android.annotation.SuppressLint
import silly.h1024h.entity.ResData
import silly.h1024h.utils.Init.db
import silly.h1024h.utils.Init


/**
 * Created by SillySnnall on 2018/5/18.
 */
class ResDataDao(private var tabName: String) {

    init {
        if (!sqlTableIsExist(tabName)) createTable()
    }

    fun createTable() {
        val sql = "CREATE TABLE $tabName(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,file TEXT,img_url TEXT,net_url TEXT)"
        db.execSQL(sql)
    }

    /**
     * 判断数据库中某张表是否存在
     */
    @SuppressLint("Recycle")
    private fun sqlTableIsExist(tableName: String?): Boolean {
        var result = false
        if (tableName == null) {
            return false
        }
        val sql = "select count(*) as c from sqlite_master where table ='table' and name ='$tableName' "
        var cursor = Init.db.rawQuery(sql, null)
        if (cursor!!.moveToNext()) {
            val count = cursor.getInt(0)
            if (count > 0) {
                result = true
            }
        }
        cursor.close()
        return result
    }

    @SuppressLint("Recycle")
    fun queryByFile(file: String): ResData? {
        val cursor = db.rawQuery("SELECT * FROM $tabName WHERE file='$file'", null)
        //将游标移到第一行
        if (!cursor.moveToFirst()) return null
        //循环读取数据
        cursor.move(0)
        val more = ResData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getInt(0))
        cursor.close()
        return more
    }

    @SuppressLint("Recycle")
    fun queryByNetUrl(net_url: String): ResData? {
        val cursor = db.rawQuery("SELECT * FROM $tabName WHERE net_url='$net_url'", null)
        //将游标移到第一行
        if (!cursor.moveToFirst()) return null
        //循环读取数据
        cursor.move(0)
        val more = ResData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getInt(0))
        cursor.close()
        return more
    }

    @SuppressLint("Recycle")
    fun queryByImgUrl(img_url: String): ResData? {
        val cursor = db.rawQuery("SELECT * FROM $tabName WHERE img_url='$img_url'", null)
        //将游标移到第一行
        if (!cursor.moveToFirst()) return null
        //循环读取数据
        cursor.move(0)
        val more = ResData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getInt(0))
        cursor.close()
        return more
    }

    fun createByFile(resData: ResData) {
        val queryByFile = queryByFile(resData.file)
        if (queryByFile == null) {
            //插入数据SQL语句
            val sql = "INSERT INTO $tabName(name,file,img_url,net_url) VALUES('${resData.name}','${resData.file}','${resData.img_url}','${resData.net_url}')"
            //执行SQL语句
            db.execSQL(sql)
        }
    }

    fun createByNetUrl(resData: ResData) {
        val queryByFile = queryByNetUrl(resData.net_url)
        if (queryByFile == null) {
            //插入数据SQL语句
            val sql = "INSERT INTO $tabName(name,file,img_url,net_url) VALUES('${resData.name}','${resData.file}','${resData.img_url}','${resData.net_url}')"
            //执行SQL语句
            db.execSQL(sql)
        }
    }

    fun createByImgUrl(resData: ResData) {
        val queryByFile = queryByImgUrl(resData.img_url)
        if (queryByFile == null) {
            //插入数据SQL语句
            val sql = "INSERT INTO $tabName(name,file,img_url,net_url) VALUES('${resData.name}','${resData.file}','${resData.img_url}','${resData.net_url}')"
            //执行SQL语句
            db.execSQL(sql)
        }
    }

    @SuppressLint("Recycle")
    fun queryForAll(): List<ResData> {
        val cursor = db.rawQuery("SELECT * FROM $tabName", null)
        //将游标移到第一行
        val list = arrayListOf<ResData>()
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            //循环读取数据
            while (!cursor.isAfterLast) {
                list.add(ResData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(0)))
                //游标移到下一行
                cursor.moveToNext()
            }
        }
        cursor.close()
        return list
    }

    fun queryPaging(pageNum: Int, itemCount: Int): List<ResData> {
        val cursor = db.rawQuery("SELECT * FROM $tabName LIMIT $pageNum,$itemCount", null)
        //将游标移到第一行
        val list = arrayListOf<ResData>()
        //判断游标是否为空
        if (cursor.moveToFirst()) {
            //循环读取数据
            while (!cursor.isAfterLast) {
                list.add(ResData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(0)))
                //游标移到下一行
                cursor.moveToNext()
            }
        }
        cursor.close()
        return list
    }

    fun deleteAll() {
        val sql = "DELETE FROM $tabName"
        //执行SQL语句
        db.execSQL(sql)
    }
}
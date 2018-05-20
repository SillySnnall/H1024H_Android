package silly.h1024h.utils

import android.annotation.SuppressLint
import android.app.Application
import android.database.sqlite.SQLiteDatabase
import silly.h1024h.db.NativeDataBaseHelper

@SuppressLint("StaticFieldLeak")
object Init {
    lateinit var ctx: Application
    lateinit var db: SQLiteDatabase

    fun init(app: Application) {
        this.ctx = app
    }

    fun initDb(app: Application) {
        this.db = NativeDataBaseHelper(app).writableDatabase
    }
}
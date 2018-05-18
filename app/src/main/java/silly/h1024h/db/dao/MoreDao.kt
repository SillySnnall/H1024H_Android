package silly.h1024h.db.dao

import android.content.Context
import silly.h1024h.base.dao.BaseDao
import silly.h1024h.entity.More
import java.sql.SQLException

/**
 * Created by SillySnnall on 2018/5/18.
 */
class MoreDao(context: Context?) : BaseDao<More>(context, More::class.java){
    fun queryByFile(file: String): More? {
        try {
            return dao.queryBuilder().where().eq("file", file).queryForFirst()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }
}
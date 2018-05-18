package silly.h1024h.entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

/**
 * Created by SillySnnall on 2018/5/18.
 */
@DatabaseTable(tableName = "tb_More")
data class More(
        @DatabaseField
        var name: String = "",
        @DatabaseField
        var file: String = "",
        @DatabaseField(generatedId = true)
        var _id: Int = 0) : Serializable
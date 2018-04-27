package silly.h1024h.utils

import android.widget.Toast

/**
 * Created by SillySnnall on 2018/4/26.
 */
object ToastUtil {
    fun toast(text: String) {
        Toast.makeText(Init.ctx, text, Toast.LENGTH_SHORT).show()
    }
}
package silly.h1024h.view

import android.app.Activity
import android.content.Context
import silly.h1024h.R
import silly.h1024h.view.LoadingView.map

/**
 * Created by SillySnnall on 2018/5/22.
 */
object LoadingView {

    val map = mutableMapOf<Activity, SillyDialog>()

    fun init(activity: Activity) {
        map[activity] = SillyDialog(activity).loadLayout(R.layout.dialog_loading).setKeyCodeBack(object : SillyDialog.OnclickListener{
            override fun onBackClick(context: Context?) {

            }
        })
    }

    fun show(activity: Activity) {
        map[activity]?.show()
    }

    fun dismiss(activity: Activity) {
        map[activity]?.dismiss()
    }

    fun dismissCancel(activity: Activity) {
        map[activity]?.dismissCancel()
    }
}
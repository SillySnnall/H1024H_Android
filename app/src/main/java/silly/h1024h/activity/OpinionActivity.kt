package silly.h1024h.activity

import android.content.Context
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_opinion.*
import silly.h1024h.R
import silly.h1024h.base.activity.BaseMvpActivity
import silly.h1024h.contract.OpinionContract
import silly.h1024h.persenter.OpinionPersenter
import silly.h1024h.utils.ToastUtil

/**
 * 意见反馈
 */
class OpinionActivity : BaseMvpActivity<OpinionContract.Presenter>(), OpinionContract.View {
    override fun commitSuccess() {
        finish()
    }

    override fun setPersenter(): OpinionContract.Presenter {
        return OpinionPersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.activity_opinion
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {
        // 返回
        pre.setOnClickListener {
            finish()
        }
        // 点击空白键盘消失
        opinion_root.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(opinion_root.windowToken, 0)
        }
        // 提交
        commit.setOnClickListener {
            val content = edit.text.trim().toString()
            if(content.isEmpty()) {
                ToastUtil.toast("内容不能为空")
                return@setOnClickListener
            }
            mPersenter?.commitOpinion(content)
        }

    }

}

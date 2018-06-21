package silly.h1024h.fragment

import silly.h1024h.R
import silly.h1024h.base.fragment.BaseMvpFragment
import silly.h1024h.contract.MeContract
import silly.h1024h.persenter.MePersenter

class MeFragment : BaseMvpFragment<MeContract.Presenter>(), MeContract.View {

    override fun setPersenter(): MeContract.Presenter {
        return MePersenter(this)
    }

    override fun setLayoutView(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}

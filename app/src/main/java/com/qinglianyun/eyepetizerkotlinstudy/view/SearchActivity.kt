package com.qinglianyun.eyepetizerkotlinstudy.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.transition.Explode
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.qinglianyun.base.adapter.BaseClickAdapter
import com.qinglianyun.base.utils.RecyclerViewUtils
import com.qinglianyun.base.utils.SoftKeyboadUtils
import com.qinglianyun.base.utils.TextUtils
import com.qinglianyun.base.view.BaseActivity
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.adapter.HomeAdapter
import com.qinglianyun.eyepetizerkotlinstudy.adapter.SearchAdapter
import com.qinglianyun.eyepetizerkotlinstudy.presenter.SearchPresenter
import com.qinglianyun.eyepetizerkotlinstudy.view.i.ISearchView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import kotlin.collections.ArrayList

/**
 * Created by tang_xqing on 2019/11/28.
 */
class SearchActivity : BaseActivity<ISearchView, SearchPresenter>(), ISearchView {
    private lateinit var mEtSearch: EditText
    private lateinit var mTvCancel: TextView
    private lateinit var mTvHint: TextView
    private lateinit var mTvHotSearch: TextView
    private lateinit var mRvHotSearch: RecyclerView
    private lateinit var mRvHotResult: RecyclerView
    private lateinit var mFabSearch: FloatingActionButton

    private lateinit var mHotKeyAdapter: SearchAdapter
    private lateinit var mHotResultAdapter: HomeAdapter
    private var mHotData: MutableList<String> = mutableListOf()
    private var mSearchResult: MutableList<HomeBean.IssueListBean.ItemListBean> = mutableListOf()

    private var mKeyWord: String = ""

    override fun getLayoutView(): Int {
        return R.layout.activity_search
    }

    override fun initPresenter() {
        mPresenter = SearchPresenter(this)
    }

    override fun initViews() {

//        var explode = Explode()
//        explode.duration = 2000
//        window.enterTransition = explode
//        window.exitTransition = explode

        mEtSearch = findViewById(R.id.et_search)
        mTvHint = findViewById(R.id.tv_search_hint)
        mTvCancel = findViewById(R.id.tv_search_cancel)
        mTvHotSearch = findViewById(R.id.tv_hot_search)
        mRvHotSearch = findViewById(R.id.rv_hot_key_search)
        mRvHotResult = findViewById(R.id.rv_hot_search_result)
        mFabSearch = findViewById(R.id.fab_search)

        RecyclerViewUtils.initFlexboxLayoutManager(mRvHotSearch, this)
        mHotKeyAdapter = SearchAdapter(this, mHotData, mRvHotSearch)
        mRvHotSearch.adapter = mHotKeyAdapter

        RecyclerViewUtils.initVerticalLayoutManager(mRvHotResult, this)
        mHotResultAdapter = HomeAdapter(this, mSearchResult, mRvHotResult)
        mRvHotResult.adapter = mHotResultAdapter
    }

    override fun initListeners() {
        mTvCancel.setOnClickListener {
            onBackPressed()
        }

        mEtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var trim = s.toString().trim()
                trim?.let {
                    mKeyWord = trim
                    mPresenter.getSearchDataByKey(it)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        mHotKeyAdapter.setRvClickListener(object : BaseClickAdapter.OnRvClickListener<String> {
            override fun onItemClick(view: View, data: String, position: Int) {
                mKeyWord = data
                mPresenter.getSearchDataByKey(data)
            }
        })

        mHotResultAdapter.setRvClickListener(object :
            BaseClickAdapter.OnRvClickListener<HomeBean.IssueListBean.ItemListBean> {
            override fun onItemClick(
                view: View,
                data: HomeBean.IssueListBean.ItemListBean,
                position: Int
            ) {
                jumpToActivity(data)
            }
        })
    }

    private fun jumpToActivity(data: HomeBean.IssueListBean.ItemListBean) {
        data?.let {
            var dataBean = it.data
            dataBean?.run {
                var phono = cover?.feed
                var title = title
                var description = description
                var category = category
                var duration = duration
                var playUrl = playUrl
                var blurred = cover?.blurred
                var collect = consumption?.collectionCount
                var share = consumption?.shareCount
                var reply = consumption?.replyCount
                var videoBean: VideoBean = VideoBean(
                    phono,
                    title,
                    description,
                    duration,
                    playUrl,
                    category,
                    blurred,
                    collect,
                    share,
                    reply,
                    System.currentTimeMillis()
                )
                VideoDetailActivity.startAction(mActivity, videoBean)
            }
        }
    }

    override fun initData() {
        mPresenter?.getHotWord()
        setUpEnterAnimation()
        initAnimation()
    }

    private fun setUpEnterAnimation() {
        var transition =
            TransitionInflater.from(this).inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                transition?.removeListener(this)
                initAnimation()
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
            }

        })

    }

    private fun initAnimation() {
        var listAnima = listOf<ObjectAnimator>(
            ObjectAnimator.ofFloat(mFabSearch, "scaleX", 0f, 50f),
            ObjectAnimator.ofFloat(mFabSearch, "scaleY", 0f, 100f)
        )

        // 动画集合
        var animatorSet = AnimatorSet()
        animatorSet.playTogether(listAnima)
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.duration = 500
        animatorSet.start()
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
//                mFabSearch.visibility = View.GONE
                mFabSearch.visibility = View.INVISIBLE
            }
        })

        /**
         * 动态改变FloatingActionButton的颜色，使用ColorStateList改变。
         * 创建ColorStateList两种方式，一个是使用xml文件定义selector-color；一个是动态创建selector
         * 动态创建ColorStateList：第一个参数：状态；第二个参数：颜色
         */
//        var yValue = 100f
//        var animator = ObjectAnimator.ofFloat(mFabSearch, "translationX", yValue)
//        animator.start()

//        var colorStateList = Resources.getSystem().getColorStateList(R.color.color_fab_pressed)
//        mFabSearch.backgroundTintList = colorStateList


    }

    override fun getHotWorldSuccess(result: ArrayList<String>) {
        mHotKeyAdapter.setDataList(result)
    }

    override fun getHotWorldFail(code: Int, msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getSearchByKeySuc(result: HomeBean.IssueListBean) {
        result?.let {
            SoftKeyboadUtils.closeSoftKeyboard(this, mEtSearch)

            mTvHint.visibility = View.INVISIBLE
            mRvHotSearch.visibility = View.GONE
            mRvHotResult.visibility = View.VISIBLE

            var temp = mutableListOf<HomeBean.IssueListBean.ItemListBean>()
            it?.itemList?.forEach {
                temp.add(it)
                it.data?.category
            }

            mHotResultAdapter.setDataList(temp)
            TextUtils.setText(mTvHotSearch, "-「$mKeyWord」搜索结果共${result.total}个-")
        }
    }

    override fun getSearchByKeyFail(code: Int, msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        mFabSearch.visibility = View.VISIBLE
        super.onBackPressed()
    }

    companion object {
        fun startAction(ctx: Context) {

            ctx.startActivity(
                Intent(ctx, SearchActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(ctx as Activity).toBundle()
            )
        }

        fun startAction(ctx: Context, view: View) {
            ctx.startActivity(
                Intent(ctx, SearchActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    ctx as Activity,
                    view,
                    view.transitionName
                ).toBundle()
            )
        }
    }

    /**
     * ActivityOptions兼容类：ActivityOptionsCompat
     *
     * makeSceneTransitionAnimation 场景动画
     */

    /**
     * 过渡动画
     * 操作类：ActivityOptions( 兼容类--ActivityOptionsCompat )
     * 可以设置activity进入\退出的动画，也可以设置两个Activity通过共享元素来启动Activity。
     * activity进入\退出的动画步骤：
     *      1、startActivity()改用两个参数的方法，第二个参数：ActivityOptionsCompat.makeSceneTransitionAnimatio().toBundle()
     *      2、在目标Activity设置进入、退出的动画， Fade():淡入淡出  Explode()：分解 Slide(): 滑动。例如：windown.entenrTransitionAnim = Fade()
     *
     * 共享元素动画：
     *  步骤：
     *  1、两个Activity确认共享控件，控件设置android:transitionName，相同的值。源Activity控件可以不设置transitionName
     *  2、startActivity()改用两个参数的方法，第二个参数：ActivityOptionsCompat.makeSceneTransitionAnimatio()
     *     makeSceneTransitionAnimatio(activity, 共享控件, transitionName)
     *
     */

    //            ActivityCompat.startActivity()    // 是调用ContextCompat的startActivity()
    // 以下方法：调用Activity的startActivity(),还是ContextCompat的startActivity()
}

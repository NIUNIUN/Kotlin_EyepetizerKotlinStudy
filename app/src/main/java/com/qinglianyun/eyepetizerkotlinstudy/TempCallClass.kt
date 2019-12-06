package com.qinglianyun.eyepetizerkotlinstudy

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView

/** https://blog.csdn.net/u010017719/article/details/90405591
 * Created by tang_xqing on 2019/12/2.
 */
class TempCallClass {

    fun call(ctx: Context) {
        var tempAdapter: TempAdapter = TempAdapter(ctx)
        tempAdapter.setListener {
            onFailFun { code, msg ->
                println("onFailFun() call with $code $msg")
            }

            onSuccessFun {
                println("onSuccessFun() call with $it")
            }
        }

        tempAdapter.getItem(1024)
    }
}

class SimpleBulid : SimpleCall {
    private lateinit var onSuccessVal: (String) -> Unit
    private lateinit var onFailVal: (Int, String) -> Unit

    fun onSuccessFun(lister: (String) -> Unit) {
        onSuccessVal = lister
    }

    fun onFailFun(lister: (Int, String) -> Unit) {
        onFailVal = lister
    }

    override fun onSuccess(result: String) {
        onSuccessVal.invoke(result)
    }

    override fun onFail(code: Int, msg: String) {
        // 调用onFailVal()方法，等价于onFailVal(code,msg)。通过函数变量调用自身
        onFailVal.invoke(code, msg)
    }
}

interface SimpleCall {
    fun onSuccess(result: String)
    fun onFail(code: Int, msg: String)
}

class TempAdapter(ctx: Context) {
    var mCtx: Context
    lateinit var mListener: SimpleCall

    init {
        mCtx = ctx
    }

    fun setListener(simple: SimpleBulid.() -> Unit) {         // 参数是一个函数，不是一个对象
        var build1 = SimpleBulid()
        build1.simple()     // 调用simple所指的函数。因为参数是一个函数，所以也就是对象调用所传入的函数
        mListener = build1
    }

    fun getItem(position: Int) {
        var holder = ViewHolder(mCtx)
        mListener.onSuccess("成功 -$position")
        mListener.onFail(-100, "失败 -$position")
    }

    class ViewHolder(ctx: Context) {
        val tv: TextView
        val et: EditText

        init {
            tv = TextView(ctx)
            et = EditText(ctx)
        }

        fun ge(context: Context){
            var mss:MyEditText = MyEditText(context)
        }
    }
}

class MyEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle,
    defStyleRes: Int = 0
) :
    EditText(context, attrs, defStyleAttr, defStyleRes) {

}
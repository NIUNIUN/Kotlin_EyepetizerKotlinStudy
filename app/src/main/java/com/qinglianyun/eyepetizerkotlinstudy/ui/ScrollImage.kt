package com.qinglianyun.eyepetizerkotlinstudy.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.widget.ImageView

/**
 * Created by tang_xqing on 2019/12/13.
 */
class ScrollImage(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {
    var lastX: Int = -1
    var lastY: Int = -1

    var lastTraX: Float = -1f
    var lastTraY: Float = -1f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var hei = height   // 可见高度，屏幕中可以看到的。例如RecyclerView滑动过程中，隐藏了header，只展示了item
        var realHeight = measuredHeight  // 测量宽度，就是实际宽度（隐藏view + 可见view）

//        event?.x      // 相对于view的x
//        event?.rawX      // 屏幕中实际x
//        left  指View的左边缘到父View的左边缘

        event?.let {
            var x = it.x.toInt()
            var y = it.y.toInt()

            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = x
                    lastY = y

                    lastTraX = it.rawX
                    lastTraY = it.rawY
                }

                MotionEvent.ACTION_MOVE -> {

                    scrollBy(x-lastX,y-lastY)    // 对View中的内容移动，而不是控件本身
//                    computeTracker(it)  // 移动View中的内容

                    // 对View本身进行平移
                    var offsetX = (it.rawX - lastTraX).toInt()
                    var offsetY = (it.rawY - lastTraY).toInt()

                    layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)

                    lastX = x
                    lastY = y
                    /**
                     *  现在的问题：移动的时候正常，但是第二次点击移动时，又按照之前的位置进行计算，也就是说位置没有真正的发生改变
                     *
                     */
                }
            }
        }
        return true
    }

    /**
     * 可以重写此方法，来实现弹性滑动，有滑动过程
     */
    override fun computeScroll() {

    }

    /**
     * 计算一个事件一定范围内的滑动速度。
     * 要添加事件和时间
     */
    private fun computeTracker(event: MotionEvent) {
        // VelocityTracker：用来计算一个事件的滑动速度
        var obtain = VelocityTracker.obtain()
        obtain.addMovement(event)
        obtain.computeCurrentVelocity(1000) // 计算时间范围内的滑动速度，范围毫秒

        println("滑动速度-II ${obtain.xVelocity}  ${obtain.yVelocity}")

        // 不使用的时候要回收，避免内存泄漏
        obtain.recycle()
    }
}
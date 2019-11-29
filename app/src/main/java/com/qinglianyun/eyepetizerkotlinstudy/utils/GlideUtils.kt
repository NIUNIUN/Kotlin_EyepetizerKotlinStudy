package com.qinglianyun.eyepetizerkotlinstudy.utils

import android.content.Context
import android.graphics.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import kotlin.math.min
import android.graphics.RectF
import android.graphics.BitmapShader
import android.graphics.Bitmap
import android.content.res.Resources

/**
 * Created by tang_xqing on 2019/11/25.
 */
object GlideUtils {
    fun display(ctx: Context, imgUri: String, ivImg: ImageView) {
        Glide.with(ctx)
            .load(imgUri)
            .error(com.qinglianyun.eyepetizerkotlinstudy.R.mipmap.ic_empty_picture)
            .placeholder(com.qinglianyun.eyepetizerkotlinstudy.R.mipmap.ic_image_loading)
            .crossFade()
            .into(ivImg)

    }

    fun displayCircle(ctx: Context, imgUri: String, ivImg: ImageView) {
        Glide.with(ctx)
            .load(imgUri)
            .error(com.qinglianyun.eyepetizerkotlinstudy.R.mipmap.ic_empty_picture)
            .placeholder(com.qinglianyun.eyepetizerkotlinstudy.R.mipmap.ic_image_loading)
            .transform(GlideCircleTransform(ctx))
            .crossFade()
            .into(ivImg)
    }

    fun displayRound(ctx: Context, imgUri: String, ivImg: ImageView, radio: Int = 5) {
        Glide.with(ctx)
            .load(imgUri)
            .error(com.qinglianyun.eyepetizerkotlinstudy.R.mipmap.ic_empty_picture)
            .placeholder(com.qinglianyun.eyepetizerkotlinstudy.R.mipmap.ic_image_loading)
            .transform(GlideRound2Transform(ctx, radio))
            .crossFade()
            .into(ivImg)
    }
}

class GlideCircleTransform(ctx: Context) : BitmapTransformation(ctx) {
    override fun getId(): String {
        return javaClass.name
    }

    override fun transform(
        pool: BitmapPool?,
        toTransform: Bitmap?,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        // 画圆角图形
        return circleCrop(pool, toTransform) as Bitmap
    }

    fun circleCrop(pool: BitmapPool?, toTransform: Bitmap?): Bitmap? {
        if (null == toTransform) {
            return null
        }
        var width = toTransform?.width
        var height = toTransform?.height
        var minValue = min(width, height)
        var x = (width - minValue) / 2
        var y = (height - minValue) / 2

        // 1、对原图裁剪合适的矩形
        // 原图，裁剪的开始位置x,y  ，图片宽高
        var squared = Bitmap.createBitmap(toTransform, x, y, minValue, minValue)

        // 2、设置图片的信息
        var result = pool?.get(minValue, minValue, Bitmap.Config.ARGB_8888)
        if (null == result) {
            result = Bitmap.createBitmap(minValue, minValue, Bitmap.Config.ARGB_8888)
        }

        // 3、画圆
        var canvas: Canvas = Canvas(result)
        var paint = Paint()
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        var cx: Float = minValue * 1.0f / 2
        canvas.drawCircle(cx, cx, cx, paint)
        return result
    }
}

class GlideRound2Transform(ctx: Context, round: Int = 5) : BitmapTransformation(ctx) {
    var mRound: Float

    init {
        mRound = Resources.getSystem().displayMetrics.density * round
    }

    override fun getId(): String {
        return javaClass.name + Math.round(mRound)
    }

    override fun transform(
        pool: BitmapPool?,
        toTransform: Bitmap?,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return transformRound(pool, toTransform) as Bitmap
    }

    fun transformRound(pool: BitmapPool?, toTransform: Bitmap?): Bitmap? {
        if (null == toTransform) {
            return null
        }
        var width = toTransform.width
        var height = toTransform.height

        var result = pool?.get(width, height, Bitmap.Config.ARGB_8888)
        if (null == result) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        var canvas = Canvas(result)

        var paint = Paint()
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true

        var recf = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(recf, mRound, mRound, paint)

        return result
    }
}

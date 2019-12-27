package com.txq.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

/** https://blog.csdn.net/wufeng55/article/details/80918749
 * Created by tang_xqing on 2019/12/4.
 */
class CameraUtils {
    companion object {
        private val context: Context by lazy {
            Utils.getContext()
        }
        const val ALBUM_RESULT_CODE = 0x12
        const val CROP_RESULT_CODE = 0x13
        const val OPEN_CAMERA_RESULT_CODE = 0x14

        fun getProviderName(context: Context) = context.applicationContext.packageName + ".provider"

        /**
         * 打开系统相册
         */
        fun openSysAlbum(context: Fragment) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            context.startActivityForResult(
                intent,
                ALBUM_RESULT_CODE
            )
        }

        fun openSysAlbum(context: Activity) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            context.startActivityForResult(
                intent,
                ALBUM_RESULT_CODE
            )
        }

        fun openCamera(context: Activity, uri: Uri) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (!uri.isNull()) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // android7.0一定要添加临时权限标记
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            context.startActivityForResult(
                intent,
                OPEN_CAMERA_RESULT_CODE
            )
        }

        fun openCamera(content: Fragment, uri: Uri) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (!uri.isNull()) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            content.startActivityForResult(
                intent,
                OPEN_CAMERA_RESULT_CODE
            )
        }

        /**
         * 裁剪图片。
         * 保存裁剪后图片的Uri，通过Uri.fromFile()获取，否则Android7.0以上提示”无法保存经过裁剪后的图片“信息
         *
         * 系统相机拍照后调用裁剪，提示”无法加载图片“。解决方案：intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
         */
        fun cropImage(context: Fragment, uri: Uri, cropImgUri: Uri) {
            if (uri.isNull()) {
                return
            }
            val intent = Intent("com.android.camera.action.CROP")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            // 源地址uri
            intent.setDataAndType(uri, "image/*")
            //设置目的地址uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImgUri)

            // 开启裁剪
            intent.putExtra("crop", "true")

            // 裁剪宽高比
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)

            // 裁剪输出大小
            intent.putExtra("outputX", 320)
            intent.putExtra("outputY", 320)
            intent.putExtra("scale", true)

            intent.putExtra(
                "return-data",
                false
            )    // true--返回bitmap; false--通过我们设置的uri来获取图片，防止图片太大导致OOM

            // 图片格式
//            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

            intent.putExtra("noFaceDetection", true) // 取消人脸识别

            context.startActivityForResult(
                intent,
                CROP_RESULT_CODE
            )
        }

        fun cropImage(context: Activity, uri: Uri) {
            if (uri.isNull()) {
                return
            }

            val intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            intent.putExtra("outputX", 320)
            intent.putExtra("outputY", 320)
            intent.putExtra("scale", true)
            context.startActivityForResult(
                intent,
                CROP_RESULT_CODE
            )
        }

        fun getOuputMediaFileUri(fileName: String): Uri? {
            var mediaFile = createFile(fileName)
            if (null == mediaFile) {
                return null
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  // 7.0及以上版本
                return FileProvider.getUriForFile(
                    context, getProviderName(context), mediaFile
                )   // 与清单文件provider配置信息一致
            } else {
                return Uri.fromFile(mediaFile)        //或者 Uri.isPaise("file://"+file.toString()
            }
        }

        fun getBitmapForUri(context: Context, uri: Uri): Bitmap? {
            var inputStream = context.contentResolver.openInputStream(uri)
            // 不加载文件到内存中，得到bitmap的真实宽、高
            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true   // 不加载到内存中
            options.inDither = true
            options.inPreferredConfig = Bitmap.Config.RGB_565
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream.close()

            var outWidth = options.outWidth
            var outHeight = options.outHeight
            if (outHeight == -1 || outWidth == -1) {
                return null
            }

            var hh = 800f
            var ww = 400f
            var be = 1  // 1表示不缩放
            if (outHeight > hh && outHeight > outWidth) {
                be = (outHeight.toFloat() / hh).toInt()
            } else if (outWidth > ww && outWidth > outHeight) {
                be = (outWidth.toFloat() / ww).toInt()
            }

            be = if (be <= 0) 1 else be

            // 图片按比例压缩
            var bitmapOptions = BitmapFactory.Options()
            bitmapOptions.inSampleSize = be   // 压缩比例
            bitmapOptions.inDither = true
            bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565
            inputStream = context.contentResolver.openInputStream(uri)
            var bitmap = BitmapFactory.decodeStream(inputStream, null, bitmapOptions)
            inputStream.close()
            return compressImage(bitmap)
        }

        /**
         * 进行质量压缩
         */
        fun compressImage(image: Bitmap): Bitmap {
            var baos = ByteArrayOutputStream()

            // 第一个参数：图片格式；第二个参数：图片质量，100-最高，0-最差；第三个参数：保存压缩后的数据流
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)  // 质量压缩方法。100表示不压缩
            var options = 100
            while (baos.toByteArray().size / 1024 > 100) {
                // 循环判断图片是否大于100Kb，大于则压缩
                baos.reset()
                image.compress(Bitmap.CompressFormat.JPEG, options, baos)
                options = options.minus(10)
                if (options <= 0) {
                    break
                }
            }
            var inputStream = ByteArrayInputStream(baos.toByteArray())
            return BitmapFactory.decodeStream(inputStream)
        }


        fun createFile(fileName: String): File? {
            var mediaFile: File
            var mediaStorageDir = File(Environment.getExternalStorageDirectory().absolutePath)
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            mediaFile = File(
                mediaStorageDir.path + File.separator + "CameraPicature", fileName
            )   // 这里的文件名与provider配置的xml一致
            if (!mediaFile.parentFile.exists()) {
                // 拍照存储图片，对于文件夹未创建，有些机型会自动创建，有些机型不会
                mediaFile.parentFile.mkdirs()
            }
            if (!mediaFile.exists()) {
                mediaFile.createNewFile()
            }
            return mediaFile
        }

        fun Uri.isNull() = null == this
    }
}
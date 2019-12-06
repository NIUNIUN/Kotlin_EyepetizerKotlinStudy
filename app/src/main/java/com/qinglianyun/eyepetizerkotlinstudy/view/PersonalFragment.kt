package com.qinglianyun.eyepetizerkotlinstudy.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.qinglianyun.base.utils.ToastUtils
import com.qinglianyun.base.view.BaseFragment
import com.qinglianyun.eyepetizerkotlinstudy.R
import com.qinglianyun.eyepetizerkotlinstudy.presenter.MainPresenter
import com.qinglianyun.eyepetizerkotlinstudy.ui.CameraDialogFragment
import com.qinglianyun.base.utils.CameraUtils
import com.qinglianyun.eyepetizerkotlinstudy.view.i.IMainView

/**
 * Created by tang_xqing on 2019/11/22.
 */
class PersonalFragment : BaseFragment<IMainView, MainPresenter>(), IMainView,
    CameraDialogFragment.OnListener {
    private lateinit var mTvCache: TextView
    private lateinit var mTvWatch: TextView
    private lateinit var mIvHeader: ImageView

    private var imageUri: Uri? = null
    private var imagCropUri: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_personal
    }

    override fun initPresenter() {
        mPresenter = MainPresenter(this)
    }

    override fun initViews() {
        mTvCache = mActivity.findViewById(R.id.tv_cache)
        mTvWatch = mActivity.findViewById(R.id.tv_watch)
        mIvHeader = mActivity.findViewById(R.id.iv_head)
    }

    override fun initListeners() {
        mIvHeader.setOnClickListener {
            if (checkCameraPermission()) {
                showDialog()
            }
        }

        mTvCache.setOnClickListener {
            VideoCacheActivity.startAction(mActivity)
        }
    }

    override fun initData() {
    }

    fun checkCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                mActivity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                mActivity,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PER
            )
            return false
        }
        return true
    }

    private fun showDialog() {
        var instance = CameraDialogFragment.getInstance()
        instance.setOnListener(this)
        instance.show(childFragmentManager, null)
    }

    override fun choosePicture() {
        // 调用相机
        CameraUtils.openSysAlbum(this)
    }

    override fun camera() {
        // 调用系统相机
        var openCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageUri = CameraUtils.getOuputMediaFileUri(mActivity, "camera.jpg")
        if (null != imageUri) {
            // 因为指定了图片存储路径，所以onActivityResult()返回的data为null。避免有些机型不指定Uri返回data为null，所以还是指定Uri
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
        // Android7.0 一定要添加临时权限标记
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(openCameraIntent, TAKE_PICTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PER && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ToastUtils.showShortInfo("已授予访问相机权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PICTURE) {
                if (null != data) {  // 说明没有指定文件路径或有些机型为null
                    if (data.hasExtra("data")) {
                        var bitmap: Bitmap = data.getParcelableExtra("data")
                        mIvHeader.setImageBitmap(CameraUtils.compressImage(bitmap))
                    }
                } else {    // 指定了图片存储路径，通过指定的Uri来获取图片  openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                    imageUri?.let {
                        imagCropUri = CameraUtils.getOuputMediaFileUri(mActivity, "corp.jpg")
                        CameraUtils.cropImage(this, it, imagCropUri!!)
//                        var bitmap = CameraUtils.getBitmapForUri(mActivity, it)
//                        bitmap?.let {
//                            mIvHeader.setImageBitmap(it)
//                        }
                    }
                }
            } else if (requestCode == CameraUtils.CROP_RESULT_CODE) {
                // 裁剪时,这样设置 cropIntent.putExtra("return-data", true); 处理方案如下
                data?.extras?.let {
                    var bitmap = it.getParcelable<Bitmap>("data")
                    mIvHeader.setImageBitmap(bitmap)
                }
            } else {
                // 裁剪时,这样设置 cropIntent.putExtra("return-data", false); 处理方案如下
                imagCropUri?.let {
                    var bitmap = CameraUtils.getBitmapForUri(mActivity, it)
                    bitmap?.let {
                        mIvHeader.setImageBitmap(it)
                    }
                }
            }
        } else if (requestCode == CameraUtils.ALBUM_RESULT_CODE) {
            data?.data?.let {
                ToastUtils.showShortInfo("获取图片 " + it)
//                var bitmap = CameraUtils.getBitmapForUri(mActivity, it)
//                bitmap?.let {
//                    mIvHeader.setImageBitmap(it)
//                }
            }
        }
    }

    companion object {
        const val TAKE_PICTURE = 0x123
        const val CAMERA_PER = 0x124

        fun getInstance(): PersonalFragment {
            return PersonalFragment()
        }
    }
}
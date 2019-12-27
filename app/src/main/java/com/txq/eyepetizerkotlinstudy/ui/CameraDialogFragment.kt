package com.txq.eyepetizerkotlinstudy.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import com.txq.eyepetizerkotlinstudy.R

/**
 * Created by tang_xqing on 2019/12/4.
 */
class CameraDialogFragment : DialogFragment() {
    private lateinit var mRgCamera: RadioGroup
    private lateinit var mRbCamera: RadioButton
    private lateinit var mRbPicature: RadioButton
    private lateinit var mListener: OnListener

    companion object {
        fun getInstance(): CameraDialogFragment {
            return CameraDialogFragment()
        }
    }

    fun setOnListener(listener: OnListener) {
        mListener = listener
    }

    interface OnListener {
        fun choosePicture()
        fun camera()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view =
            LayoutInflater.from(context).inflate(R.layout.dialog_fragment_camera_layout, null)

        mRbCamera = view.findViewById(R.id.rb_camera)
        mRgCamera = view.findViewById(R.id.rg_camera)
        mRbPicature = view.findViewById(R.id.rb_picature)

        mRbCamera.setOnClickListener {
            mListener?.camera()
            dialog.dismiss()
        }

        mRbPicature.setOnClickListener {
            mListener?.choosePicture()
            dialog.dismiss()
        }

        var dialog = AlertDialog.Builder(context!!)
            .setView(view)
            .setPositiveButton(null, null)
            .setNegativeButton(null, null)
            .create()

        return dialog
    }
}
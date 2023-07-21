package com.simple.hackbottomsheet.ui.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuanhoang.bottomsheet.CustomBottomSheetDialog


open class BaseBottomSheetDialogFragment(contentLayoutId: Int) : BottomSheetDialogFragment(contentLayoutId) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = object : CustomBottomSheetDialog(requireContext(), theme) {

        }

        dialog.setOnShowListener {

        }

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        Log.d("tuanha", "onDismiss: ")
    }
}
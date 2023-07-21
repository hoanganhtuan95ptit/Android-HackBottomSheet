package com.simple.hackbottomsheet.ui

import android.os.Bundle
import android.view.View
import com.simple.hackbottomsheet.R
import com.simple.hackbottomsheet.ui.base.BaseBottomSheetDialogFragment
import com.simple.hackbottomsheet.utils.ext.getStatusBar

class Preview2BottomSheetDialogFragment : BaseBottomSheetDialogFragment(R.layout.fragment_preview_2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btn_open_preview_2).setOnClickListener {

            Preview1BottomSheetDialogFragment().show(childFragmentManager, "")
        }

        view.findViewById<View>(R.id.btn_open_preview_3).setOnClickListener {

            Preview2BottomSheetDialogFragment().show(childFragmentManager, "")
        }

        view.findViewById<View>(R.id.btn_open_preview_4).setOnClickListener {

            dismiss()
        }

        val layoutParams = view.layoutParams
        layoutParams.height = requireActivity().window.decorView.height - requireActivity().window.decorView.getStatusBar() - 50
    }
}
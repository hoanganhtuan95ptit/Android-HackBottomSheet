package com.simple.hackbottomsheet.ui

import android.os.Bundle
import android.view.View
import com.simple.hackbottomsheet.R
import com.simple.hackbottomsheet.ui.base.BaseBottomSheetDialogFragment

class Preview1BottomSheetDialogFragment : BaseBottomSheetDialogFragment(R.layout.fragment_preview_1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btn_open_preview_2).setOnClickListener {

            Preview1BottomSheetDialogFragment().show(childFragmentManager, "")
        }
    }
}
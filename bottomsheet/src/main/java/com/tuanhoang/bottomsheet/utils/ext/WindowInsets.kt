@file:Suppress("DEPRECATION")

package com.tuanhoang.bottomsheet.utils.ext

import android.os.Build
import android.view.WindowInsets

fun WindowInsets.getStatusBar() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    getInsets(WindowInsets.Type.systemBars()).top
} else {
    systemWindowInsetTop
}

fun WindowInsets.getNavigationBar() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    getInsets(WindowInsets.Type.navigationBars()).bottom
} else {
    systemWindowInsetBottom
}

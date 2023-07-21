package com.tuanhoang.bottomsheet.utils.ext

import android.app.Activity
import android.os.Build

fun Activity.getHeightStatusBar(): Int {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        window.decorView.rootWindowInsets.getStatusBar()
    } else {

        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        if (resourceId > 0) {

            resources.getDimensionPixelSize(resourceId)
        } else {

            0
        }
    }
}

fun Activity.getHeightNavigationBar(): Int {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        window.decorView.rootWindowInsets.getNavigationBar()
    } else {

        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        if (resourceId > 0) {

            resources.getDimensionPixelSize(resourceId)
        } else {

            0
        }
    }
}
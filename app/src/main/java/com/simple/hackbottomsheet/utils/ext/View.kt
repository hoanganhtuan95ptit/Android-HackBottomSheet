package com.simple.hackbottomsheet.utils.ext

import android.view.View

fun View.getStatusBar() = rootWindowInsets.getStatusBar()

fun View.getNavigationBar() = rootWindowInsets.getNavigationBar()

fun View.getHeightStatusBarOrNull() = rootWindowInsets.getHeightStatusBarOrNull()

fun View.getHeightNavigationBarOrNull() = rootWindowInsets.getHeightNavigationBarOrNull()
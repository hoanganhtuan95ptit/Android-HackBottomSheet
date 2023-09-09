package com.simple.bottomsheet

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.AttrRes
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.simple.bottomsheet.utils.ext.getHeightNavigationBar
import com.simple.bottomsheet.utils.ext.getHeightStatusBar
import kotlin.math.max
import kotlin.math.min

open class CustomBottomSheetDialog(context: Context, theme: Int) : BottomSheetDialog(context, theme) {

    open val animDuration: Long = 350L

    open val isSupportAnimation: Boolean = true


    var rootView: ViewGroup? = null

    var background: View? = null


    private var _delay: Boolean = false

    private var _cancelable: Boolean = false


    private var bottomSheet: ViewGroup? = null


    private var percent: Float = 0f


    private var animator: ValueAnimator? = null


    private var onShowListener: DialogInterface.OnShowListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomSheet = findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
        rootView = bottomSheet!!.parent as ViewGroup


        if (isSupportAnimation) {

            val colorBackground = bottomSheet?.children?.firstOrNull()?.context?.getColorFromAttr(com.google.android.material.R.attr.colorOnBackground) ?: Color.BLACK

            background = View(context)
            background!!.alpha = 0f
            background!!.setBackgroundColor(colorBackground)
            findViewById<ViewGroup>(com.google.android.material.R.id.container)!!.addView(background, 0)

            window?.setDimAmount(0f)

            window?.setWindowAnimations(R.style.DialogNoAnimation)
        }


        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ownerActivity!!.window.decorView.bottom + ownerActivity!!.getHeightNavigationBar())


        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                if (!isSupportAnimation) {

                    return
                }

                percent = max(0f, min(1f, 1f + slideOffset))

                handleProgress(percent)
            }
        })


        val rootView = rootView ?: return

        rootView.doOnPreDraw {

            bottomSheet!!.setBackgroundResource(android.R.color.transparent)

            if (!isSupportAnimation) {

                return@doOnPreDraw
            }

            rootView.translationY = rootView.height.toFloat() + ownerActivity!!.getHeightNavigationBar()
        }

        rootView.doOnLayout {

            if (!isSupportAnimation || _delay) {

                return@doOnLayout
            }

            handleShow()
        }

        rootView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->

            if (!isSupportAnimation) {

                return@addOnLayoutChangeListener
            }

            behavior.peekHeight = rootView.height + ownerActivity!!.getHeightNavigationBar()
        }
    }

    fun postponeEnterTransition() {

        _delay = true
    }

    fun startPostponedEnterTransition() {

        _delay = false

        rootView?.doOnLayout {

            if (animator == null) handleShow()
        }
    }

    override fun setCancelable(cancelable: Boolean) {

        super.setCancelable(cancelable)
        this._cancelable = cancelable
    }

    override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {

        if (isSupportAnimation) {
            this.onShowListener = listener
        } else {
            super.setOnShowListener(listener)
        }
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {

        if (isSupportAnimation && _cancelable && event.action == KeyEvent.ACTION_DOWN && ownerActivity?.window != null) {

            handleDismiss()
            return true
        }

        return super.dispatchKeyEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        if (isSupportAnimation && _cancelable && event.action == MotionEvent.ACTION_UP && event.y.toInt() < bottomSheet!!.top && ownerActivity?.window != null) {

            handleDismiss()
            return true
        }

        return super.dispatchTouchEvent(event)
    }

    override fun dismiss() {

        if (animator?.isRunning == true) {

            return
        }

        if (isSupportAnimation && dismissWithAnimation) {

            handleDismiss()
        } else {

            rootView = null
            bottomSheet = null

            background = null

            onShowListener = null

            super.dismiss()
        }
    }

    private fun handleShow() {

        if (animator?.isRunning == true || !isSupportAnimation) {

            return
        }

        val rootView = rootView ?: return

        val bottomSheet = bottomSheet ?: return

        val list = arrayListOf<PropertyValuesHolder>()

        list.add(PropertyValuesHolder.ofFloat("percent", percent, 1f))

        animator = list.animation(duration = animDuration, onUpdate = {

            val percent = it.getAnimatedValue("percent") as Float

            handleProgress(percent)

            rootView.translationY = (1 - percent) * bottomSheet.height
        }, onEnd = {

            dismissWithAnimation = true

            percent = 1f

            onShowListener?.onShow(this)
        })
    }

    private fun handleDismiss() {

        if (animator?.isRunning == true || !isSupportAnimation) {

            return
        }

        val rootView = rootView ?: return

        val bottomSheet = bottomSheet ?: return

        val list = arrayListOf<PropertyValuesHolder>()

        list.add(PropertyValuesHolder.ofFloat("percent", percent, 0f))

        animator = list.animation(duration = animDuration, onUpdate = {

            val percent = it.getAnimatedValue("percent") as Float

            handleProgress(percent)

            rootView.translationY = (1 - percent) * bottomSheet.height
        }, onEnd = {

            dismissWithAnimation = false

            percent = 0f

            dismiss()
        })
    }

    private fun handleProgress(percent: Float) {

        if (!isSupportAnimation) {

            return
        }

        Manager.updateProgress(this, percent)
    }

    private fun List<PropertyValuesHolder>.animation(startDelay: Long = 0, duration: Long = 350, onStart: () -> Unit = {}, onUpdate: (ValueAnimator) -> Unit = { _ -> }, onEnd: () -> Unit = {}): ValueAnimator {

        onStart.invoke()

        val animator = ValueAnimator.ofPropertyValuesHolder(*this.toTypedArray())

        animator.addUpdateListener {
            onUpdate.invoke(it)
        }

        animator.doOnEnd {
            onEnd.invoke()
        }

        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.startDelay = startDelay
        animator.start()

        return animator
    }

    private fun Context.getColorFromAttr(@AttrRes attrColor: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attrColor, typedValue, true)
        return typedValue.data
    }
}

private object Manager {


    private val activityAndListDialog: HashMap<Activity, ArrayList<CustomBottomSheetDialog>> = hashMapOf()


    fun updateProgress(dialog: CustomBottomSheetDialog, percent: Float) {


        val activity = dialog.ownerActivity ?: return

        val activityDecorView = activity.window.decorView

        val activityAndListDialog = activityAndListDialog ?: return


        val list: ArrayList<CustomBottomSheetDialog> = activityAndListDialog[activity] ?: arrayListOf<CustomBottomSheetDialog>().apply {

            activityAndListDialog[activity] = this
        }


        val alphaMax = 0.5f

        val spaceScale = 0.07f

        val spaceTranslationY = activity.getHeightStatusBar() - activityDecorView.height * spaceScale / 2f


        if (percent == 0f && !list.contains(dialog)) return
        if (percent != 0f && !list.contains(dialog)) list.add(dialog)


        val dialogBefore = list.getOrNull(list.lastIndex - 1)
        val dialogCurrent = list.lastOrNull()


        dialogBefore?.background?.alpha = alphaMax - percent * alphaMax
        dialogCurrent?.background?.alpha = percent * alphaMax


        val contentView = dialogBefore?.rootView ?: activityDecorView.findViewById<ViewGroup>(android.R.id.content).getChildAt(0).apply {

            (activity as? ActivityScreen)?.onPercent(percent)
        }


        if (percent == 0f && list.contains(dialog)) list.remove(dialog)
        if (percent == 0f && list.isEmpty()) activityAndListDialog.remove(activity)


        val scale = spaceScale * percent

        val translationY = spaceTranslationY * percent

        contentView.scaleX = (1 - scale)
        contentView.scaleY = (1 - scale)
        contentView.translationY = translationY
    }
}

interface ActivityScreen {

    fun onPercent(percent: Float)
}

package com.ravi.practicaltaskmvvm.utils.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

import androidx.core.view.isVisible
import com.ravi.practicaltaskmvvm.R
import com.ravi.practicaltaskmvvm.databinding.ViewStateCircularProgrssBinding

class ApiViewStateConstraintLayout : ConstraintLayout {
    private lateinit var inflater: LayoutInflater
    private var viewState = ViewState.SHOW_CONTENT
    private var circularProgressView: ViewStateCircularProgrssBinding? = null
    private var typedArray: TypedArray? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        initAttribute(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initAttribute(attributeSet)
    }

    private fun initAttribute(attributeSet: AttributeSet?) {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ApiViewStateConstraintLayout)
    }

    fun showContent() {
        viewState = ViewState.SHOW_CONTENT
        circularProgressView?.root?.isVisible = false
    }

    fun showProgress() {
        viewState = ViewState.PROGRESS_LOADING
        circularProgressView ?: inflateCircularProgressView()
        circularProgressView?.root?.isVisible = true
        applyCustomStyles()
    }

    private fun applyCustomStyles() {
        typedArray?.getDimensionPixelSize(
            R.styleable.ApiViewStateConstraintLayout_circularProgressBarWidth,
            0
        )?.takeIf { it != 0 }?.let {
            circularProgressView?.circularProgressBar?.layoutParams?.width = it
        }

        typedArray?.getDimensionPixelSize(
            R.styleable.ApiViewStateConstraintLayout_circularProgressBarHeight,
            0
        )?.takeIf { it != 0 }?.let {
            circularProgressView?.circularProgressBar?.layoutParams?.height = it
        }

        typedArray?.getColor(
            R.styleable.ApiViewStateConstraintLayout_circularProgressBarColor,
            0
        )?.takeIf { it != 0 }?.let {
            circularProgressView?.circularProgressBar?.indeterminateDrawable?.setColorFilter(
                it,
                PorterDuff.Mode.SRC_IN
            )
        }

        circularProgressView?.circularProgressBar?.requestLayout()
    }

    private fun inflateCircularProgressView() {
        circularProgressView = ViewStateCircularProgrssBinding.bind(
            inflater.inflate(
                R.layout.view_state_circular_progrss,
                null
            )
        )
        circularProgressView?.root?.tag = ViewState.PROGRESS_LOADING

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            topToTop = ConstraintSet.PARENT_ID
            bottomToBottom = ConstraintSet.PARENT_ID
            startToStart = ConstraintSet.PARENT_ID
            endToEnd = ConstraintSet.PARENT_ID
        }
        addView(circularProgressView?.root, layoutParams)
    }

    private enum class ViewState {
        PROGRESS_LOADING,
        SHOW_CONTENT
    }
}

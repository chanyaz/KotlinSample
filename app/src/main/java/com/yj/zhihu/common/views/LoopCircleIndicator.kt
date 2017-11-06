package com.yj.zhihu.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.yj.zhihu.R


/**
 * @author yuanjian 17/11/3.
 */
class LoopCircleIndicator : FrameLayout {

    private var mIndicatorRadio = -1
    private var mIndicatorMargin = -1
    private var mIndicatorBackgroundResId = R.drawable.yj_radius_red
    private var mIndicatorUnselectedBackgroundResId = R.drawable.yj_radius_white
    private var num = -1
    private lateinit var childParams: LinearLayout.LayoutParams
    private lateinit var selectedView: View

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        handleTypedArray(context, attrs)
    }

    private fun handleTypedArray(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoopCircleIndicator)
        mIndicatorRadio = typedArray.getDimensionPixelSize(R.styleable.LoopCircleIndicator_yj_lci_radio, 20)
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.LoopCircleIndicator_yj_lci_margin, 5)
        childParams = LinearLayout.LayoutParams(mIndicatorRadio, mIndicatorRadio).apply {
            rightMargin = mIndicatorMargin
        }
        mIndicatorBackgroundResId = typedArray.getResourceId(R.styleable.LoopCircleIndicator_yj_lci_drawable, R.drawable.yj_radius_white)
        mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(R.styleable.LoopCircleIndicator_yj_lci_drawable_unselected, mIndicatorBackgroundResId)

        typedArray.recycle()
    }

    fun initIndicator(num: Int) {
        this.num = num
        addView(LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            var i = 0
            while (i < num) {
                if (i == num - 1) {
                    addView(View(context).apply {
                        setBackgroundResource(mIndicatorUnselectedBackgroundResId)
                    }, LinearLayout.LayoutParams(mIndicatorRadio, mIndicatorRadio))
                } else {
                    addView(View(context).apply {
                        setBackgroundResource(mIndicatorUnselectedBackgroundResId)
                    }, childParams)
                }
                i++
            }

        })
        selectedView = View(context).apply {
            layoutParams = ViewGroup.LayoutParams(mIndicatorRadio, mIndicatorRadio)
            setBackgroundResource(mIndicatorBackgroundResId)
        }
        addView(selectedView)
    }

    fun scrollIndicator(position: Int, positionOffset: Float) {
        selectedView.translationX = (position + positionOffset) * (mIndicatorRadio + mIndicatorMargin)
    }

}
package com.lvkang.skin.wedget.helper

import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import  androidx.appcompat.R
import com.lvkang.skin.ktx.obtainStyledAttributes
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.wedget.SkinCompatHelper

/**
 * @name SkinCompatBackgroundHelper
 * @package com.lvkang.skin.wedget
 * @author 345 QQ:1831712732
 * @time 2020/11/29 16:30
 * @description 更换背景的帮助类
 */

class SkinCompatBackgroundHelper(val view: View) : SkinCompatHelper() {

    private var backgroundResId = INVALID_ID

    fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        obtainStyledAttributes(
            view, attrs, R.styleable.ViewBackgroundHelper, defStyleAttr, 0
        ) {
            backgroundResId = it.getResourceId(
                R.styleable.ViewBackgroundHelper_android_background, INVALID_ID
            )
        }
        applySkin()
    }


    override fun applySkin() {
        setBackground(backgroundResId)
    }

    private fun setBackground(res: Int) {
        if (res == INVALID_ID) return
        val drawable = SkinCompatResources.getDrawable(res)
        if (drawable != null) {
            val paddingleft = view.paddingLeft
            val paddingTop = view.paddingTop
            val paddingRight = view.paddingRight
            val paddingBottom = view.paddingBottom
            ViewCompat.setBackground(view, drawable)
            view.setPadding(paddingleft, paddingTop, paddingRight, paddingBottom)
            return
        }
        val color = SkinCompatResources.getColor(res)
        color?.run {
            view.setBackgroundColor(color)
        }
    }
}
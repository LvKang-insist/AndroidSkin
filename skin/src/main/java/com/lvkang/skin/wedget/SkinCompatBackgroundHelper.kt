package com.lvkang.skin.wedget

import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat
import com.lvkang.skin.R
import com.lvkang.skin.SkinManager
import com.lvkang.skin.ktx.obtainStyledAttributes

/**
 * @name SkinCompatBackgroundHelper
 * @package com.lvkang.skin.wedget
 * @author 345 QQ:1831712732
 * @time 2020/11/29 16:30
 * @description
 */

class SkinCompatBackgroundHelper(val view: View) : SkinCompatHelper() {

    var backgroundResId = INVALID_ID

    fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        obtainStyledAttributes(
            view, attrs, R.styleable.skinBackgroundHelper, defStyleAttr, 0
        ) {
            backgroundResId = it.getResourceId(
                R.styleable.skinBackgroundHelper_android_background, INVALID_ID
            )
        }
        applySkin()
    }


    override fun applySkin() {
        setBackground(backgroundResId)
    }

    private fun setBackground(res: Int) {
        if (res == INVALID_ID) return
        val drawable = SkinManager.getSkinResources().getDrawable(backgroundResId)
        drawable?.run {
            val paddingleft = view.paddingLeft
            val paddingTop = view.paddingTop
            val paddingRight = view.paddingRight
            val paddingBottom = view.paddingBottom
            ViewCompat.setBackground(view, drawable)
            view.setPadding(paddingleft, paddingTop, paddingRight, paddingBottom)
        }
        val color = SkinManager.getSkinResources().getColor(backgroundResId)
        color?.run {
            view.setBackgroundColor(color)
        }
    }
}
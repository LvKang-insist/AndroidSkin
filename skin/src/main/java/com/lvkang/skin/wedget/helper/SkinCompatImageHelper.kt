package com.lvkang.skin.wedget.helper

import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.lvkang.skin.R
import com.lvkang.skin.ktx.obtainStyledAttributes
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.wedget.SkinCompatHelper

/**
 * @name SkinCompatImageHelper
 * @package com.lvkang.skin.wedget.helper
 * @author 345 QQ:1831712732
 * @time 2020/12/10 22:56
 * @description
 */
class SkinCompatImageHelper(val view: ImageView) : SkinCompatHelper() {

    var src = INVALID_ID

    fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        obtainStyledAttributes(
            view, attrs, R.styleable.skinImageHelper, defStyleAttr, 0
        ) {
            src = it.getResourceId(
                R.styleable.skinImageHelper_android_src, INVALID_ID
            )
        }
        applySkin()
    }


    override fun applySkin() {
        setImage(src)
    }

    private fun setImage(res: Int) {
        if (res == INVALID_ID) return
        val drawable = SkinCompatResources.getDrawable(res)
        drawable?.run {
            val paddingleft = view.paddingLeft
            val paddingTop = view.paddingTop
            val paddingRight = view.paddingRight
            val paddingBottom = view.paddingBottom
            ViewCompat.setBackground(view, drawable)
            view.setPadding(paddingleft, paddingTop, paddingRight, paddingBottom)
            return@run
        }
        val color = SkinCompatResources.getColor(res)
        color?.run {
            view.setBackgroundColor(color)
        }
    }
}
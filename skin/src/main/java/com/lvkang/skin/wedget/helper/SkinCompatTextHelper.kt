package com.lvkang.skin.wedget.helper

import android.util.AttributeSet
import android.widget.TextView
import com.lvkang.skin.R
import com.lvkang.skin.ktx.obtainStyledAttributes
import com.lvkang.skin.ktx.px2dip
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.util.SkinLog
import com.lvkang.skin.wedget.SkinCompatHelper

/**
 * @name SkinCompatImageHelper
 * @package com.lvkang.skin.wedget.helper
 * @author 345 QQ:1831712732
 * @time 2020/12/10 23:10
 * @description
 */
open class SkinCompatTextHelper(private val view: TextView) : SkinCompatHelper() {

    internal var sizeId = INVALID_ID
    internal var textColorId = INVALID_ID
    internal var textId = INVALID_ID

    open fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        obtainStyledAttributes(
            view, attrs, R.styleable.skinTextHelper, defStyleAttr, 0
        ) {
            sizeId = it.getResourceId(R.styleable.skinTextHelper_android_textSize, INVALID_ID)
            textColorId = it.getResourceId(R.styleable.skinTextHelper_android_textColor, INVALID_ID)
            textId = it.getResourceId(R.styleable.skinTextHelper_android_text, INVALID_ID)
        }
        applySkin()
    }


    override fun applySkin() {
        setSize(sizeId)
        setText(textId)
        setTextColor(textColorId)
    }

    private fun setText(res: Int) {
        if (res == INVALID_ID) return
        val string = SkinCompatResources.getString(res)
        string?.run { view.text = string }
    }

    private fun setTextColor(res: Int) {
        if (res == INVALID_ID) return
        val color = SkinCompatResources.getColor(res)
        color?.run { view.setTextColor(this) }
    }

    private fun setSize(res: Int) {
        if (res == INVALID_ID) return
        val size = SkinCompatResources.getDimension(res)
        size?.run { view.textSize = px2dip(this) }
    }

}
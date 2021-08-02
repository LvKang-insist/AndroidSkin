package com.lvkang.skin.wedget.androidx

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.lvkang.skin.wedget.helper.SkinCompatBackgroundHelper
import com.lvkang.skin.wedget.SkinCompatSupportable
import com.lvkang.skin.wedget.helper.SkinCompatImageHelper

/**
 * @name AppXImageView
 * @package com.lvkang.skin.wedget.androidx
 * @author 345 QQ:1831712732
 * @time 2020/12/10 22:45
 * @description
 */
class ImageViewX : AppCompatImageView, SkinCompatSupportable {


    private val mImageHelper by lazy { SkinCompatImageHelper(this) }
    private val backgroundHelper by lazy { SkinCompatBackgroundHelper(this) }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mImageHelper.loadFromAttributes(attrs, defStyleAttr)
        backgroundHelper.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun applySkin() {
        mImageHelper.applySkin()
        backgroundHelper.applySkin()
    }
}
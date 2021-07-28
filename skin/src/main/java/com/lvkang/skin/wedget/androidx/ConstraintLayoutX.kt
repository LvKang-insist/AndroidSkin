package com.lvkang.skin.wedget.androidx

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.lvkang.skin.wedget.SkinCompatSupportable
import com.lvkang.skin.wedget.helper.SkinCompatBackgroundHelper

/**
 * @name ConstraintLayoutX
 * @package com.lvkang.skin.wedget.androidx
 * @author 345 QQ:1831712732
 * @time 2021/07/28 16:59
 * @description
 */
class ConstraintLayoutX : ConstraintLayout, SkinCompatSupportable {

    private val mBackgroundHelper by lazy {
        SkinCompatBackgroundHelper(this)
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mBackgroundHelper.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun applySkin() {
        mBackgroundHelper.applySkin()
    }
}
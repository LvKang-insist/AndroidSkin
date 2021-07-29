package com.lvkang.skin.wedget.android

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.lvkang.skin.wedget.helper.SkinCompatBackgroundHelper
import com.lvkang.skin.wedget.SkinCompatSupportable

/**
 * @name SkinSupportView
 * @package com.lvkang.skin.wedget.support
 * @author 345 QQ:1831712732
 * @time 2020/12/01 23:34
 * @description
 */
class ViewX : View, SkinCompatSupportable {

    private val mBackgroundHelper by lazy {
        SkinCompatBackgroundHelper(this)
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        mBackgroundHelper.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun applySkin() {
        mBackgroundHelper.applySkin()
    }
}
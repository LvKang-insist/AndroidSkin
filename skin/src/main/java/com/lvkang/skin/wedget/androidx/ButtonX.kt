package com.lvkang.skin.wedget.androidx

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.lvkang.skin.wedget.SkinCompatSupportable
import com.lvkang.skin.wedget.helper.SkinCompatBackgroundHelper
import com.lvkang.skin.wedget.helper.SkinCompatTextHelper

/**
 * @name AppxButton
 * @package com.lvkang.skin.wedget.androidx
 * @author 345 QQ:1831712732
 * @time 2020/12/10 23:30
 * @description
 */
class ButtonX : AppCompatButton, SkinCompatSupportable {

    private val skinTextHelper by lazy(LazyThreadSafetyMode.NONE) { SkinCompatTextHelper(this) }
    private val mBackgroundHelper by lazy { SkinCompatBackgroundHelper(this) }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        skinTextHelper.loadFromAttributes(attrs, defStyleAttr)
        mBackgroundHelper.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun applySkin() {
        skinTextHelper.applySkin()
        mBackgroundHelper.applySkin()
    }
}
package com.lvkang.skin.wedget.androidx.cardview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.lvkang.skin.wedget.SkinCompatSupportable
import com.lvkang.skin.wedget.helper.SkinCompatBackgroundHelper
import com.lvkang.skin.wedget.helper.SkinCompatCardHelper
import com.lvkang.skin.wedget.helper.SkinCompatTextHelper

/**
 * @name CardViewX
 * @package com.lvkang.skin.wedget.androidx
 * @author 345 QQ:1831712732
 * @time 2021/08/03 21:57
 * @description
 */
internal class CardViewX : CardView, SkinCompatSupportable {

    private val skinCompatCardHelper by lazy { SkinCompatCardHelper(this) }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        skinCompatCardHelper.loadFromAttributes(attrs, defStyleAttr)
    }

    override fun applySkin() {
        skinCompatCardHelper.applySkin()
    }
}
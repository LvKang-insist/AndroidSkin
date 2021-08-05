package com.lvkang.skin.wedget.helper

import android.util.AttributeSet
import android.widget.EditText
import com.lvkang.skin.R
import com.lvkang.skin.ktx.obtainStyledAttributes
import com.lvkang.skin.resource.SkinCompatResources

/**
 * @name SkinCompatEditTextHelpter
 * @package com.lvkang.skin.wedget.helper
 * @author 345 QQ:1831712732
 * @time 2021/08/02 16:51
 * @description
 */
class SkinCompatEditTextHelpter(private val edit: EditText) : SkinCompatTextHelper(edit) {
    private var hintId = INVALID_ID
    override fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        obtainStyledAttributes(
            edit, attrs, R.styleable.skinTextHelper, defStyleAttr, 0
        ) {
            sizeId = it.getResourceId(R.styleable.skinTextHelper_android_textSize, INVALID_ID)
            textColorId = it.getResourceId(R.styleable.skinTextHelper_android_textColor, INVALID_ID)
            textId = it.getResourceId(R.styleable.skinTextHelper_android_text, INVALID_ID)
            hintId = it.getResourceId(R.styleable.skinTextHelper_android_hint, INVALID_ID)
        }
        applySkin()
    }

    override fun applySkin() {
        super.applySkin()
        setHintText(hintId)
    }

    private fun setHintText(res: Int) {
        if (res == INVALID_ID) return
        val string = SkinCompatResources.getString(res)
        string?.run { edit.hint = this }
    }

}
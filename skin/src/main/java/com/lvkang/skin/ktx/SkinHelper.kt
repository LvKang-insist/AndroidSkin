package com.lvkang.skin.ktx

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import java.lang.Exception

/**
 * @name SkinHelper
 * @package com.lvkang.skin.ktx
 * @author 345 QQ:1831712732
 * @time 2020/11/29 16:40
 * @description
 */

inline fun obtainStyledAttributes(
    view: View,
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    @AttrRes defstyleAttr: Int,
    @StyleRes defStyleRes: Int,
    crossinline block: (TypedArray) -> Unit
) {
    val a =
        view.context.obtainStyledAttributes(set, attrs, defstyleAttr, defStyleRes)
    tryCache { block(a) }
    a.recycle()
}

inline fun tryCache(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
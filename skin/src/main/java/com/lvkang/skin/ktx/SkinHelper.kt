package com.lvkang.skin.ktx

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import com.lvkang.skin.SkinManager
import com.lvkang.skin.config.SkinPreUtils
import java.io.File
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
    tryCatch { block(a) }
    a.recycle()
}

inline fun tryCatch(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(pxValue: Float): Float {
    val scale: Float = SkinManager.getContext().resources.displayMetrics.density
    return (pxValue / scale + 0.5f)
}


/**
 * 文件是否存在，true 表示存在
 */
fun isFile(filePath: String): Boolean {
    if (!File(filePath).exists()) {
        return false
    }
    return true
}

fun pathName(path: String): String {
    val name = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."))
    val suffix = path.substring(path.lastIndexOf(".") + 1, path.length)
    return "$name.$suffix"
}
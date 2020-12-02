package com.lvkang.skin.inflater

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull

/**
 * @name SkinLayoutInflater
 * @package com.lvkang.skin.inflater
 * @author 345 QQ:1831712732
 * @time 2020/11/29 14:18
 * @description
 */
interface SkinLayoutInflater {
    fun createView(@NonNull context: Context, name: String, @NonNull attres: AttributeSet): View?
}
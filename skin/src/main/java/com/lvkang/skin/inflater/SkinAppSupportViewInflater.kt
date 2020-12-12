package com.lvkang.skin.inflater

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.lvkang.skin.wedget.androidx.AppxImageView

/**
 * @name SkinAppSupportViewInflater
 * @package com.lvkang.skin.inflater
 * @author 345 QQ:1831712732
 * @time 2020/12/13 01:11
 * @description
 */
class SkinAppSupportViewInflater : SkinLayoutInflater {
    override fun createView(context: Context, name: String, attres: AttributeSet): View? {
        val view: View? = null
        when (name) {
            "ImageView" -> return AppxImageView(context, attres)
        }
        return view
    }
}
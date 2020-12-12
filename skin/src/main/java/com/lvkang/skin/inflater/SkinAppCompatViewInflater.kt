package com.lvkang.skin.inflater

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.lvkang.skin.wedget.androidx.AppxButton
import com.lvkang.skin.wedget.androidx.AppxImageView
import com.lvkang.skin.wedget.androidx.AppxSkinView

/**
 * @name SkinAppCompatViewInflater
 * @package com.lvkang.skin.inflater
 * @author 345 QQ:1831712732
 * @time 2020/12/01 23:29
 * @description
 */
class SkinAppCompatViewInflater : SkinLayoutInflater {
    @Suppress("PrivatePropertyName")
    private val TAG = "SkinAppCompatViewInflat"
    override fun createView(context: Context, name: String, attres: AttributeSet): View? {
        return createViewFrom(context, name, attres)
    }

    private fun createViewFrom(context: Context, name: String, attres: AttributeSet): View? {
        var view: View? = null
        when (name) {
            "View" -> view = AppxSkinView(context, attres)
            "androidx.appcompat.widget.AppCompatImageView" -> view = AppxImageView(context, attres)
            "androidx.appcompat.widget.AppCompatButton" -> view = AppxButton(context, attres)
        }

        return view
    }
}
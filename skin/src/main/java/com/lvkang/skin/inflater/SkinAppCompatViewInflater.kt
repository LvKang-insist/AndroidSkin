package com.lvkang.skin.inflater

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.lvkang.skin.wedget.SkinCompatSupportable
import com.lvkang.skin.wedget.support.SkinSupportView

/**
 * @name SkinAppCompatViewInflater
 * @package com.lvkang.skin.inflater
 * @author 345 QQ:1831712732
 * @time 2020/12/01 23:29
 * @description
 */
class SkinAppCompatViewInflater : SkinLayoutInflater {
    override fun createView(context: Context, name: String, attres: AttributeSet): View? {
        return createViewFrom(context, name, attres)
    }

    private fun createViewFrom(context: Context, name: String, attres: AttributeSet): View? {
        var view: View? = null
        if (name.contains(".")) return null

        when (name) {
            "View" -> view = SkinSupportView(context, attres)
        }

        return view
    }
}
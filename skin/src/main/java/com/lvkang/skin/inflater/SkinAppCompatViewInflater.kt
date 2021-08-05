package com.lvkang.skin.inflater

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.lvkang.skin.util.SkinLog
import com.lvkang.skin.wedget.android.FrameLayoutX
import com.lvkang.skin.wedget.android.RelativeLayoutX
import com.lvkang.skin.wedget.android.ViewX
import com.lvkang.skin.wedget.androidx.*
import com.lvkang.skin.wedget.androidx.cardview.CardViewX

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
        when (name) {
            "View" -> return ViewX(context, attres)
            "ImageView" -> return ImageViewX(context, attres)
            "Button" -> return ButtonX(context, attres)
            "EditText" -> return EditTextX(context, attres)
            "TextView" -> return TextViewX(context, attres)
            "FrameLayout" -> return FrameLayoutX(context, attres)
            "RelativeLayout" -> return RelativeLayoutX(context, attres)
            "ScrollView" -> return NestedScrollViewX(context, attres)
            "androidx.appcompat.widget.AppCompatImageView" -> return ImageViewX(context, attres)
            "androidx.appcompat.widget.AppCompatButton" -> return ButtonX(context, attres)
            "androidx.appcompat.widget.AppCompatTextView" -> return TextViewX(context, attres)
            "androidx.appcompat.widget.AppCompatEditText" -> return EditTextX(context, attres)
            "androidx.cardview.widget.CardView" -> return CardViewX(context, attres)
            "androidx.core.widget.NestedScrollView" -> {
                return NestedScrollViewX(context, attres)
            }
            "androidx.constraintlayout.widget.ConstraintLayout" -> {
                return ConstraintLayoutX(context, attres)
            }
            "androidx.appcompat.widget.LinearLayoutCompat" -> {
                return LinearLayoutCompat(context, attres)
            }
        }

        return null
    }
}
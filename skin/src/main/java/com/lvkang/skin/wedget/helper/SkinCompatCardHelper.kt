package com.lvkang.skin.wedget.helper

import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.lvkang.skin.ktx.obtainStyledAttributes
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.wedget.SkinCompatHelper

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.lvkang.skin.R
import com.lvkang.skin.util.SkinLog
import com.lvkang.skin.wedget.androidx.cardview.RoundRectDrawable


/**
 * @name SkinCompatCardHelper
 * @package com.lvkang.skin.wedget.helper
 * @author 345 QQ:1831712732
 * @time 2021/08/04 10:42
 * @description
 */
class SkinCompatCardHelper(private val view: CardView) : SkinCompatHelper() {

    private var appBackgroundResId = INVALID_ID
    private var appCardCornerRadiusResId = INVALID_ID
    private var appCardElevationResId = INVALID_ID


    private var radius = 0f
    private var colorStateList: ColorStateList? = null


    fun loadFromAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        obtainStyledAttributes(
            view, attrs, R.styleable.CardView, defStyleAttr,   R.style.CardView
        ) {
            appBackgroundResId = it.getResourceId(
                R.styleable.CardView_cardBackgroundColor, INVALID_ID
            )
            appCardCornerRadiusResId = it.getResourceId(
                R.styleable.CardView_cardCornerRadius, INVALID_ID
            )
            appCardElevationResId = it.getResourceId(
                R.styleable.CardView_cardElevation, INVALID_ID
            )
            //缓存默认设置的圆角
            colorStateList =
                it.getColorStateList(R.styleable.CardView_cardBackgroundColor)
            SkinLog.log(colorStateList.toString())
            radius = it.getDimension(R.styleable.CardView_cardElevation, 0f)
        }
        applySkin()
    }

    override fun applySkin() {
        setElevation(appCardElevationResId)
        setBackgroundAndRadius(appBackgroundResId, appCardCornerRadiusResId)
    }

    private fun setElevation(resId: Int) {
        if (resId == INVALID_ID) return
        val elevation = SkinCompatResources.getDimension(resId) ?: 0f
        view.clipToOutline = true
        view.elevation = elevation
    }

    private fun setBackgroundAndRadius(
        bgRes: Int,
        radiusRes: Int
    ) {
        //如果没有使用皮肤
        if (bgRes == INVALID_ID) {
            //并且没有缓存，则根据主题获取背景等属性
            if (colorStateList == null) {
                // There isn't one set, so we'll compute one based on the theme
                val aa: TypedArray =
                    view.context.obtainStyledAttributes(intArrayOf(android.R.attr.colorBackground))
                val themeColorBackground = aa.getColor(0, 0)
                aa.recycle()
                // If the theme colorBackground is light, use our own light color, otherwise dark
                val hsv = FloatArray(3)
                Color.colorToHSV(themeColorBackground, hsv)
                colorStateList = ColorStateList.valueOf(
                    if (hsv[2] > 0.5f)
                        ResourcesCompat.getColor(
                            view.resources, R.color.cardview_light_background, null
                        )
                    else
                        ResourcesCompat.getColor(
                            view.resources, R.color.cardview_dark_background, null
                        )
                )
            }
        } else {
            SkinCompatResources.getColorStateList(bgRes)?.run {
                colorStateList = this
            }
        }
        if (radiusRes != INVALID_ID)
            SkinCompatResources.getDimension(radiusRes)?.run {
                radius = this
            }
        if (colorStateList != null) {
            val drawable = RoundRectDrawable(colorStateList, radius)
            view.background = drawable
        }
    }
}
package com.lvkang.skin.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat

/**
 * @name SkinNoneLoaderImpl
 * @package com.lvkang.skin.resource
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:22
 * @description
 */
class SkinNoneLoaderImpl : SkinLoaderStrategy() {
    override fun loadSkin(skinName: String): String? = ""
    override fun getType(): SkinLoadStrategy = SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE
    override fun getColor(context: Context, skinName: String, resId: Int): Int {
        return ResourcesCompat.getColor(context.resources, resId, null)
    }
    override fun getDrawable(context: Context, skinName: String, resId: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, resId, null)
    }
}
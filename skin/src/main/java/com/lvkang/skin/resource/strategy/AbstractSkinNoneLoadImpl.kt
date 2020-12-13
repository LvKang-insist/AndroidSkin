package com.lvkang.skin.resource.strategy

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.lvkang.skin.SkinManager
import com.lvkang.skin.resource.AbstractSkinLoadStrategy
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.resource.SkinLoadStrategy

/**
 * @name SkinNoneLoaderImpl
 * @package com.lvkang.skin.resource.strategy
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:22
 * @description
 */
class AbstractSkinNoneLoadImpl : AbstractSkinLoadStrategy() {
    override fun loadSkin(skinName: String): String? {
        SkinCompatResources.resetSkin(SkinManager.getContext().resources, this)
        return ""
    }

    override fun getType(): SkinLoadStrategy = SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE
    override fun getColor(context: Context, skinName: String, resId: Int): Int {
        return ResourcesCompat.getColor(context.resources, resId, null)
    }

    override fun getDrawable(context: Context, skinName: String, resId: Int): Drawable? {
        return ResourcesCompat.getDrawable(context.resources, resId, null)
    }
}
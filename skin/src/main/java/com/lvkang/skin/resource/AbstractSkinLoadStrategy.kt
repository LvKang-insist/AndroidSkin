package com.lvkang.skin.resource

import android.content.Context
import android.graphics.drawable.Drawable
import com.lvkang.skin.SkinManager
import java.io.File

/**
 * @name SkinLoaderStrategy
 * @package com.lvkang.skin.resource
 * @author 345 QQ:1831712732
 * @time 2020/12/07 00:24
 * @description 皮肤包加载策略
 */


abstract class AbstractSkinLoadStrategy {

    val skinFileDir = "${SkinManager.getApplication().getExternalFilesDir("file")}${File.separator}"

    /**
     * 加载皮肤
     * @return 不等于 null 且 length 大于0 表示皮肤加载成功
     *         length == 0 表示使用的是 SKIN_LOADER_STRATEGY_NONE，即为没有加载皮肤，使用 app 内部资源
     *         等于 null 表示皮肤加载失败
     */
    abstract fun loadSkin(vararg any: String?): String?

    /**
     * @return 加载策略
     */
    abstract fun getType(): SkinLoadStrategyEnum

    /**
     *@return 皮肤包中资源的名称
     */
    open fun getSkinResName(): String? = null

    /**
     * 通过重写此方法可返回自定义颜色
     * @return color
     */
    open fun getColor(context: Context, skinName: String, resId: Int): Int =
        SkinCompatResources.NOT_ID

    /**
     * 通过重写此方法可返回自定义drawable
     * @return drawable
     */
    open fun getDrawable(context: Context, skinName: String, resId: Int): Drawable? = null

    open fun getDimension(context: Context, skinName: String, resId: Int): Float? = null

    open fun getString(context: Context, skinName: String, resId: Int): String? = null

}
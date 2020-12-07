package com.lvkang.skin.resource

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.lvkang.skin.SkinManager

/**
 * @name SkinResource
 * @package com.lvkang.skin
 * @author 345 QQ:1831712732
 * @time 2020/11/24 23:31
 * @description 皮肤的资源管理器
 */
@SuppressLint("DiscouragedPrivateApi")
object SkinCompatResources {
    const val NOT_ID = 0
    private lateinit var resources: Resources
    private lateinit var packageName: String
    private lateinit var skinName: String
    private lateinit var loaderStrategy: SkinLoaderStrategy
    private val context by lazy { SkinManager.getContext() }
    private var isDefaultSkin = true


    fun resetSkin(
        resources: Resources,
        loaderStrategy: SkinLoaderStrategy
    ) {
        isDefaultSkin = true
        this.resources = resources
        this.packageName = ""
        this.skinName = ""
        this.loaderStrategy = loaderStrategy
    }


    fun setupSkin(
        resources: Resources,
        packageName: String,
        skinName: String,
        loaderStrategy: SkinLoaderStrategy
    ) {
        this.resources = resources
        this.packageName = packageName
        this.skinName = skinName
        this.loaderStrategy = loaderStrategy
        isDefaultSkin = false
    }

    /**
     * 通过名字获取 Drawable
     */
    fun getDrawable(resId: Int): Drawable? {
        tryCatch {
            val drawable = loaderStrategy.getDrawable(context, skinName, resId)
            if (drawable != null) return drawable
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return ResourcesCompat.getDrawable(resources, skinResId, null)
            }
            return ResourcesCompat.getDrawable(context.resources, resId, null)
        }
        return null
    }

    /** 通过名字获取颜色 */
    fun getColor(resId: Int): Int? {
        tryCatch {
            val color = loaderStrategy.getColor(context, skinName, resId)
            if (color != NOT_ID) return color
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return ResourcesCompat.getColor(resources, skinResId, null)
            }
            return ResourcesCompat.getColor(context.resources, resId, null)
        }
        return null
    }

    fun getSkinResId(resId: Int): Int {
        val resName = loaderStrategy.getSkinResName() ?: resources.getResourceEntryName(resId)
        val resType = resources.getResourceTypeName(resId)
        return resources.getIdentifier(resName, resType, packageName)
    }

    private inline fun <T> tryCatch(block: () -> T): T? {
        return try {
            block()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }
}
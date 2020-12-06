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

    private lateinit var resources: Resources
    private lateinit var packageName: String
    private lateinit var loaderStrategy: SkinLoaderStrategy
    private var isDefaultSkin = true

    fun resetSkin(
        resources: Resources,
        loaderStrategy: SkinLoaderStrategy
    ) {
        isDefaultSkin = true
        this.resources = resources
        this.packageName = ""
        this.loaderStrategy = loaderStrategy
    }


    fun setupSkin(
        resources: Resources,
        packageName: String,
        loaderStrategy: SkinLoaderStrategy
    ) {
        this.resources = resources
        this.packageName = packageName
        this.loaderStrategy = loaderStrategy
        isDefaultSkin = false
    }

    /**
     * 通过名字获取 Drawable
     */
    fun getDrawable(resId: Int): Drawable? {
        tryCatch {
            //如果不是默认皮肤
            if (!SkinManager.getIsDefaultSkin()) {
                val resName = resources.getResourceEntryName(resId)
                val resType = resources.getResourceTypeName(resId)
                val id = resources.getIdentifier(resName, resType, packageName)
                if ("drawable" == resType) {
                    return ResourcesCompat.getDrawable(resources, id, null)
                }
            } else {
                return ResourcesCompat.getDrawable(resources, resId, null)
            }
        }
        return null
    }

    /** 通过名字获取颜色 */
    fun getColor(resId: Int): Int? {
        tryCatch {
            if (!SkinManager.getIsDefaultSkin()) {
                val resName = resources.getResourceEntryName(resId)
                val resType = resources.getResourceTypeName(resId)
                val id = resources.getIdentifier(resName, resType, packageName)
                if ("color" == resType) {
                    return ResourcesCompat.getColor(resources, id, null)
                }
            } else {
                return ResourcesCompat.getColor(resources, resId, null)
            }
        }
        return null
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
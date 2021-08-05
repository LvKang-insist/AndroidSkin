package com.lvkang.skin.resource

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.lvkang.skin.SkinManager
import com.lvkang.skin.ktx.tryCatch
import com.lvkang.skin.util.SkinLog

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
    private lateinit var loadStrategyAbstract: AbstractSkinLoadStrategy
    private val context by lazy { SkinManager.getContext() }
    var isDefaultSkin = true


    fun resetSkin(
        resources: Resources,
        loadStrategyAbstract: AbstractSkinLoadStrategy
    ) {
        isDefaultSkin = true
        this.resources = resources
        this.packageName = ""
        this.skinName = ""
        this.loadStrategyAbstract = loadStrategyAbstract
    }


    fun setupSkin(
        resources: Resources,
        packageName: String,
        skinName: String,
        loadStrategyAbstract: AbstractSkinLoadStrategy
    ) {
        this.resources = resources
        this.packageName = packageName
        this.skinName = skinName
        this.loadStrategyAbstract = loadStrategyAbstract
        isDefaultSkin = false
    }


    /**
     * 获取 String
     */
    fun getString(resId: Int): String? {
        tryCatch {
            val string = loadStrategyAbstract.getString(context, skinName, resId)
            if (string != null) return string
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return resources.getString(skinResId)
            } else {
                return context.resources.getString(resId)
            }
        }
        return null
    }

    /**
     * 获取 Dimension
     */
    fun getDimension(resId: Int): Float? {
        tryCatch {
            val float = loadStrategyAbstract.getDimension(context, skinName, resId)
            if (float != null) return float
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return resources.getDimension(skinResId)
            } else {
                return context.resources.getDimension(resId)
            }
        }
        return null
    }

    /**
     * 获取 Drawable
     */
    fun getDrawable(resId: Int): Drawable? {
        tryCatch {
            val drawable = loadStrategyAbstract.getDrawable(context, skinName, resId)
            if (drawable != null) return drawable
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return ResourcesCompat.getDrawable(resources, skinResId, null)
            } else {
                return ResourcesCompat.getDrawable(context.resources, resId, null)
            }
        }
        return null
    }

    /** 获取 Color */
    fun getColor(resId: Int): Int? {
        tryCatch {
            val color = loadStrategyAbstract.getColor(context, skinName, resId)
            if (color != NOT_ID) return color
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return ResourcesCompat.getColor(resources, skinResId, null)
            } else {
                return ResourcesCompat.getColor(context.resources, resId, null)
            }
        }
        return null
    }

     fun getColorStateList(resId: Int): ColorStateList? {
        tryCatch {
            if (!isDefaultSkin) {
                val skinResId = getSkinResId(resId)
                if (skinResId != NOT_ID)
                    return ResourcesCompat.getColorStateList(resources, skinResId, null)
            } else {
                return ResourcesCompat.getColorStateList(context.resources, resId, null)
            }
        }
        return null
    }

    private fun getSkinResId(resId: Int): Int {
        return try {
            val resName =
                loadStrategyAbstract.getSkinResName() ?: context.resources.getResourceEntryName(
                    resId
                )
            val resType = context.resources.getResourceTypeName(resId)
            SkinLog.log("$resId  $resName $resType")
            resources.getIdentifier(resName, resType, packageName)
        } catch (e: Exception) {
            SkinLog.log(e.message ?: "Not Font resId $resId")
            NOT_ID
        }
    }

    /** 获取皮肤包 resources */
    @SuppressLint("DiscouragedPrivateApi")
    fun getSkinResources(skinPath: String): Resources? {
        return try {
            val superRes = SkinManager.getApplication().resources
            val asset = AssetManager::class.java.newInstance()
            val method =
                AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.invoke(asset, skinPath)
            Resources(asset, superRes.displayMetrics, superRes.configuration)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取皮肤包名
     */
    fun getSkinPackageName(skinPath: String): String? {
        return SkinManager.getContext().packageManager.getPackageArchiveInfo(
            skinPath,
            PackageManager.GET_ACTIVITIES
        )?.packageName
    }


}
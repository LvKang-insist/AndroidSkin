package com.lvkang.skin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat

/**
 * @name SkinResource
 * @package com.lvkang.skin
 * @author 345 QQ:1831712732
 * @time 2020/11/24 23:31
 * @description 皮肤的资源管理器
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
@SuppressLint("DiscouragedPrivateApi")
class SkinResourceManager(private val context: Context, private val skinPath: String?) {

    /**
     * 资源通过这个对象获取
     */
    lateinit var mSkinResource: Resources

    private var mPackageName: String? = null

    init {
        if (skinPath == null || skinPath.isEmpty()) {
            mSkinResource = context.resources
        } else {
            tryCatch {
                val superRes = context.resources
                val asset = AssetManager::class.java.newInstance()
                val method =
                    AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
                method.invoke(asset, skinPath)
                mSkinResource = Resources(asset, superRes.displayMetrics, superRes.configuration)

                // 获取 skinPath 包名
                mPackageName = context.packageManager.getPackageArchiveInfo(
                    skinPath, PackageManager.GET_ACTIVITIES
                )!!.packageName
            }
        }
    }

    /**
     * 通过名字获取 Drawable
     */
    fun getDrawable(resId: Int): Drawable? {
        tryCatch {
            //如果不是默认皮肤
            if (!SkinManager.getIsDefaultSkin()) {
                val resName = mSkinResource.getResourceEntryName(resId)
                val resType = mSkinResource.getResourceTypeName(resId)
                val id = mSkinResource.getIdentifier(resName, resType, mPackageName)
                if ("drawable" == resType) {
                    return ResourcesCompat.getDrawable(mSkinResource, id, null)
                }
            } else {
                return ResourcesCompat.getDrawable(mSkinResource, resId, null)
            }
        }
        return null
    }

    /** 通过名字获取颜色 */
    fun getColor(resId: Int): Int? {
        tryCatch {
            if (!SkinManager.getIsDefaultSkin()) {
                val resName = mSkinResource.getResourceEntryName(resId)
                val resType = mSkinResource.getResourceTypeName(resId)
                val id = mSkinResource.getIdentifier(resName, resType, mPackageName)
                if ("color" == resType) {
                    return ResourcesCompat.getColor(mSkinResource, id, null)
                }
            } else {
                return ResourcesCompat.getColor(mSkinResource, resId, null)
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
package com.lvkang.skin.config

import android.content.pm.PackageManager
import android.text.TextUtils
import com.lvkang.skin.SkinManager
import java.io.File

/**
 * @name SkinFile
 * @package com.lvkang.skin.config
 * @author 345 QQ:1831712732
 * @time 2020/12/07 23:20
 * @description
 */
object SkinUtils {

    /**
     * 获取皮肤包名
     */
    fun getSkinPackageName(skinPath: String): String? {
        return SkinManager.getContext().packageManager.getPackageArchiveInfo(
            skinPath,
            PackageManager.GET_ACTIVITIES
        )?.packageName
    }

    /**
     * 包名是否为空，true 表示不为空
     */
    private fun isPackageName(skinPath: String): Boolean {
        val packageName = SkinManager.getContext().packageManager.getPackageArchiveInfo(
            skinPath, PackageManager.GET_ACTIVITIES
        )!!.packageName
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.clearSkinInfo()
            return false
        }
        return true
    }

    /**
     * 文件是否存在，true 表示存在
     */
    fun isFile(filePath: String): Boolean {
        if (!File(filePath).exists()) {
            SkinPreUtils.clearSkinInfo()
            return false
        }
        return true
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Float {
        val scale: Float = SkinManager.getContext().resources.displayMetrics.density
        return (pxValue / scale + 0.5f)
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(dpValue: Float): Float {
        val scale: Float = SkinManager.getContext().resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }


}
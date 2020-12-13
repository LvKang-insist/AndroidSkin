package com.lvkang.skin.resource.strategy

import android.util.Log
import com.lvkang.skin.SkinManager
import com.lvkang.skin.config.SkinUtils
import com.lvkang.skin.config.SkinPreUtils
import com.lvkang.skin.resource.AbstractSkinLoadStrategy
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.resource.SkinLoadStrategy
import java.io.File
import java.io.IOException

/**
 * @name SkinAssetsLoaderImpl
 * @package com.lvkang.skin.resource.strategy
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:24
 * @description
 */

class AbstractSkinAssetsLoadImpl : AbstractSkinLoadStrategy() {

    override fun loadSkin(skinName: String): String? {
        val skinPath = copyCache(skinName, SkinPreUtils.getSkinCacheDir())
        if (skinPath.isNullOrBlank()) return null
        val resource = SkinManager.getSkinResources(skinPath)
        val packageName = SkinUtils.getSkinPackageName(skinPath)
        if (resource != null && packageName != null) {
            SkinCompatResources.setupSkin(resource, packageName, skinName, this)
            return SkinPreUtils.getSkinCacheDir()
        }
        return null
    }

    override fun getType(): SkinLoadStrategy = SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS

    private fun copyCache(skinName: String, cacheDir: String): String? {
        return try {
            val outFile = File(cacheDir, skinName)
            if (SkinUtils.isFile(outFile.path)) {
                return outFile.path
            }
            val input = SkinManager.getContext().resources.assets.open(skinName)
            input.copyTo(outFile.outputStream())
            outFile.path
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
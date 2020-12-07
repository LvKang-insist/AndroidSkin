package com.lvkang.skin.resource

import com.lvkang.skin.SkinManager
import com.lvkang.skin.config.SkinFile
import com.lvkang.skin.config.SkinPreUtils
import java.io.File
import java.io.IOException

/**
 * @name SkinAssetsLoaderImpl
 * @package com.lvkang.skin.resource
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:24
 * @description
 */

class SkinAssetsLoaderImpl : SkinLoaderStrategy() {

    override fun loadSkin(skinName: String): String? {
        val skinPath = copyCache(skinName, SkinPreUtils.getSkinCacheDir())
        if (skinPath.isNullOrBlank()) return null
        val resource = SkinManager.getSkinResources(skinPath)
        val packageName = SkinFile.getSkinPackageName(skinPath)
        if (resource != null && packageName != null) {
            SkinCompatResources.setupSkin(resource, packageName, this)
            return skinPath
        }
        return null
    }

    override fun getType(): SkinLoadStrategy = SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS

    private fun copyCache(skinName: String, cacheDir: String): String? {
        return try {
            val outFile = File(cacheDir, skinName)
            val input = SkinManager.getContext().resources.assets.open(skinName)
            input.copyTo(outFile.outputStream())
            outFile.path
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
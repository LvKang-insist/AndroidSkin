package com.lvkang.skin.resource.strategy

import com.lvkang.skin.SkinManager
import com.lvkang.skin.ktx.isFile
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
 * @description Assets 加载策略，即 Assets 目录下的皮肤包
 */

class AbstractSkinLoadAssetsImpl : AbstractSkinLoadStrategy() {

    override fun loadSkin(path: String): String? {
        val skinPath = copyCache(
            path, "${SkinManager.getApplication().getExternalFilesDir("file")}${File.separator}"
        )
        if (skinPath.isNullOrBlank()) return null
        val resource = SkinCompatResources.getSkinResources(skinPath)
        val packageName = SkinCompatResources.getSkinPackageName(skinPath)
        if (resource != null && packageName != null) {
            SkinCompatResources.setupSkin(resource, packageName, path, this)
            return skinPath
        }
        return null
    }

    override fun getType(): SkinLoadStrategy = SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS

    private fun copyCache(skinName: String, cacheDir: String): String? {
        return try {
            val outFile = File(cacheDir, skinName)
            if (isFile(outFile.path)) {
                return outFile.path
            }
            val input = SkinManager.getContext().resources.assets.open(skinName)
            input.copyTo(outFile.outputStream())
            outFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
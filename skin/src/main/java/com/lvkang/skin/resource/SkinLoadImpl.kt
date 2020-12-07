package com.lvkang.skin.resource

import com.lvkang.skin.SkinManager
import com.lvkang.skin.config.SkinFile
import com.lvkang.skin.config.SkinPreUtils

/**
 * @name SkinLoadImpl
 * @package com.lvkang.skin.resource
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:27
 * @description
 */
class SkinLoadImpl : SkinLoaderStrategy() {

    override fun loadSkin(skinName: String): String? {
        val skinPath = SkinPreUtils.getSkinCacheDir() + skinName
        val resource = SkinManager.getSkinResources(skinPath)
        val packageName = SkinFile.getSkinPackageName(skinPath)
        if (resource != null && packageName != null) {
            SkinCompatResources.setupSkin(resource, packageName, this)
            return skinPath
        }
        return null
    }


    override fun getType(): SkinLoadStrategy = SkinLoadStrategy.SKIN_LOADER_STARTEGY
}
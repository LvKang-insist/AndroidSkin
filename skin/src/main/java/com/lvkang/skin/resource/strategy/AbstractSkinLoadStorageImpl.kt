package com.lvkang.skin.resource.strategy

import com.lvkang.skin.ktx.pathName
import com.lvkang.skin.resource.AbstractSkinLoadStrategy
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.resource.SkinLoadStrategyEnum

/**
 * @name SkinLoadImpl
 * @package ccom.lvkang.skin.resource.strategy
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:27
 * @description 内部存储加载策略
 */
class AbstractSkinLoadStorageImpl : AbstractSkinLoadStrategy() {

    override fun loadSkin(vararg any: String?): String? {
        val path = any[0]!!
        val resource = SkinCompatResources.getSkinResources(path)
        val packageName = SkinCompatResources.getSkinPackageName(path)
        if (resource != null && packageName != null) {
            SkinCompatResources.setupSkin(resource, packageName, pathName(path), this)
            return path
        }
        return null
    }


    override fun getType(): SkinLoadStrategyEnum = SkinLoadStrategyEnum.SKIN_LOADER_STARTEGY_STORAGE
}
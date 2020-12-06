package com.lvkang.skin.resource

/**
 * @name SkinLoaderStrategy
 * @package com.lvkang.skin.resource
 * @author 345 QQ:1831712732
 * @time 2020/12/07 00:24
 * @description 皮肤包加载策略
 */


interface SkinLoaderStrategy {

    fun loadSkin(): String
    fun getType(): SkinLoadType
}
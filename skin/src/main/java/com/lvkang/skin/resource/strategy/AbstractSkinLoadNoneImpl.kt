package com.lvkang.skin.resource.strategy

import com.lvkang.skin.SkinManager
import com.lvkang.skin.resource.AbstractSkinLoadStrategy
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.resource.SkinLoadStrategyEnum

/**
 * @name SkinNoneLoaderImpl
 * @package com.lvkang.skin.resource.strategy
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:22
 * @description 默认加载策略，即不加载任何皮肤
 */
class AbstractSkinLoadNoneImpl : AbstractSkinLoadStrategy() {
    override fun loadSkin(vararg any: String?): String? {
        SkinCompatResources.resetSkin(SkinManager.getContext().resources, this)
        return null
    }

    override fun getType(): SkinLoadStrategyEnum = SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_NONE
}
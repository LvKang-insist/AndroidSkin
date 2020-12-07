package com.lvkang.skin.listener

/**
 * @name SkinLoadListener
 * @package com.lvkang.skin
 * @author 345 QQ:1831712732
 * @time 2020/12/07 22:31
 * @description
 */
interface SkinLoadListener {

    /** 换肤成功 */
    fun loadSkinSucess()

    /** 加载失败 */
    fun loadSkinFailure(error: String)

    /** 重复加载 */
    fun loadRepeat()
}
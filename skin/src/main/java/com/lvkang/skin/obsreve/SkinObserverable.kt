package com.lvkang.skin.obsreve

/**
 * @name SkinObserverable
 * @package com.lvkang.skin.obsreve
 * @author 345 QQ:1831712732
 * @time 2020/12/01 22:37
 * @description
 */
open class SkinObserverable {
    private val observers by lazy {
        arrayListOf<SkinObserver>()
    }

    fun addSkinObserver(skinObserver: SkinObserver) {
        //如果不包含此观察者，则添加
        if (!observers.contains(skinObserver))
            observers.add(skinObserver)
    }

    fun removeSkinObserver(skinObserver: SkinObserver) {
        observers.remove(skinObserver)
    }

    fun clearSkinObserver() {
        observers.clear()
    }

    fun notifyUpDataSkin() {
        observers.forEach {
            it.applySkin()
        }
    }

    fun skinObserveSize(): Int {
        return observers.size
    }
}
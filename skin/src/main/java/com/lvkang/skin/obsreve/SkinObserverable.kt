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
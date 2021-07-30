package com.lvkang.skin

import android.app.Application
import android.content.Context
import com.lvkang.skin.app.SkinActivityLifecycle
import com.lvkang.skin.config.SkinPreUtils
import com.lvkang.skin.inflater.SkinLayoutInflater
import com.lvkang.skin.ktx.pathName
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.obsreve.SkinObserverable
import com.lvkang.skin.resource.*
import com.lvkang.skin.resource.SkinLoadStrategyEnum
import com.lvkang.skin.resource.strategy.AbstractSkinLoadAssetsImpl
import com.lvkang.skin.resource.strategy.AbstractSkinLoadStorageImpl
import com.lvkang.skin.resource.strategy.AbstractSkinLoadNoneImpl
import com.lvkang.skin.resource.strategy.AbstractSkinLoadZipImpl
import com.lvkang.skin.util.SkinLog
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
object SkinManager : SkinObserverable() {

    private lateinit var application: Application
    private val inflaters = arrayListOf<SkinLayoutInflater>()
    private val startegy = mutableMapOf<String, AbstractSkinLoadStrategy>()
    private var isAutoLoadSkin = true

    fun init(context: Application): SkinManager {
        application = context
        return this
    }

    /** 自定义 View 时，可选择添加一个{@link SkinLayoutInflater} */
    fun addInflaters(inflater: SkinLayoutInflater): SkinManager {
        inflaters.add(inflater)
        return this
    }


    fun getInflaters(): ArrayList<SkinLayoutInflater> {
        return inflaters
    }

    /** 设置是否使用非继承的方式实现换肤，默认为 false */
    fun setAutoLoadSkin(isAutoLoadSkin: Boolean): SkinManager {
        this.isAutoLoadSkin = isAutoLoadSkin
        return this
    }

    /** 初始化完成后必须调用 */
    fun build() {
        startegy[SkinLoadStrategyEnum.SKIN_LOADER_STARTEGY_STORAGE.name] = AbstractSkinLoadStorageImpl()
        startegy[SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_NONE.name] = AbstractSkinLoadNoneImpl()
        startegy[SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ASSETS.name] =
            AbstractSkinLoadAssetsImpl()
        startegy[SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ZIP.name] = AbstractSkinLoadZipImpl()

        if (isAutoLoadSkin) application.registerActivityLifecycleCallbacks(SkinActivityLifecycle())
        val name = SkinPreUtils.getSkinName()
        val path = SkinPreUtils.getSkinPath()
        val loadStrategy = SkinPreUtils.getSkinStrategy()
        if (name != null && loadStrategy != null) {
            val strategy = getStrategyType(loadStrategy)
            if (strategy != null) {
                when (strategy) {
                    SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_NONE -> loadNone()
                    SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ASSETS -> {
                        loadAssetsSkin(name, isRepeat = true)
                    }
                    SkinLoadStrategyEnum.SKIN_LOADER_STARTEGY_STORAGE -> {
                        loadStorageSkin(path, isRepeat = true)
                    }
                    SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ZIP -> {
                        loadZipSkin(path, isRepeat = true)
                    }
                }
                return
            }
        }
        loadNone()
    }


    /** 加载默认皮肤 ，即无皮肤 */
    fun loadNone(skinLoadListener: SkinLoadListener? = null) {
        val none = "none"
        val strategy = SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_NONE
        val skinLoaderStrategy = startegy[strategy.name]
        skinLoaderStrategy?.loadSkin(none)
        loadSkin(none, none, strategy, skinLoadListener)
    }

    /**
     * 加载资源文件夹下的皮肤
     * @param name 资源文件名
     * @param skinLoadListener 回调
     * @param isRepeat false 表示要加载的 skin 和当前使用的相同时不重复加载
     */
    fun loadAssetsSkin(
        name: String,
        skinLoadListener: SkinLoadListener? = null,
        isRepeat: Boolean = false,
    ) {
        val strategy = SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ASSETS
        if (compareSkin(name, strategy) && (!isRepeat)) {
            SkinLog.log("Repeat loading")
            skinLoadListener?.loadRepeat()
            return
        }
        val skinLoaderStrategy = startegy[strategy.name]

        CoroutineScope(Dispatchers.IO).launch {
            val skinPath = skinLoaderStrategy?.loadSkin(name)
            launch(Dispatchers.Main) {
                skinPath?.run {
                    loadSkin(skinPath, name, strategy, skinLoadListener)
                } ?: kotlin.run {
                    skinLoadListener?.loadSkinFailure("load failure")
                }
            }
        }
    }

    /**
     * 加载内部存储下的皮肤，必须是沙箱路径
     * @param path skin 绝对路径
     * @param skinLoadListener 回调
     * @param isRepeat false 表示要加载的 skin 和当前使用的相同时不重复加载
     */
    fun loadStorageSkin(
        path: String,
        skinLoadListener: SkinLoadListener? = null,
        isRepeat: Boolean = false
    ) {
        val strategy = SkinLoadStrategyEnum.SKIN_LOADER_STARTEGY_STORAGE
        val name = pathName(path)
        if (compareSkin(name, strategy) && (!isRepeat)) {
            SkinLog.log("Repeat loading")
            skinLoadListener?.loadRepeat()
            return
        }
        val skinLoaderStrategy = startegy[strategy.name]
        CoroutineScope(Dispatchers.IO).launch {
            val skinPath = skinLoaderStrategy?.loadSkin(path)
            launch(Dispatchers.Main) {
                skinPath?.run {
                    loadSkin(skinPath, name, strategy, skinLoadListener)
                } ?: kotlin.run {
                    skinLoadListener?.loadSkinFailure("load failure")
                }
            }
        }
    }

    /**
     * 加载内部存储下的 zpi 皮肤文件，必须是沙箱路径
     * @param path skin 绝对路径
     * @param password zip 文件密码，没有可不传
     * @param skinLoadListener 回调
     * @param isRepeat false 表示要加载的 skin 和当前使用的相同时不重复加载
     */
    fun loadZipSkin(
        path: String,
        password: String? = null,
        skinLoadListener: SkinLoadListener? = null,
        isRepeat: Boolean = false
    ) {
        val strategy = SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ZIP
        val name = pathName(path)
        if (compareSkin(name, strategy) && (!isRepeat)) {
            SkinLog.log("Repeat loading")
            skinLoadListener?.loadRepeat()
            return
        }
        val skinLoaderStrategy = startegy[strategy.name]
        CoroutineScope(Dispatchers.IO).launch {
            val skinPath = skinLoaderStrategy?.loadSkin(path, password)
            launch(Dispatchers.Main) {
                skinPath?.run {
                    loadSkin(skinPath, name, strategy, skinLoadListener)
                } ?: kotlin.run {
                    skinLoadListener?.loadSkinFailure("load failure")
                }
            }
        }
    }

    private fun loadSkin(
        skinPath: String,
        name: String,
        strategyEnum: SkinLoadStrategyEnum,
        skinLoadListener: SkinLoadListener?
    ) {
        notifyUpdateSkin()
        SkinPreUtils.saveSkinStatus(skinPath, name, strategyEnum.name)
        skinLoadListener?.loadSkinSucess()
    }


    private fun compareSkin(skinName: String, strategyEnum: SkinLoadStrategyEnum): Boolean {
        val name = SkinPreUtils.getSkinName()
        val loadStrategy = SkinPreUtils.getSkinStrategy()
        if (name == skinName && strategyEnum.name == loadStrategy) {
            return true
        }
        return false
    }


    private fun notifyUpdateSkin() {
        SkinManager.notifyUpDataSkin()
    }


    fun getApplication(): Application {
        return application
    }

    /**
     * @return true 表示当前为默认皮肤
     */
    fun getIsDefault(): Boolean {
        return SkinCompatResources.isDefaultSkin
    }

    /**
     * @param strategy 策略名称
     * 获取加载策略
     */
    private fun getStrategyType(strategy: String): SkinLoadStrategyEnum? {
        SkinLoadStrategyEnum.values().forEach {
            if (it.name == strategy) {
                return it
            }
        }
        return null
    }


    fun getContext(): Context {
        return application
    }

}

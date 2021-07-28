package com.lvkang.skin

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import com.lvkang.skin.app.SkinActivityLifecycle
import com.lvkang.skin.config.SkinConfig.SKIN_LOAD_ERROR
import com.lvkang.skin.config.SkinPreUtils
import com.lvkang.skin.inflater.SkinLayoutInflater
import com.lvkang.skin.ktx.pathName
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.obsreve.SkinObserverable
import com.lvkang.skin.resource.*
import com.lvkang.skin.resource.SkinLoadStrategy
import com.lvkang.skin.resource.strategy.AbstractSkinAssetsLoadImpl
import com.lvkang.skin.resource.strategy.AbstractSkinLoadStorageImpl
import com.lvkang.skin.resource.strategy.AbstractSkinNoneLoadImpl
import com.lvkang.skin.util.SkinLog
import kotlinx.coroutines.*
import java.lang.Exception

@Suppress("DEPRECATION")
object SkinManager : SkinObserverable() {

    private const val TAG = "SkinManager"
    private lateinit var application: Application
    private val inflaters = arrayListOf<SkinLayoutInflater>()
    private val startegy = mutableMapOf<String, AbstractSkinLoadStrategy>()
    private var isAutoLoadSkin = false

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
        startegy[SkinLoadStrategy.SKIN_LOADER_STARTEGY.name] = AbstractSkinLoadStorageImpl()
        startegy[SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE.name] = AbstractSkinNoneLoadImpl()
        startegy[SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS.name] = AbstractSkinAssetsLoadImpl()

        if (isAutoLoadSkin) application.registerActivityLifecycleCallbacks(SkinActivityLifecycle())
        val name = SkinPreUtils.getSkinName()
        val loadStrategy = SkinPreUtils.getSkinStrategy()
        if (name != null && loadStrategy != null) {
            val strategy = getStrategyType(loadStrategy)
            if (strategy != null) {
                loadSkin(strategy, name, null)
                return
            }
        }
        //如果没有使用皮肤，则加载策略为 SKIN_LOADER_STRATEGY_NONE
        loadSkin(SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE, null, null)
    }

    /**
     * 加载新皮肤
     * @param skinPath 皮肤的名字或路径，当策略为 SKIN_LOADER_STRATEGY_ASSETS 时，传入 name 即可
     */
    fun loadNewSkin(
        strategy: SkinLoadStrategy,
        skinPath: String? = null,
        skinLoadListener: SkinLoadListener? = null
    ) {
        val name = SkinPreUtils.getSkinName()
        val loadStrategy = SkinPreUtils.getSkinStrategy()
        if (name == skinPath && strategy.name == loadStrategy) {
            skinLoadListener?.loadRepeat()
            SkinLog.log("Do not reload the skin!")
            return
        }
        loadSkin(strategy, skinPath, skinLoadListener)
    }

    private fun loadSkin(
        strategy: SkinLoadStrategy,
        path: String? = null,
        skinLoadListener: SkinLoadListener? = null
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val skinLoaderStrategy = startegy[strategy.name]
                when (strategy) {
                    SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE -> {
                        launch(Dispatchers.Main) {
                            skinLoadListener?.loadSkinSucess()
                            notifyUpdateSkin()
                        }
                    }
                    SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS,
                    SkinLoadStrategy.SKIN_LOADER_STARTEGY -> {
                        path?.run {
                            val skinPath = skinLoaderStrategy?.loadSkin(this)
                            launch(Dispatchers.Main) {
                                if (skinPath != null) {
                                    notifyUpdateSkin()
                                    SkinPreUtils.saveSkinStatus(
                                        skinPath,
                                        pathName(skinPath),
                                        strategy.name
                                    )
                                    skinLoadListener?.loadSkinSucess()
                                } else {
                                    skinLoadListener?.loadSkinFailure(SKIN_LOAD_ERROR)
                                }
                            }
                        } ?: throw Resources.NotFoundException("Not Font path")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "loadSkin: loadSkin: The skin resource failed to load!")
                e.printStackTrace()
                skinLoadListener?.loadSkinFailure(SKIN_LOAD_ERROR)
            }
        }
    }

    private fun notifyUpdateSkin() {
        SkinManager.notifyUpDataSkin()
    }


    /**
     * 恢复默认
     */
    fun restoreDefault() {
        loadSkin(SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE, null, object : SkinLoadListener {
            override fun loadSkinSucess() {
                SkinPreUtils.clearSkinInfo()
            }

            override fun loadSkinFailure(error: String) = Unit

            override fun loadRepeat() = Unit
        })
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
    private fun getStrategyType(strategy: String): SkinLoadStrategy? {
        SkinLoadStrategy.values().forEach {
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

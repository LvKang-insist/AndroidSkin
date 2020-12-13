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
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.obsreve.SkinObserverable
import com.lvkang.skin.resource.*
import com.lvkang.skin.resource.SkinLoadStrategy
import com.lvkang.skin.resource.strategy.AbstractSkinAssetsLoadImpl
import com.lvkang.skin.resource.strategy.AbstractSkinLoadImpl
import com.lvkang.skin.resource.strategy.AbstractSkinNoneLoadImpl
import kotlinx.coroutines.*
import java.lang.Exception

@Suppress("DEPRECATION")
object SkinManager : SkinObserverable() {

    private const val TAG = "SkinManager"
    private lateinit var mContext: Application
    private val inflaters = arrayListOf<SkinLayoutInflater>()
    private val startegy = mutableMapOf<String, AbstractSkinLoadStrategy>()
    private var isAutoLoadSkin = false

    fun init(context: Application): SkinManager {
        SkinPreUtils.init(context)
        mContext = context
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
        startegy[SkinLoadStrategy.SKIN_LOADER_STARTEGY.name] = AbstractSkinLoadImpl()
        startegy[SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE.name] = AbstractSkinNoneLoadImpl()
        startegy[SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS.name] = AbstractSkinAssetsLoadImpl()

        if (isAutoLoadSkin) mContext.registerActivityLifecycleCallbacks(SkinActivityLifecycle())
        val name = SkinPreUtils.getSkinName()
        val loadStrategyName = SkinPreUtils.getSkinStrategyName()
        if (name != null && loadStrategyName != null) {
            val loadStrategy = getStrategyType(loadStrategyName)
            if (loadStrategy != null) {
                loadSkin(name, loadStrategy, null)
                return
            }
        }
        //如果没有使用皮肤，则加载策略为 SKIN_LOADER_STRATEGY_NONE
        loadSkin("", SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE, null)
    }

    /**
     * 加载新皮肤
     */
    fun loadNewSkin(
        skinName: String,
        skinLoadStrategy: SkinLoadStrategy,
        skinLoadListener: SkinLoadListener? = null
    ) {
        val name = SkinPreUtils.getSkinName()
        val loadStrategy = SkinPreUtils.getSkinStrategyName()
        if (name == skinName && skinLoadStrategy.name == loadStrategy) {
            skinLoadListener?.loadRepeat()
            Log.d(TAG, "loadNewSkin: Do not reload the skin!")
            return
        }
        loadSkin(skinName, skinLoadStrategy, skinLoadListener)
    }

    private fun loadSkin(
        skinName: String,
        skinLoadStrategy: SkinLoadStrategy,
        skinLoadListener: SkinLoadListener? = null
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val skinLoaderStrategy = startegy[skinLoadStrategy.name]
                val skinPath = skinLoaderStrategy?.loadSkin(skinName)
                launch(Dispatchers.Main) {
                    when {
                        skinPath == null -> {
                            Log.e(TAG, "loadSkin: The skin resource failed to load!")
                            skinLoadListener?.loadSkinFailure(SKIN_LOAD_ERROR)
                        }
                        skinPath.isNotEmpty() -> {
                            //改变皮肤
                            notifyUpdateSkin()
                            //保存皮肤的状态
                            SkinPreUtils.saveSkinStatus(skinPath, skinName, skinLoadStrategy.name)
                            skinLoadListener?.loadSkinSucess()
                        }
                        skinPath.isEmpty() -> {
                            //length==0 ，表示策略为 SKIN_LOADER_STRATEGY_NONE，只需要刷新即可
                            skinLoadListener?.loadSkinSucess()
                            notifyUpdateSkin()
                        }
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
        loadSkin("", SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE, object : SkinLoadListener {
            override fun loadSkinSucess() {
                SkinPreUtils.clearSkinInfo()
            }

            override fun loadSkinFailure(error: String) = Unit

            override fun loadRepeat() = Unit
        })
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

    /**
     * 获取皮肤 resources
     */
    @SuppressLint("DiscouragedPrivateApi")
    fun getSkinResources(skinPath: String): Resources? {
        return try {
            val superRes = mContext.resources
            val asset = AssetManager::class.java.newInstance()
            val method =
                AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.invoke(asset, skinPath)
            Resources(asset, superRes.displayMetrics, superRes.configuration)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getContext(): Context {
        return mContext
    }

}

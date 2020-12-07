package com.lvkang.skin


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import com.lvkang.skin.config.SkinConfig
import com.lvkang.skin.config.SkinPreUtils
import com.lvkang.skin.inflater.SkinLayoutInflater
import com.lvkang.skin.ktx.tryCache
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.obsreve.SkinObserverable
import com.lvkang.skin.resource.*
import kotlinx.coroutines.*
import java.lang.Exception

object SkinManager : SkinObserverable() {

    private const val TAG = "SkinManager"
    private lateinit var mContext: Application
    private val inflaters = arrayListOf<SkinLayoutInflater>()
    private val startegy = mutableMapOf<String, SkinLoaderStrategy>()

    /**
     * 自定义 View 时，可选择添加一个{@link SkinLayoutInflater}
     */
    fun addInflaters(inflater: SkinLayoutInflater): SkinManager {
        inflaters.add(inflater)
        return this
    }


    fun getInflaters(): ArrayList<SkinLayoutInflater> {
        return inflaters
    }

    fun init(context: Application): SkinManager {
        SkinPreUtils.init(context)
        mContext = context
        startegy[SkinLoadStrategy.SKIN_LOADER_STARTEGY.name] = SkinLoadImpl()
        startegy[SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE.name] = SkinNoneLoaderImpl()
        startegy[SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS.name] = SkinAssetsLoaderImpl()

        val name = SkinPreUtils.getSkinName()
        val loadStrategy = SkinPreUtils.getSkinStrategy()
        if (name != null && loadStrategy != null) {
            //如果没有使用皮肤，则加载策略为 SKIN_LOADER_STRATEGY_NONE
            loadSkin("", SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE, null)
        }
        return this
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
        val loadStrategy = SkinPreUtils.getSkinStrategy()
        if (name == skinName && skinLoadStrategy.name == loadStrategy) {
            Log.e(TAG, "loadNewSkin: Do not reload the skin!")
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
            tryCache {
                val skinLoaderStrategy = startegy[skinLoadStrategy.name]
                val skinPath = skinLoaderStrategy?.loadSkin(skinName)
                launch(Dispatchers.Main) {
                    when {
                        skinPath == null -> {
                            skinLoadListener?.loadSkinFailure("The skin resource failed to load!")
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
                            notifyUpdateSkin()
                        }
                    }
                }
                cancel()
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
        SkinPreUtils.clearSkinInfo()
        loadSkin("", SkinLoadStrategy.SKIN_LOADER_STRATEGY_NONE, null)
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

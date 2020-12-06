package com.lvkang.skin


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import com.lvkang.skin.config.SkinConfig
import com.lvkang.skin.config.SkinPreUtils
import com.lvkang.skin.inflater.SkinLayoutInflater
import com.lvkang.skin.obsreve.SkinObserverable
import com.lvkang.skin.resource.SkinCompatResources
import java.io.File

object SkinManager : SkinObserverable() {

    private lateinit var mContext: Application
    private val inflaters = arrayListOf<SkinLayoutInflater>()
    private var isDefaultSkin = true

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

    /** 获取当前是否为默认皮肤 */
    fun getIsDefaultSkin(): Boolean {
        return isDefaultSkin
    }


    fun init(context: Application): SkinManager {
        SkinPreUtils.init(context)
        mContext = context
        //每次打开应用都会到这里来，做一系列的预防，防止皮肤被删除
        val currentSkinPath = SkinPreUtils.getSkinPath()
        isDefaultSkin = false
        return this
    }

    /**
     * 加载新皮肤
     */
    fun loadSkin(skinPath: String): Int {
        if (SkinPreUtils.equalsSkinPath(skinPath)) return SkinConfig.SKIN_CHANGE_NOTHING
        if (!SkinPreUtils.isFile(skinPath)) {
            copyCache(skinPath)
            if (!SkinPreUtils.isFile(skinPath)) return SkinConfig.SKIN_FILE_NOT_FOUND
        }
        if (!isPackageName(skinPath)) return SkinConfig.SKIN_FILE_ERROR
        isDefaultSkin = false
        TODO("未完成：resources 资源加载")
        //改变皮肤
        notifyUpdateSkin()
        //保存皮肤的状态
        SkinPreUtils.saveSkinStatus(skinPath)
        return SkinConfig.SKIN_CHANGE_SUCCESS
    }

    private fun notifyUpdateSkin() {
        SkinManager.notifyUpDataSkin()
    }

    private fun copyCache(cacheDir: String) {
        val outFile = File(cacheDir)
        val input = mContext!!.resources.assets.open("skinHome.skin")
        input.copyTo(outFile.outputStream())
    }

    /**
     * 恢复默认
     */
    fun restoreDefault(): Int {
        SkinPreUtils.getSkinPath() ?: return SkinConfig.SKIN_CHANGE_NOTHING
        SkinPreUtils.clearSkinInfo()
        //当前app资源路径
        val resPath = mContext!!.packageResourcePath
        isDefaultSkin = false
        TODO("未完成：resources 资源加载")
//        skinCompatResources = SkinCompatResources(mContext!!, resPath)
        //设置为默认皮肤
        notifyUpdateSkin()
        return SkinConfig.SKIN_CHANGE_SUCCESS
    }

    /**
     * 包名是否为空，true 表示不为空
     */
    private fun isPackageName(currentSkinPath: String): Boolean {
        val packageName = mContext.packageManager.getPackageArchiveInfo(
            currentSkinPath, PackageManager.GET_ACTIVITIES
        )!!.packageName
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.clearSkinInfo()
            return false
        }
        return true
    }

    /**
     * 获取皮肤 resources
     */
    @SuppressLint("DiscouragedPrivateApi")
    fun getSkinResourceManager(skinPath: String): Resources {
        val superRes = mContext.resources
        val asset = AssetManager::class.java.newInstance()
        val method =
            AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        method.invoke(asset, skinPath)
        return Resources(asset, superRes.displayMetrics, superRes.configuration)
    }

    /**
     * 获取皮肤包名
     */
    fun getSkinPackageName(skinPath: String): String? {
        return mContext.packageManager.getPackageArchiveInfo(
            skinPath,
            PackageManager.GET_ACTIVITIES
        )?.packageName
    }

    fun getContext(): Context {
        return mContext
    }

}

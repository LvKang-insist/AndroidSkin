package com.lvkang.skin


import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import com.lvkang.skin.config.SkinConfig
import com.lvkang.skin.config.SkinPreUtils
import com.lvkang.skin.inflater.SkinLayoutInflater
import com.lvkang.skin.obsreve.SkinObserverable
import java.io.File

object SkinManager : SkinObserverable() {

    private var mContext: Context? = null
    private val inflaters = arrayListOf<SkinLayoutInflater>()
    private var isDefaultSkin = true
    private var skinResourceManager: SkinResourceManager? = null

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

    fun getIsDefaultSkin(): Boolean {
        return isDefaultSkin
    }


    fun init(context: Context): SkinManager {
        SkinPreUtils.init(context)
        mContext = context.applicationContext
        //每次打开应用都会到这里来，做一系列的预防，防止皮肤被删除
        val currentSkinPath = SkinPreUtils.getSkinPath()
        isDefaultSkin = false
        //做一些初始化的工作
        skinResourceManager = SkinResourceManager(mContext!!, currentSkinPath)
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
        skinResourceManager = SkinResourceManager(mContext!!, skinPath)
        //改变皮肤
        checkSkin()
        //保存皮肤的状态
        SkinPreUtils.saveSkinStatus(skinPath)
        return SkinConfig.SKIN_CHANGE_SUCCESS
    }

    private fun checkSkin() {
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
        skinResourceManager = SkinResourceManager(mContext!!, resPath)
        //设置为默认皮肤
        checkSkin()
        return SkinConfig.SKIN_CHANGE_SUCCESS
    }

    /**
     * 包名是否为空，true 表示不为空
     */
    private fun isPackageName(currentSkinPath: String): Boolean {
        val packageName = mContext!!.packageManager.getPackageArchiveInfo(
            currentSkinPath, PackageManager.GET_ACTIVITIES
        )!!.packageName
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.clearSkinInfo()
            return false
        }
        return true
    }

    /**
     * 获取当前皮肤资源
     */
    fun getSkinResourceManager(): SkinResourceManager {
        return skinResourceManager!!
    }
}

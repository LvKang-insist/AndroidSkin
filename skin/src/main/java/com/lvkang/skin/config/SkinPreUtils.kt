package com.lvkang.skin.config

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.lvkang.skin.SkinManager
import java.io.File


/**
 * @name SkinConfig
 * @package com.lvkang.skin.config
 * @author 345 QQ:1831712732
 * @time 2020/11/24 23:31
 * @description
 */
@SuppressLint("StaticFieldLeak")
object SkinPreUtils {

    var context: Context? = null
    fun init(context: Context) {
        SkinPreUtils.context = context.applicationContext
    }


    /**
     * 保存当前皮肤路径
     */
    fun saveSkinPath(skinPath: String?) {
        context!!.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(SkinConfig.SKIN_PATH_NAME, skinPath)
            .apply()
    }

    /**
     * 返回当前皮肤路径
     */
    fun getSkinPath(): String? {
        return context!!.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE)
            .getString(SkinConfig.SKIN_PATH_NAME, null)
    }

    /**
     * 清空皮肤路径
     */
    fun clearSkinInfo() {
        saveSkinPath(null)
    }

    /**
     * 判断皮肤地址是否相同
     */
    fun equalsSkinPath(skinPath: String): Boolean {
        val path = SkinPreUtils.getSkinPath()
        if (path != null && path == skinPath) {
            Toast.makeText(context, "请务重复更换皮肤", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }


    /**
     * 文件是否存在，true 表示存在
     */
    fun isFile(filePath: String): Boolean {
        if (!File(filePath).exists()) {
            clearSkinInfo()
            return false
        }
        return true
    }


    /**
     * 保存当前使用的皮肤
     */
    fun saveSkinStatus(skinPath: String) {
        SkinPreUtils.saveSkinPath(skinPath)
    }


    /**
     * 添加一个标记
     */
    fun setTag(boolean: Boolean) {
        context!!.getSharedPreferences(SkinConfig.TAG, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(SkinConfig.TAG, boolean)
            .apply()
    }

    /**
     * 获取标记
     */
    fun getTag(): Boolean {
        return context!!.getSharedPreferences(SkinConfig.TAG, Context.MODE_PRIVATE)
            .getBoolean(SkinConfig.TAG, false)
    }
}
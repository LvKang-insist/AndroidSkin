package com.lvkang.skin.resource.strategy

import com.lvkang.skin.ktx.isFile
import com.lvkang.skin.ktx.pathName
import com.lvkang.skin.resource.AbstractSkinLoadStrategy
import com.lvkang.skin.resource.SkinCompatResources
import com.lvkang.skin.resource.SkinLoadStrategyEnum
import com.lvkang.skin.util.SkinLog
import net.lingala.zip4j.ZipFile
import java.io.File


/**
 * @name AbstractSkinLoadZipImpl
 * @package com.lvkang.skin.resource.strategy
 * @author 345 QQ:1831712732
 * @time 2021/07/30 14:12
 * @description
 */
class AbstractSkinLoadZipImpl : AbstractSkinLoadStrategy() {

    override fun loadSkin(vararg any: String?): String? {
        val path = any[0]!!
        if (!isFile(path)) {
            return null
        }
        //path包含.skin，说明是app初始化加载的
        if (path.contains(".skin")) {
            return loadSkin(path)
        }
        //判断该文件是否已经被解压过
        val skin = "${path.substring(0, path.lastIndexOf('.'))}.skin"
        if (isFile(skin)) {
            return loadSkin(skin)
        }
        val password = if (any.size > 1) any[1] else null
        val list = try {
            if (password != null) {
                SkinLog.log("$password")
                unzip(File(path), skinFileDir, password)
            } else {
                unzip(File(path), skinFileDir, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        if (list.isNotEmpty()) {
            return loadSkin(list[0].absolutePath)
        }
        return null
    }

    private fun loadSkin(skinPath: String): String? {
        val resource = SkinCompatResources.getSkinResources(skinPath)
        val packageName = SkinCompatResources.getSkinPackageName(skinPath)
        if (resource != null && packageName != null) {
            SkinCompatResources.setupSkin(resource, packageName, pathName(skinPath), this)
            return skinPath
        }
        return null
    }


    override fun getType(): SkinLoadStrategyEnum = SkinLoadStrategyEnum.SKIN_LOADER_STRATEGY_ZIP

    private fun unzip(zipFile: File?, dest: String, passwd: String?): MutableList<File> {
        val zFile = ZipFile(zipFile)

        val destDir = File(dest)
        if (destDir.isDirectory && !destDir.exists()) {
            destDir.mkdir()
        }
        if (zFile.isEncrypted && passwd != null) {
            zFile.setPassword(passwd.toCharArray())
        }
        zFile.extractAll(dest)
        val headerList = zFile.fileHeaders
        val extractedFileList: MutableList<File> = ArrayList()
        for (fileHeader in headerList) {
            if (!fileHeader.isDirectory) {
                extractedFileList.add(File(destDir, fileHeader.fileName))
            }
        }
        return extractedFileList.toMutableList()
    }
}
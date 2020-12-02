package com.lvkang.skin.config

/**
 * @name SkinConfig
 * @package com.lvkang.skin.config
 * @author 345 QQ:1831712732
 * @time 2020/11/24 23:31
 * @description
 */
object SkinConfig {

    const val TAG = "tag"

    //Sp 文件名称
    const val SKIN_INFO_NAME = "skinInfo";

    //保存文件皮肤的key
    const val SKIN_PATH_NAME = "skinPath";

    //不需要改变任何东西
    const val SKIN_CHANGE_NOTHING = -1

    //换肤成功
    const val SKIN_CHANGE_SUCCESS = 1

    //找不到文件
    const val SKIN_FILE_NOT_FOUND = 2

    //文件错误，可能不是apk
    const val SKIN_FILE_ERROR = 3


}
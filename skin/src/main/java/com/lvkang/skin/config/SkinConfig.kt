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

    //保存文件皮肤 path 的 key
    const val SKIN_PATH = "skin_path"

    //保存文件皮肤 name 的 key
    const val SKIN_NAME = "skin_name"

    //保存文件皮肤 strategy 的 key
    const val SKIN_STRATEGY = "skin_strategy"

    //皮肤文件的缓存路径
    const val SKIN_DIR = "skin_dir"

    //不需要改变任何东西
    const val SKIN_CHANGE_NOTHING = -1

    //换肤成功
    const val SKIN_CHANGE_SUCCESS = 1

    /** 重复更换 */
    const val SKIN_CHANGE_REPEAT = 2

    //找不到文件
    const val SKIN_FILE_NOT_FOUND = 3

    //文件错误
    const val SKIN_FILE_ERROR = 4


}
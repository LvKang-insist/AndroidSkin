package com.lvkang.skin.util

import android.util.Log

/**
 * @name SkinLog
 * @package com.lvkang.skin.util
 * @author 345 QQ:1831712732
 * @time 2021/07/28 18:08
 * @description
 */
object SkinLog {

    private const val TAG = "SkinLog"

    const val LOG_V = "verbose"
    const val LOG_D = "debug"
    const val LOG_I = "info"
    const val LOG_W = "warn"
    const val LOG_E = "error"

    var defaultLongLevel = LOG_D

    fun log(content: String, logLevel: String = defaultLongLevel) {
        when (logLevel) {
            LOG_V -> Log.v(TAG, content)
            LOG_D -> Log.d(TAG, content)
            LOG_I -> Log.i(TAG, content)
            LOG_W -> Log.w(TAG, content)
            LOG_E -> Log.e(TAG, content)
            else -> {
                Log.v(TAG, content)
            }
        }
    }
}
package com.lvkang.example

import android.app.Application
import com.lvkang.skin.SkinManager
import com.lvkang.skin.inflater.SkinAppCompatViewInflater

/**
 * @name BaseActivity
 * @package com.lvkang.example
 * @author 345 QQ:1831712732
 * @time 2020/12/02 22:13
 * @description
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
            .addInflaters(SkinAppCompatViewInflater())

    }
}
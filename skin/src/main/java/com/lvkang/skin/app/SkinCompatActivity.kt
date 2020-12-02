package com.lvkang.skin.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.lvkang.skin.SkinManager
import com.lvkang.skin.factory.SkinCompateFactory
import com.lvkang.skin.obsreve.SkinObserver

/**
 * @name SkinCompatActivity
 * @package com.lvkang.skin.app
 * @author 345 QQ:1831712732
 * @time 2020/11/27 23:43
 * @description
 */
open class SkinCompatActivity : AppCompatActivity(), SkinObserver {


    private val skinFactory by lazy { SkinCompateFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, skinFactory)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        SkinManager.addSkinObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SkinManager.removeSkinObserver(this)
    }

    override fun applySkin() {
        skinFactory.applySkin()
    }

}
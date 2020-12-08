package com.lvkang.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lvkang.skin.SkinManager
import com.lvkang.skin.app.SkinCompatActivity
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.resource.AbstractSkinLoadStrategy
import com.lvkang.skin.resource.SkinLoadStrategy

class MainActivity : SkinCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.view)
        view.setOnClickListener {
            SkinManager.loadNewSkin(
                "skinHome.skin",
                SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS,
                object : SkinLoadListener {
                    override fun loadSkinSucess() {
                        Toast.makeText(this@MainActivity, "加载成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun loadSkinFailure(error: String) {
                        Toast.makeText(this@MainActivity, "加载失败", Toast.LENGTH_SHORT).show()
                    }

                    override fun loadRepeat() {
                        Toast.makeText(this@MainActivity, "重复加载", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        findViewById<View>(R.id.view3).setOnClickListener {
            SkinManager.restoreDefault()
        }
        Log.e(TAG, "onCreate: $view")
    }
}
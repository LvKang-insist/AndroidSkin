package com.lvkang.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lvkang.skin.SkinManager
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.resource.SkinLoadStrategy

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.button)
        view.setOnClickListener {
            if (!SkinManager.getIsDefault()) {
                Toast.makeText(this@MainActivity, "切換至默認", Toast.LENGTH_SHORT).show()
                SkinManager.restoreDefault()
                return@setOnClickListener
            }
            SkinManager.loadNewSkin(
                "skinHome.skin",
                SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS,
                object : SkinLoadListener {
                    override fun loadSkinSucess() {
                        Toast.makeText(this@MainActivity, "皮肤资源加载成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun loadSkinFailure(error: String) {
                        Toast.makeText(this@MainActivity, "加载失败", Toast.LENGTH_SHORT).show()
                    }

                    override fun loadRepeat() {
                        Toast.makeText(this@MainActivity, "重复加载", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        findViewById<View>(R.id.next).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}
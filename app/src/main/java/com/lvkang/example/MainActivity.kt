package com.lvkang.example

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lvkang.skin.SkinManager
import com.lvkang.skin.app.SkinCompatActivity
import com.lvkang.skin.listener.SkinLoadListener
import com.lvkang.skin.resource.SkinLoadStrategy
import java.util.concurrent.ThreadPoolExecutor

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
                "skin.apk",
                SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS
            )
        }
        findViewById<View>(R.id.button1).setOnClickListener {
            if (!SkinManager.getIsDefault()) {
                Toast.makeText(this@MainActivity, "切換至默認", Toast.LENGTH_SHORT).show()
                SkinManager.restoreDefault()
                return@setOnClickListener
            }
            SkinManager.loadNewSkin(
                "skin1.apk",
                SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS
            )
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            if (!SkinManager.getIsDefault()) {
                Toast.makeText(this@MainActivity, "切換至默認", Toast.LENGTH_SHORT).show()
                SkinManager.restoreDefault()
                return@setOnClickListener
            }
            SkinManager.loadNewSkin(
                "skin2.apk",
                SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS
            )
        }

        findViewById<View>(R.id.button3).setOnClickListener {
            if (!SkinManager.getIsDefault()) {
                Toast.makeText(this@MainActivity, "切換至默認", Toast.LENGTH_SHORT).show()
                SkinManager.restoreDefault()
                return@setOnClickListener
            }
            SkinManager.loadNewSkin(
                "skin3.apk",
                SkinLoadStrategy.SKIN_LOADER_STRATEGY_ASSETS
            )
        }

        findViewById<View>(R.id.next).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}




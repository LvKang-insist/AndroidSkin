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
            SkinManager.loadAssetsSkin("skin.apk")
        }
        findViewById<View>(R.id.button1).setOnClickListener {
            SkinManager.loadAssetsSkin("skin1.apk")

        }

        findViewById<View>(R.id.button2).setOnClickListener {
            SkinManager.loadAssetsSkin("skin2.apk")
        }

        findViewById<View>(R.id.button3).setOnClickListener {
            SkinManager.loadAssetsSkin("skin3.apk")
        }

        findViewById<View>(R.id.none).setOnClickListener {
            SkinManager.loadNone()
        }

        findViewById<View>(R.id.next).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}




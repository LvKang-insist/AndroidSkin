package com.lvkang.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.lvkang.skin.SkinManager
import com.lvkang.skin.app.SkinCompatActivity

class MainActivity : SkinCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.view)
        view.setOnClickListener {
            SkinManager.loadSkin(cacheDir?.path + "/skinHome.skin")
        }
        Log.e(TAG, "onCreate: $view")
    }
}
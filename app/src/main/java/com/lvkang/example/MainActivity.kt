package com.lvkang.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lvkang.skin.SkinManager
import com.lvkang.skin.ktx.isFile
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.button)
        view.setOnClickListener {
            SkinManager.loadAssetsSkin("skin.skin")
        }
        findViewById<View>(R.id.button1).setOnClickListener {
            SkinManager.loadAssetsSkin("skin1.skin")

        }

        findViewById<View>(R.id.button2).setOnClickListener {
            SkinManager.loadAssetsSkin("skin2.skin")
        }

        findViewById<View>(R.id.button3).setOnClickListener {
            SkinManager.loadAssetsSkin("skin3.skin")
        }

        findViewById<View>(R.id.button4).setOnClickListener {

            //先将zip复制到沙箱中
            val path = copyCache(
                "skin4.zip",
                "${SkinManager.getApplication().getExternalFilesDir("file")}${File.separator}"
            )
            path?.run {
                SkinManager.loadZipSkin(path, password = "3310")
            }
        }

        findViewById<View>(R.id.button5).setOnClickListener {
            SkinManager.loadAssetsSkin("skin5.skin")
        }

        findViewById<View>(R.id.none).setOnClickListener {
            SkinManager.loadNone()
        }

        findViewById<View>(R.id.next).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
        findViewById<View>(R.id.cardview).setOnClickListener {
            SkinManager.loadAssetsSkin("skin7.skin")
        }
    }

    private fun copyCache(skinName: String, cacheDir: String): String? {
        return try {
            val outFile = File(cacheDir, skinName)
            if (isFile(outFile.path)) {
                outFile.delete()
            }
            val input = SkinManager.getContext().resources.assets.open(skinName)
            input.copyTo(outFile.outputStream())
            outFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}




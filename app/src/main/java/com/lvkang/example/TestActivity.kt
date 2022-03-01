package com.lvkang.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lvkang.skin.app.SkinCompatActivity

/**
 * @name TextActivity
 * @package com.lvkang.example
 * @author 345 QQ:1831712732
 * @time 2020/12/14 00:12
 * @description
 */
class TestActivity : SkinCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}
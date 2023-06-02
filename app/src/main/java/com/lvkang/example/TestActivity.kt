package com.lvkang.example

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val recycler = findViewById<RecyclerView>(R.id.recycler)

        recycler.layoutManager = LinearLayoutManager(this)

        val list = arrayListOf<String>()
        for (i in 0..1000)
            list.add("")
        recycler.adapter = TestAdapter(list)


        findViewById<View>(R.id.test).setOnClickListener {
            recycler?.adapter?.notifyDataSetChanged()
            print()
        }
    }
}
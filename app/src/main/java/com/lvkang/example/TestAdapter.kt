package com.lvkang.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @name TestAdapter
 * @package com.lvkang.example
 * @author 345 QQ:1831712732
 * @time 2022/07/21 17:33
 * @description
 */
class TestAdapter(val data: List<String>) : RecyclerView.Adapter<TestAdapter.TestHolder>() {


    class TestHolder(view: View) : RecyclerView.ViewHolder(view) {
        var text = view.findViewById<AppCompatTextView>(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return TestHolder(view)
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        holder.text.text = "$position"
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
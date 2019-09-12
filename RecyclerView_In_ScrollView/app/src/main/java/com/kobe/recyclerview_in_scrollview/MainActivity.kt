package com.kobe.recyclerview_in_scrollview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.overScrollMode = View.OVER_SCROLL_NEVER

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.scrollToPosition(0)
        recyclerView.layoutManager = linearLayoutManager

        val adapter = MyRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
    }
}

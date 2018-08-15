package com.kobe.recyclerview_in_scrollview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_layout.view.*

class MyRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<MyRecyclerViewAdapter.BaseViewHolder>() {

    private val items = ArrayList<Int>()

    init {
        for (i:Int in 0..100) {
            items.add(i)
        }
    }

    class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView? = itemView?.titleTextView
        val contentView: TextView? = itemView?.contentTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val titleStr: String = "Title " + items[position]
        val contentStr: String = "Content " + items[position]

        holder.titleView?.text = titleStr
        holder.contentView?.text = contentStr
    }
}
package com.ko.mvp.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ko.mvp.R

class MvpRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var models: List<MvpModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MvpRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_user, parent, false)
        )
    }

    override fun getItemCount(): Int =
        models.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MvpRecyclerViewHolder) {
            holder.bind(this.models[position])
        }
    }

    fun update(models: List<MvpModel>) {
        this.models = models
        notifyDataSetChanged()
    }
}

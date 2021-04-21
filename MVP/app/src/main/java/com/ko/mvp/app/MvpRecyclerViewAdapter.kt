package com.ko.mvp.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ko.mvp.base.MvpUserModel
import com.ko.mvp.databinding.LayoutUserBinding

internal class MvpRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var models: List<MvpUserModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MvpRecyclerViewHolder(
            LayoutUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MvpRecyclerViewHolder) {
            holder.bind(models[position])
        }
    }

    fun update(models: List<MvpUserModel>) {
        this.models = models
        notifyDataSetChanged()
    }
}

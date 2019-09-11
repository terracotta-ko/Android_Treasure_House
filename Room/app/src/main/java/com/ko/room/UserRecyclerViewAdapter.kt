package com.ko.room

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class UserRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var users: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserRecyclerViewHolder) {
            holder.bind(users[position])
        }
    }

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}

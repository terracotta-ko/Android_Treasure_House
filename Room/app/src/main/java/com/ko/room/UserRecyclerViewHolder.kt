package com.ko.room

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_user.*

internal class UserRecyclerViewHolder(override val containerView: View?):
    LayoutContainer, RecyclerView.ViewHolder(containerView) {

    fun bind(user: User) {
        userId.text = user.id.toString()
        userName.text = user.name
        userIsVerified.text = user.isVerified.toString()
    }
}

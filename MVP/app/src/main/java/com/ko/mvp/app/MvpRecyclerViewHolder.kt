package com.ko.mvp.app

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_user.*

class MvpRecyclerViewHolder(
    override val containerView: View?
) : LayoutContainer, RecyclerView.ViewHolder(containerView) {

    fun bind(model: MvpModel) {
        userId.text = model.userId
        userName.text = model.userName
        userIsVerified.text = model.isVerified
    }
}

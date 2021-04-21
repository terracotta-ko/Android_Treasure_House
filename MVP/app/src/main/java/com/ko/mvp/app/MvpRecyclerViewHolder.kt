package com.ko.mvp.app

import androidx.recyclerview.widget.RecyclerView
import com.ko.mvp.base.MvpUserModel
import com.ko.mvp.databinding.LayoutUserBinding

internal class MvpRecyclerViewHolder(
    private val viewBinding: LayoutUserBinding,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(model: MvpUserModel) {
        viewBinding.run {
            userId.text = model.userId
            userName.text = model.userName
            userIsVerified.text = model.isVerified
        }
    }
}

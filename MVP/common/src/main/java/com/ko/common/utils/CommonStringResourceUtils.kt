package com.ko.common.utils

import android.content.Context
import androidx.annotation.StringRes

class CommonStringResourceUtils(private val context: Context) {

    fun getString(@StringRes stringResId: Int): String =
        context.getString(stringResId)

    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any?): String =
        context.getString(stringResId, *formatArgs)
}
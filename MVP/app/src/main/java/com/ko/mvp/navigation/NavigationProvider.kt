package com.ko.mvp.navigation

import android.content.Context
import com.ko.common.navigation.AddActivityNavigator

interface NavigationProvider {

    fun getAddActivityNavigator(context: Context): AddActivityNavigator
}

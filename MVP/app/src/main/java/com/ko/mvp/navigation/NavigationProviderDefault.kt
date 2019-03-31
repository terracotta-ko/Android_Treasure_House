package com.ko.mvp.navigation

import android.content.Context
import com.ko.adduser.app.AddActivity
import com.ko.common.navigation.AddActivityNavigator

class NavigationProviderDefault : NavigationProvider {

    override fun getAddActivityNavigator(context: Context) =
        object : AddActivityNavigator {
            override fun navigateToAddActivity(context: Context) {
                AddActivity.start(context)
            }
        }
}

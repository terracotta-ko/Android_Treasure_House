package com.ko.mvp.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import com.ko.adduser.app.AddActivity
import com.ko.common.navigation.AddActivityNavigator

class NavigationProviderDefault : NavigationProvider {

    override fun getAddActivityNavigator(context: Context) =
        object : AddActivityNavigator {
            override fun startForResult(fragment: Fragment, requestCode: Int) {
                AddActivity.startForResult(fragment, requestCode)
            }
        }
}

package com.ko.common.navigation

import androidx.fragment.app.Fragment

interface AddActivityNavigator {

    fun startForResult(fragment: Fragment, requestCode: Int)
}

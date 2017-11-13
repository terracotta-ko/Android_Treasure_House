package com.intowow.kotlin_fragmentexample

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity(), ContentFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //>> now, you only need to use addFragment() to add Fragment dynamically
        addFragment(R.id.fragment_place, ContentFragment.newInstance("a","b"))
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //>> extension function
    inline fun FragmentManager.inTransation(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    //>> extension function
    fun AppCompatActivity.addFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.inTransation { add(id, fragment) }
    }
}

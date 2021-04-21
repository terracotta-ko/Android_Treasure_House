package com.ko.mvp.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ko.adduser.databinding.ActivityAddBinding
import com.ko.mvp.R
import com.ko.mvp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, UserListFragment.newInstance())
                .commit()
        }
    }
}

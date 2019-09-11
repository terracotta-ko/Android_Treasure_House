package com.ko.adduser.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import com.ko.adduser.R
import com.ko.adduser.core.AddContract
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity(), AddContract.View {

    companion object {

        fun start(context: Context) {
            ActivityCompat.startActivity(context, makeIntent(context), null)
        }

        private fun makeIntent(context: Context): Intent =
                Intent(context, AddActivity::class.java)
    }

    private lateinit var presenter: AddContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val serviceLocator = AddServiceLocator(this)
        presenter = serviceLocator.getPresenter()
        presenter.bindView(this)

        confirmButton.setOnClickListener {
            val userId = userIdInputText.editableText?.toString()
            val userName = userNameInputText.editableText?.toString()

            if (userId != null && userName != null) {
                presenter.saveUser(userId, userName)
            }
        }
    }

    override fun onDestroy() {
        presenter.unbindView()
        super.onDestroy()
    }

    override fun close() {
        finish()
    }
}

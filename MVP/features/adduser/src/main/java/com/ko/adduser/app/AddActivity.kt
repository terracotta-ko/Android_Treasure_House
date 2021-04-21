package com.ko.adduser.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ko.adduser.R
import com.ko.adduser.base.AddContract
import com.ko.adduser.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity(), AddContract.View {

    companion object {

        fun startForResult(fragment: Fragment, requestCode: Int) {
            fragment.startActivityForResult(makeIntent(fragment), requestCode, null)
        }

        private fun makeIntent(fragment: Fragment): Intent =
            Intent(fragment.requireContext(), AddActivity::class.java)
    }

    private lateinit var viewBinding: ActivityAddBinding
    private lateinit var presenter: AddContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        setupClickListener()

        val serviceLocator = AddServiceLocator(this)
        presenter = serviceLocator.getPresenter()
        presenter.bindView(this)
    }

    override fun onDestroy() {
        presenter.unbindView()
        super.onDestroy()
    }

    override fun showError(error: String) {
        Log.d("TAG", "showError: $error")
    }

    override fun closeWithResult() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    //>> private functions

    private fun setupClickListener() {

        viewBinding.run {
            addButton.setOnClickListener {
                presenter.saveUser(
                    userIdInputText.editableText?.toString()?.trim(),
                    userNameInputText.editableText?.toString()?.trim()
                )
            }
        }
    }
}

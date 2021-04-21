package com.ko.mvp.app


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ko.common.navigation.AddActivityNavigator
import com.ko.common.navigation.RequestCode
import com.ko.mvp.R
import com.ko.mvp.base.MvpContract
import com.ko.mvp.base.MvpModel
import com.ko.mvp.base.MvpUserModel
import com.ko.mvp.databinding.FragmentUserListBinding

internal class UserListFragment : Fragment(), MvpContract.View {

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }

    private var viewBinding: FragmentUserListBinding? = null
    private lateinit var presenter: MvpContract.Presenter
    private lateinit var adapter: MvpRecyclerViewAdapter
    private lateinit var addActivityNavigator: AddActivityNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val serviceLocator = MvpServiceLocator(context)
        presenter = serviceLocator.getPresenter()
        adapter = serviceLocator.getAdapter()
        addActivityNavigator = serviceLocator.getAddActivityNavigator()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUserListBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListener()

        presenter.bindView(this)
        presenter.onViewCreated()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCode.ADD -> {
                    presenter.onAddSucceeded()
                }
            }
        }
    }

    override fun onDestroyView() {
        presenter.unbindView()
        super.onDestroyView()
    }

    override fun updateView(models: List<MvpUserModel>) {
        adapter.update(models)
    }

    override fun gotoAddActivity() {
        addActivityNavigator.startForResult(this, RequestCode.ADD)
    }

    //>> private functions

    private fun setupRecyclerView() {
        viewBinding?.run {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

            userListRecyclerView.layoutManager = linearLayoutManager
            userListRecyclerView.adapter = adapter
        }
    }

    private fun setupClickListener() {
        viewBinding?.run {
            addButton.setOnClickListener {
                presenter.onAddButtonClick()
            }
        }
    }
}

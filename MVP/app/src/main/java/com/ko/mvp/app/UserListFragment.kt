package com.ko.mvp.app


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ko.common.navigation.AddActivityNavigator

import com.ko.mvp.R
import com.ko.mvp.core.MvpContract
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment(), MvpContract.View {

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }

    private lateinit var presenter: MvpContract.Presenter
    private lateinit var adapter: MvpRecyclerViewAdapter
    private lateinit var addActivityNavigator: AddActivityNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        val serviceLocator = MvpServiceLocator(context!!)
        presenter = serviceLocator.getPresenter()
        adapter = serviceLocator.getAdapter()
        addActivityNavigator = serviceLocator.getAddActivityNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bindView(this)

        userListRecyclerView.adapter = adapter
        userListRecyclerView.layoutManager = LinearLayoutManager(context)

        addButton.setOnClickListener {
            presenter.onAddButtonClick()
        }
    }

    override fun onResume() {
        presenter.fetch()
        super.onResume()
    }

    override fun onDestroyView() {
        presenter.unbindView()
        super.onDestroyView()
    }

    override fun update(models: List<MvpModel>) {
        adapter.update(models)
    }

    override fun gotoAddActivity() {
        activity?.let { addActivityNavigator.navigateToAddActivity(it) }
    }
}

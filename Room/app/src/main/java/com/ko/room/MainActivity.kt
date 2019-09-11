package com.ko.room

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ko.room.data.UserDao
import com.ko.room.data.UserDatabaseProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var userDao: UserDao
    private lateinit var dispatcher: CoroutineDispatcher

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mapper = UserMapper()
    private val adapter = UserRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()
        userDao = UserDatabaseProvider.getInstance(this).userDao()
        dispatcher = CoroutineDispatcherDefault()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        launch(dispatcher.IODispatcher) {
            val users = mapper.toUser(userDao.getAllUsers())
            launch(dispatcher.UIDispatcher) {
                adapter.setUsers(users)
            }
        }
    }
}

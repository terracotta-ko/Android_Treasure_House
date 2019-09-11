package com.ko.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ko.room.data.UserDao
import com.ko.room.data.UserDatabaseProvider
import com.ko.room.data.UserEntity
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class AddActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var userDao: UserDao
    private lateinit var dispatcher: CoroutineDispatcher

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        job = Job()
        userDao = UserDatabaseProvider.getInstance(this).userDao()
        dispatcher = CoroutineDispatcherDefault()

        confirmButton.setOnClickListener {
            launch(dispatcher.IODispatcher) {
                userDao.addUser(
                    UserEntity(
                        userIdInput.editText?.text.toString().toLong(),
                        userNameInput.editText?.text.toString(),
                        Random.nextBoolean()
                    )
                )
                launch(dispatcher.UIDispatcher) {
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}

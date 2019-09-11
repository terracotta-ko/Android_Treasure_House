package ko.com.channel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val dispatcher = CoroutineDispatcherDefault()
    private val channel = Channel<Int>()

    private var cnt: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        button1.setOnClickListener {
            launch(dispatcher.IODispatcher) {
                channel.send(cnt++)
            }
        }

        button2.setOnClickListener {
            launch(dispatcher.IODispatcher) {
                channel.send(cnt++)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        launch(dispatcher.IODispatcher) {

            while (true) {
                Log.d("KKD", "receive: ${channel.receive()}")
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}

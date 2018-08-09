package com.example.bewenko.retrofit_example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        request()
    }

    private fun request() {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val request: UserApi = retrofit.create(UserApi::class.java)

        val call: Call<List<User>> = request.getUsers()
        call.enqueue(object: Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                Log.d("KKD", "onFailure: " + t.toString())
            }

            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                val users = response?.body()
                users?.forEach {
                    Log.d("KKD", "name: " + it.login)
                    Log.d("KKD", "id: " + it.id)
                    Log.d("KKD", "url: " + it.url)
                }
            }
        })
    }
}

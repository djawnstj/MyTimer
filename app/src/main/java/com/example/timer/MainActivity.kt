package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timer.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object { private const val TAG = "MainActivity" }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            Api.api.getTest("user").enqueue(object: Callback<Test> {
                override fun onResponse(call: Call<Test>, response: Response<Test>) {

                }

                override fun onFailure(call: Call<Test>, t: Throwable) {

                }

            })
        }


    }

    fun println(msg: String?) {
        binding.output.append("$msg\n")
    }

}
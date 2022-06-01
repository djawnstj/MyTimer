package com.example.timer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            Api.api.getTest("user").enqueue(object: Callback<Test> {
                override fun onResponse(call: Call<Test>, response: Response<Test>) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    AppData.resetLogoutTimer()
                }

                override fun onFailure(call: Call<Test>, t: Throwable) {

                }

            })
        }

    }

}
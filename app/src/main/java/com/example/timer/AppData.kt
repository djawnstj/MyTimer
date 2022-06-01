package com.example.timer

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.widget.Toast

class AppData {

    companion object {

        fun showToast(context: Context, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        private const val future: Long = 1000*5
        private const val interval: Long = 1000*60
        private var timer: ThreadTimer? = null

        fun resetLogoutTimer() {
            timer?.cancel()
            timer = null
            timer = ThreadTimer(future, interval)
            timer?.start()
        }

    }

}

class ThreadTimer(millisInFuture: Long, countDownInterval: Long): CountDownTimer(millisInFuture, countDownInterval) {
    override fun onTick(p0: Long) {
    }

    override fun onFinish() {
        val intent = Intent(MyApp.getContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        MyApp.getContext().startActivity(intent)
    }

}
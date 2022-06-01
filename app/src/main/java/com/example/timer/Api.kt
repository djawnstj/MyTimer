package com.example.timer

import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface DarwinTabApi {

    @FormUrlEncoded
    @POST("JPY,USD,KRW.json")
    fun getTest(
        @Field("user") user: String,
    ): Call<Test>
}


class Api {

    companion object {

        private var instance : DarwinTabApi? = null

        val api: DarwinTabApi get() =getInstance()

        @Synchronized
        fun getInstance(): DarwinTabApi {
            if (instance == null)
                instance = create()
            return instance as DarwinTabApi
        }

        fun create(): DarwinTabApi {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Client-Id", "id")
                    .addHeader("X-Client-Secret", "secret")
                    .addHeader("X-Client-UserId", "user")
                    .build()
                return@Interceptor it.proceed(request)
            }

            val listener: EventListener = object: EventListener() {
                override fun callStart(call: okhttp3.Call) {
                    super.callStart(call)
                    AppData.resetLogoutTimer()
                }
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(3600, TimeUnit.SECONDS)  // 타임아웃 시간 설정 60초
                .eventListener(listener)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.manana.kr/exchange/rate/KRW/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DarwinTabApi::class.java)
        }

    }
}

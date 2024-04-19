package com.cacttuseducation.mycircle.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cacttuseducation.mycircle.helpers.Helpers.provideRetrofitInstance
import com.cacttuseducation.mycircle.models.Posts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalViewModel : ViewModel() {

    val posts = MutableLiveData<Posts>()

    fun getPosts() {
        val call = provideRetrofitInstance().getPosts()
        call.enqueue(object : Callback<Posts?> {
            override fun onResponse(call: Call<Posts?>, response: Response<Posts?>) {
                Log.d("TAG","onResponse ${response.body()}")
                posts.value = response.body()
            }

            override fun onFailure(call: Call<Posts?>, t: Throwable) {
                Log.d("TAG","onResponse ${t.message}")
            }
        })
    }

}
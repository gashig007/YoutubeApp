package com.geektech.youtubeapp.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geektech.youtubeapp.BuildConfig.BASE_API
import com.geektech.youtubeapp.`object`.Constant
import com.geektech.youtubeapp.base.BaseViewModel
import com.geektech.youtubeapp.model.Playlists
import com.geektech.youtubeapp.remote.ApiService
import com.geektech.youtubeapp.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistViewModel : BaseViewModel() {
    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun getPlaylist(): LiveData<Playlists> {
        return playlist()
    }

    private fun playlist(): LiveData<Playlists> {
        val data = MutableLiveData<Playlists>()
        apiService.getPlaylists(Constant.part, Constant.channelId, BASE_API)
            .enqueue(object: Callback<Playlists> {
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {

                }

            })
        return data

    }
}
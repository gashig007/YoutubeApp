package com.geektech.youtubeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geektech.youtubeapp.BuildConfig
import com.geektech.youtubeapp.core.network.RetrofitClient
import com.geektech.youtubeapp.core.network.result.Resource
import com.geektech.youtubeapp.data.remote.ApiService
import com.geektech.youtubeapp.data.remote.model.PlaylistItems
import com.geektech.youtubeapp.data.remote.model.Playlists
import com.geektech.youtubeapp.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

     fun getPlaylists(): LiveData<Resource<Playlists>> {
        val data = MutableLiveData<Resource<Playlists>>()

         data.value = Resource.loading()

        apiService.getPlaylists(Constant.part, Constant.channelId, BuildConfig.BASE_API, Constant.maxResult)
            .enqueue(object: Callback<Playlists> {
                override fun onResponse(call: Call<Playlists>, response: Response<Playlists>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            data.value = Resource.success(response.body()!!)
                        }
                    } else {
                        data.value = Resource.error(response.message(),null)
                    }
                }

                override fun onFailure(call: Call<Playlists>, t: Throwable) {
                    data.value = Resource.error(t.message,null)
                }

            })
        return data

    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItems>> {
        val data = MutableLiveData<Resource<PlaylistItems>>()

        data.value = Resource.loading()
        apiService.getPlaylistItems(Constant.part, playlistId, BuildConfig.BASE_API, Constant.maxResult)
            .enqueue(object: Callback<PlaylistItems> {
                override fun onResponse(
                    call: Call<PlaylistItems>,
                    response: Response<PlaylistItems>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            data.value = Resource.success(response.body()!!)
                        }
                    } else {
                        data.value = Resource.error(response.message(),null)
                    }
                }

                override fun onFailure(call: Call<PlaylistItems>, t: Throwable) {
                    data.value = Resource.error(t.message,null)
                }

            })
        return data
    }
}
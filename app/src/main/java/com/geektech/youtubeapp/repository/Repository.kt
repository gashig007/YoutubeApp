package com.geektech.youtubeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.geektech.youtubeapp.BuildConfig
import com.geektech.youtubeapp.core.network.RetrofitClient
import com.geektech.youtubeapp.core.network.result.Resource
import com.geektech.youtubeapp.core.network.result.Resource.Companion.loading
import com.geektech.youtubeapp.core.network.result.Resource.Companion.success
import com.geektech.youtubeapp.data.remote.ApiService
import com.geektech.youtubeapp.data.remote.model.PlaylistItems
import com.geektech.youtubeapp.data.remote.model.Playlists
import com.geektech.youtubeapp.utils.Constant
import kotlinx.coroutines.Dispatchers

class Repository {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun getPlaylists(): LiveData<Resource<Playlists>> {
        return liveData(Dispatchers.IO) {
            emit(loading())
            val response = apiService.getPlaylists(
                Constant.part,
                Constant.channelId,
                BuildConfig.BASE_API,
                Constant.maxResult
            )

            emit(if (response.isSuccessful) success(response.body()!!) else error(response.message()))
        }
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItems>> {
        return liveData(Dispatchers.IO) {
            emit(loading())
            val response = apiService.getPlaylistItems(
                Constant.part,
                playlistId,
                BuildConfig.BASE_API,
                Constant.maxResult
            )

            emit(if (response.isSuccessful) success(response.body()!!) else error(response.message()))
        }
    }
}


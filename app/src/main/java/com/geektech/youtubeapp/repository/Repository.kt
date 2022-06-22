package com.geektech.youtubeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.geektech.youtubeapp.core.network.result.Resource
import com.geektech.youtubeapp.core.network.result.Resource.Companion.loading
import com.geektech.youtubeapp.data.remote.RemoteDataSource
import com.geektech.youtubeapp.data.remote.model.PlaylistItems
import com.geektech.youtubeapp.data.remote.model.Playlists
import kotlinx.coroutines.Dispatchers

class Repository(private val dataSource: RemoteDataSource) {

    fun getPlaylists(): LiveData<Resource<Playlists>> {
        return liveData(Dispatchers.IO) {
            emit(loading())
            val response = dataSource.getPlaylists()
            emit(response)
        }
    }

    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItems>> {
        return liveData(Dispatchers.IO) {
            emit(loading())
            val response = dataSource.getPlaylistItems(playlistId)
            emit(response)
        }
    }
}


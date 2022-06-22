package com.geektech.youtubeapp.data.remote

import com.geektech.youtubeapp.BuildConfig
import com.geektech.youtubeapp.core.network.BaseDataSource
import com.geektech.youtubeapp.utils.Constant
import org.koin.dsl.module

val remoteDataSource = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {
    suspend fun getPlaylists() = getResult {
        apiService.getPlaylists(
            Constant.part,
            Constant.channelId,
            BuildConfig.BASE_API,
            Constant.maxResult
        )
    }

    suspend fun getPlaylistItems(playlistId: String) = getResult {
        apiService.getPlaylistItems(
            Constant.part,
            playlistId,
            BuildConfig.BASE_API,
            Constant.maxResult
        )
    }
}
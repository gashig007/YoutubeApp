package com.geektech.youtubeapp.data.remote

import com.geektech.youtubeapp.data.remote.model.PlaylistItems
import com.geektech.youtubeapp.data.remote.model.Playlists
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResult: Int
    ): Response<Playlists>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResult: Int
    ): Response<PlaylistItems>
}
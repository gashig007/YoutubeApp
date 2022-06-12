package com.geektech.youtubeapp.ui.playlist_detail

import androidx.lifecycle.LiveData
import com.geektech.youtubeapp.App
import com.geektech.youtubeapp.core.network.result.Resource
import com.geektech.youtubeapp.core.ui.BaseViewModel
import com.geektech.youtubeapp.data.remote.model.PlaylistItems

class PlaylistDetailViewModel: BaseViewModel() {
    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItems>> {
        return App().repository.getPlaylistItems(playlistId)
    }
}
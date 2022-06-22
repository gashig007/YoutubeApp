package com.geektech.youtubeapp.ui.playlist_detail

import androidx.lifecycle.LiveData
import com.geektech.youtubeapp.core.network.result.Resource
import com.geektech.youtubeapp.core.ui.BaseViewModel
import com.geektech.youtubeapp.data.remote.model.PlaylistItems
import com.geektech.youtubeapp.repository.Repository

class PlaylistDetailViewModel(private val repository: Repository): BaseViewModel() {
    fun getPlaylistItems(playlistId: String): LiveData<Resource<PlaylistItems>> {
        return repository.getPlaylistItems(playlistId)
    }
}
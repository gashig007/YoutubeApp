package com.geektech.youtubeapp.ui.playlist

import androidx.lifecycle.LiveData
import com.geektech.youtubeapp.App
import com.geektech.youtubeapp.core.ui.BaseViewModel
import com.geektech.youtubeapp.data.remote.model.Playlists
import com.geektech.youtubeapp.core.network.result.Resource


class PlaylistViewModel: BaseViewModel() {
    fun getPlaylists(): LiveData<Resource<Playlists>> {
        return App().repository.getPlaylists()
    }
}
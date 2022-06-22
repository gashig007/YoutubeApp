package com.geektech.youtubeapp.ui.playlist

import androidx.lifecycle.LiveData
import com.geektech.youtubeapp.core.ui.BaseViewModel
import com.geektech.youtubeapp.data.remote.model.Playlists
import com.geektech.youtubeapp.core.network.result.Resource
import com.geektech.youtubeapp.data.local.AppPrefs
import com.geektech.youtubeapp.repository.Repository


class PlaylistViewModel(private val repository: Repository, private val prefs: AppPrefs): BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<Playlists>> {
        return repository.getPlaylists()
    }

    fun setOnBoard(onBoard: Boolean) {
        prefs.onBoard = onBoard
    }
}
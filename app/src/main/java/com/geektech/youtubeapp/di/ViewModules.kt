package com.geektech.youtubeapp.di

import com.geektech.youtubeapp.ui.playlist.PlaylistViewModel
import com.geektech.youtubeapp.ui.playlist_detail.PlaylistDetailViewModel
import com.geektech.youtubeapp.ui.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { PlaylistViewModel(get(), get())}
    viewModel { PlaylistDetailViewModel(get())}
    viewModel { VideoViewModel()}
}
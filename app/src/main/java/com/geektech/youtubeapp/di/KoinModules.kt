package com.geektech.youtubeapp.di

import com.geektech.youtubeapp.core.network.networkModule
import com.geektech.youtubeapp.data.local.prefModule
import com.geektech.youtubeapp.data.remote.remoteDataSource

val koinModules = listOf(
    repoModules,
    viewModules,
    networkModule,
    remoteDataSource,
    prefModule
)
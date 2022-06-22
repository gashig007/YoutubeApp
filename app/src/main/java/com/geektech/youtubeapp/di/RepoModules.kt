package com.geektech.youtubeapp.di

import com.geektech.youtubeapp.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModules: Module = module {
    single { Repository(get()) }
}
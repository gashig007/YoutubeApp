package com.geektech.youtubeapp

import android.app.Application
import com.geektech.youtubeapp.repository.Repository

class App : Application() {
    val repository: Repository by lazy {
        Repository()
    }
}
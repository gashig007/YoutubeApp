package com.geektech.youtubeapp.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.geektech.youtubeapp.R
import com.geektech.youtubeapp.base.BaseActivity
import com.geektech.youtubeapp.databinding.ActivityPlaylistDetailBinding

class PlaylistDetailActivity : BaseActivity<ActivityPlaylistDetailBinding, PlaylistDetailViewModel>() {

    override val viewModel: PlaylistDetailViewModel by lazy {
        ViewModelProvider(this)[PlaylistDetailViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistDetailBinding {
        return ActivityPlaylistDetailBinding.inflate(inflater)
    }

    override fun initView() {
        val channelId = intent.getStringExtra(PlaylistActivity.KEY)
        Toast.makeText(this, channelId, Toast.LENGTH_SHORT).show()
    }

    override fun initListener() {

    }

    override fun initViewModel() {

    }

    override fun checkInternet() {

    }

}
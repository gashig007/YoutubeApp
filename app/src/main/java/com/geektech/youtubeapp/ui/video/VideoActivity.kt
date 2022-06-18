package com.geektech.youtubeapp.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.geektech.youtubeapp.R
import com.geektech.youtubeapp.core.ui.BaseActivity
import com.geektech.youtubeapp.databinding.ActivityVideoBinding
import com.geektech.youtubeapp.ui.playlist_detail.PlaylistDetailActivity
import com.geektech.youtubeapp.utils.NetworkStatus
import com.geektech.youtubeapp.utils.NetworkStatusHelper
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

class VideoActivity : BaseActivity<ActivityVideoBinding, VideoViewModel>(), Player.Listener {

    private var videoId: String? = null
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override val viewModel: VideoViewModel by lazy {
        ViewModelProvider(this)[VideoViewModel::class.java]
    }

    override fun initListener() {
        super.initListener()
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initView() {

        videoId = intent.getStringExtra(PlaylistDetailActivity.idPdaVa).toString()
        binding.videoTitle.text = intent.getStringExtra(PlaylistDetailActivity.titlePdaVa).toString()
        binding.videoDesc.text = intent.getStringExtra(PlaylistDetailActivity.descPdaVa).toString()

        binding.networkLayout.root.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        setupPlayer()
        addMp4Files()
        addMp3Files()
    }

    override fun initViewModel() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("Seek Time", player.currentPosition)
        outState.putInt("mediaItem", player.currentMediaItemIndex)
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView = binding.videoView
        playerView.player = player
        player.addListener(this)
    }

    private fun addMp4Files() {
        val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4))
        player.addMediaItem(mediaItem)
        player.prepare()
    }

    private fun addMp3Files() {
        val mediaItem = MediaItem.fromUri(getString(R.string.test_mp3))
        player.addMediaItem(mediaItem)
        player.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when(playbackState){
            Player.STATE_BUFFERING -> {
                binding.videoProgressBar.visibility = View.VISIBLE
            }
            Player.STATE_READY -> {
                binding.videoProgressBar.visibility = View.INVISIBLE

            }
        }
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityVideoBinding {
        return ActivityVideoBinding.inflate(inflater)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    private fun checkConnection() {
        NetworkStatusHelper(this).observe(this) {
            if (it == NetworkStatus.Available) {
                binding.main.visibility = View.VISIBLE
                binding.networkLayout.root.visibility = View.GONE
            } else {
                binding.main.visibility = View.GONE
                binding.networkLayout.root.visibility = View.VISIBLE
            }
        }
    }

    override fun checkInternet() {
        checkConnection()

        binding.networkLayout.btnTryAgain.setOnClickListener {
            checkConnection()
        }
    }
}
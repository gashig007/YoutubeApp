package com.geektech.youtubeapp.ui.video

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.geektech.youtubeapp.BuildConfig
import com.geektech.youtubeapp.core.ui.BaseActivity
import com.geektech.youtubeapp.data.local.AppPrefs
import com.geektech.youtubeapp.databinding.ActivityVideoBinding
import com.geektech.youtubeapp.ui.playlist_detail.PlaylistDetailActivity
import com.geektech.youtubeapp.utils.NetworkStatus
import com.geektech.youtubeapp.utils.NetworkStatusHelper
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class VideoActivity : BaseActivity<ActivityVideoBinding, VideoViewModel>(), Player.Listener {
    private var videoId: String? = null
    private lateinit var player: ExoPlayer
    private lateinit var videoSource: ProgressiveMediaSource
    private lateinit var audioSource: ProgressiveMediaSource
    override val viewModel: VideoViewModel by viewModel()
    private val prefs: AppPrefs by inject()

    override fun initListener() {
        super.initListener()
        Log.d("ololo", prefs.onBoard.toString())
        downloadVideo()
        binding.tvBack.setOnClickListener {
            Intent(this, PlaylistDetailActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    private fun downloadVideo() {
        object : YouTubeExtractor(this) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                if (ytFiles != null) {
                    val videoTag = 134
                    val audioTag = 140
                    val videoUrl = ytFiles[videoTag].url
                    val audioUrl = ytFiles[audioTag].url
                    setupPlayer(videoUrl, audioUrl)
                }
            }
        }.extract(BuildConfig.YOUTUBE_BASE + videoId)
    }

    override fun initView() {
        videoId = intent.getStringExtra(PlaylistDetailActivity.idPdaVa).toString()
        binding.videoTitle.text = intent.getStringExtra(PlaylistDetailActivity.titlePdaVa).toString()
        binding.videoDesc.text = intent.getStringExtra(PlaylistDetailActivity.descPdaVa).toString()
        binding.networkLayout.root.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
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

    private fun setupPlayer(videoUrl: String, audioUrl: String) {
        buildMediaSource(videoUrl, audioUrl)
        player = ExoPlayer.Builder(this).build()
        binding.videoView.player = player
        player.setMediaSource(MergingMediaSource(videoSource, audioSource))
        player.addListener(this)
        player.prepare()
    }

    private fun buildMediaSource(videoUrl: String, audioUrl: String) {
        videoSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(videoUrl))

        audioSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(audioUrl))
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
            Player.STATE_ENDED -> { }
            Player.STATE_IDLE -> { }
        }
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
package com.geektech.youtubeapp.ui.playlist_detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.geektech.youtubeapp.core.network.result.Status
import com.geektech.youtubeapp.core.ui.BaseActivity
import com.geektech.youtubeapp.data.remote.model.Item
import com.geektech.youtubeapp.databinding.ActivityPlaylistDetailBinding
import com.geektech.youtubeapp.ui.playlist.PlaylistActivity
import com.geektech.youtubeapp.utils.NetworkStatus
import com.geektech.youtubeapp.utils.NetworkStatusHelper

class PlaylistDetailActivity : BaseActivity<ActivityPlaylistDetailBinding, PlaylistDetailViewModel>() {

    private var playlistId: String? = null

    override val viewModel: PlaylistDetailViewModel by lazy {
        ViewModelProvider(this)[PlaylistDetailViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistDetailBinding {
        return ActivityPlaylistDetailBinding.inflate(inflater)
    }

    override fun initView() {
        playlistId = intent.getStringExtra(PlaylistActivity.idPaPda).toString()
        Toast.makeText(this, playlistId, Toast.LENGTH_SHORT).show()
    }

    override fun initListener() {
        super.initListener()
        binding.tvBack.setOnClickListener{
            onItemClick()
        }
    }


    override fun initViewModel() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }

        initVM()
    }

    private fun initRecyclerView(playlistsList: List<Item>) {
        binding.videosRecyclerView.adapter = PlaylistDetailAdapter(playlistsList)
    }

    private fun initVM() {
        playlistId?.let {
            viewModel.getPlaylistItems(it).observe(this) {
                when(it.status) {
                    Status.SUCCESS -> {
                        if (it.data != null) {
                            viewModel.loading.postValue(false)
                            initRecyclerView(it.data.items)
                        }
                    }
                    Status.ERROR -> {
                        viewModel.loading.postValue(false)
                        Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        viewModel.loading.postValue(true)
                    }
                }
            }
        }
    }

    private fun checkConnection() {
        NetworkStatusHelper(this).observe(this) {
            if (it == NetworkStatus.Available) {
                binding.detailsMain.visibility = View.VISIBLE
                binding.networkLayout.root.visibility = View.GONE
                initVM()
            } else {
                binding.detailsMain.visibility = View.GONE
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

    private fun onItemClick() {
        Intent(this, PlaylistActivity::class.java).apply {
            startActivity(this)
        }
    }
}
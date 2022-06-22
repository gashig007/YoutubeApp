package com.geektech.youtubeapp.ui.playlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.geektech.youtubeapp.core.network.result.Status
import com.geektech.youtubeapp.core.ui.BaseActivity
import com.geektech.youtubeapp.data.remote.model.Item
import com.geektech.youtubeapp.databinding.ActivityPlaylistBinding
import com.geektech.youtubeapp.ui.playlist_detail.PlaylistDetailActivity
import com.geektech.youtubeapp.utils.NetworkStatus
import com.geektech.youtubeapp.utils.NetworkStatusHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : BaseActivity<ActivityPlaylistBinding, PlaylistViewModel>() {

    override val viewModel: PlaylistViewModel by viewModel()

    override fun initViewModel() {
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.setOnBoard(true)

        initVM()
    }

    private fun initRecyclerView(playlistsList: List<Item>) {
        binding.recycler.adapter = PlaylistAdapter(playlistsList, this::onItemClick)
    }

    private fun onItemClick(channelId: String, title: String, description: String) {
        Intent(this, PlaylistDetailActivity::class.java).apply {
            putExtra(idPaPda, channelId)
            putExtra(titlePaPda, title)
            putExtra(descriptionPaPda, description)
            startActivity(this)
        }
    }

    private fun initVM() {
        viewModel.getPlaylists().observe(this) {
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

    private fun checkConnection() {
        NetworkStatusHelper(this).observe(this) {
            if (it == NetworkStatus.Available) {
                binding.recycler.visibility = View.VISIBLE
                binding.networkLayout.root.visibility = View.GONE
                initVM()
            } else {
                binding.recycler.visibility = View.GONE
                binding.networkLayout.root.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val idPaPda = "idPaPda"
        const val titlePaPda = "titlePaPda"
        const val descriptionPaPda = "descriptionPaPda"
    }

    override fun checkInternet() {
        checkConnection()

        binding.networkLayout.btnTryAgain.setOnClickListener {
            checkConnection()
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(inflater)
    }
}
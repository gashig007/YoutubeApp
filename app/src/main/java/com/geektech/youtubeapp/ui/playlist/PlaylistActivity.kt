package com.geektech.youtubeapp.ui.playlist


import android.content.Intent
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.geektech.youtubeapp.adapter.PlaylistAdapter
import com.geektech.youtubeapp.base.BaseActivity
import com.geektech.youtubeapp.databinding.ActivityPlaylistBinding
import com.geektech.youtubeapp.model.Item

class PlaylistActivity : BaseActivity<ActivityPlaylistBinding, PlaylistViewModel>() {

    companion object {
        const val KEY = "key"
    }

    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(inflater)
    }

    override fun initView() {
    }

    override fun initListener() {

    }

    override fun initViewModel() {
        viewModel.getPlaylist().observe(this) {
            Toast.makeText(this, it.kind, Toast.LENGTH_SHORT).show()
            initRecyclerView(it.items)
        }
    }

    private fun initRecyclerView(item: List<Item>) {
        binding.recycler.adapter = PlaylistAdapter(item, this::onItemClick)

    }

    override fun checkInternet() {

    }

    private fun onItemClick(channelId: String){
        val intent = Intent(this, PlaylistDetailActivity::class.java)
        intent.putExtra(KEY,channelId)
        startActivity(intent)

    }
}
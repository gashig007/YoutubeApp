package com.geektech.youtubeapp.ui.playlist_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.youtubeapp.data.remote.model.Item
import com.geektech.youtubeapp.databinding.ItemVideosBinding

class PlaylistDetailAdapter(private val list:List<Item>, private val onItemClick: (itemsId: String) -> Unit?): RecyclerView.Adapter<PlaylistDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(items: Item) {
            Glide.with(binding.root).load(items.snippet.thumbnails.default.url).into(binding.imageEv)
            binding.playlistNameTv.text = items.snippet.title
            binding.timeTv.text = items.snippet.publishedAt.dropLast(10)
        }
    }
}
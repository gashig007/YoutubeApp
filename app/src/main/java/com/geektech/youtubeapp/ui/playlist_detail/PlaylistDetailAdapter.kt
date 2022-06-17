package com.geektech.youtubeapp.ui.playlist_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.youtubeapp.R
import com.geektech.youtubeapp.core.extentions.load
import com.geektech.youtubeapp.data.remote.model.Item
import com.geektech.youtubeapp.databinding.ItemVideosBinding

class PlaylistDetailAdapter(private val list:List<Item>, private val onItemClick: (itemsId: String, String, String) -> Unit?): RecyclerView.Adapter<PlaylistDetailAdapter.ViewHolder>() {

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
            binding.playlistNameTv.text = items.snippet.title
            if (items.snippet.title == "Private video" || items.snippet.title == "Deleted video") {
                Glide.with(binding.root).load(R.mipmap.ic_launcher).into(binding.imageEv)
            } else {
                if (items.snippet.thumbnails.default.url != null) {
                    binding.imageEv.load(items.snippet.thumbnails.default.url)
                } else if (items.snippet.thumbnails.medium.url != null) {
                    binding.imageEv.load(items.snippet.thumbnails.medium.url)
                } else if (items.snippet.thumbnails.high.url != null) {
                    binding.imageEv.load(items.snippet.thumbnails.high.url)
                } else if (items.snippet.thumbnails.standard.url != null) {
                    binding.imageEv.load(items.snippet.thumbnails.standard.url)
                } else if (items.snippet.thumbnails.maxres.url != null) {
                    binding.imageEv.load(items.snippet.thumbnails.maxres.url)
                }
            }
            binding.timeTv.text = items.snippet.publishedAt.dropLast(10)
            itemView.setOnClickListener {
                onItemClick(items.id, items.snippet.title, items.snippet.description)
            }
        }
    }
}
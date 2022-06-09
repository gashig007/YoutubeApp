package com.geektech.youtubeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.youtubeapp.databinding.ItemPlaylistBinding
import com.geektech.youtubeapp.model.Item

class PlaylistAdapter (var info: List<Item>,private var listener:OnItemClickListener)
    : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    lateinit var binding: ItemPlaylistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        binding = ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(info[position])
    }

    override fun getItemCount() = info.size

    inner class  PlaylistViewHolder(itemView: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Item) {
            Glide.with(binding.root)
                .load(item.snippet.thumbnails.default.url)
                .into(binding.imageEv)
            binding.playlistNameTv.text = item.snippet.title
            binding.playlistCountTv.text = item.contentDetails.itemCount.toString() + "video series"
            itemView.setOnClickListener{
                listener.onItemClick(item.id)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(id : String)
    }
}
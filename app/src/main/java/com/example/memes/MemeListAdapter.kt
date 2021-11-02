package com.example.memes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memes.databinding.ItemMemeLayoutBinding
import com.example.memes.model.Meme

class MemeListAdapter : RecyclerView.Adapter<MemeListAdapter.MemeViewHolder>() {

    var memes = emptyList<Meme>()

    // TODO notify list update more efficiently
    @SuppressLint("NotifyDataSetChanged")
    fun setMemeList(memes: List<Meme>) {
        this.memes = memes.toMutableList()
        notifyDataSetChanged()
    }

    class MemeViewHolder(val binding: ItemMemeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meme: Meme) {
            with(binding) {
                memeIdTextView.text = meme.id
                memeNameTextView.text = meme.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemMemeLayoutBinding.inflate(inflater, parent, false)
        return MemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        holder.bind(memes[position])
    }

    override fun getItemCount(): Int {
        return memes.size
    }
}
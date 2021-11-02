package com.example.memes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memes.databinding.ItemMemeLayoutBinding
import com.example.memes.model.Meme

class MemeListAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<MemeListAdapter.MemeViewHolder>() {

    var memes = emptyList<Meme>()

    // TODO notify list update more efficiently
    @SuppressLint("NotifyDataSetChanged")
    fun setMemeList(memes: List<Meme>) {
        this.memes = memes.toMutableList()
        notifyDataSetChanged()
    }

    class MemeViewHolder(private val binding: ItemMemeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(meme: Meme) {
            with(binding) {
                memeIdTextView.text = meme.id
                memeNameTextView.text = meme.name
                checkBox.isChecked = meme.checked
            }
        }

        fun setCheckChangeCallback(checkedChangeListener: View.OnClickListener) {
            binding.checkBox.setOnClickListener(checkedChangeListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemMemeLayoutBinding.inflate(inflater, parent, false)
        return MemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val currentMeme = memes[position]
        holder.bind(currentMeme)
        holder.setCheckChangeCallback {
            viewModel.updateMemeCheckedStatus(currentMeme.id, !currentMeme.checked)
        }
    }

    override fun getItemCount(): Int {
        return memes.size
    }
}
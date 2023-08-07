package com.example.vndbviewer.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vndbviewer.data.network.pojo.Tags
import com.example.vndbviewer.databinding.ItemTagBinding
import com.example.vndbviewer.domain.Vn
import java.math.RoundingMode
import java.text.DecimalFormat

class TagListAdapter : ListAdapter<Tags, TagListAdapter.TagViewHolder>(VnDiffCallback) {

    class TagViewHolder(val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root)

    private object VnDiffCallback: DiffUtil.ItemCallback<Tags>() {
        override fun areItemsTheSame(oldItem: Tags, newItem: Tags): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tags, newItem: Tags): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context))
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = getItem(position)
        Log.d("adapter", getItem(position).name)
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_EVEN
        holder.binding.tagName.text = tag.name
        holder.binding.tagRating.text = df.format(tag.rating)
    }
}
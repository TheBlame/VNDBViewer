package com.example.vndbviewer.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.ItemVnInfoBinding
import com.example.vndbviewer.domain.Vn


class VnListAdapter :
    ListAdapter<Vn, VnListAdapter.VnItemViewHolder>(VnDiffCallback()) {

    class VnItemViewHolder(val binding: ItemVnInfoBinding) : RecyclerView.ViewHolder(binding.root)

    var onVnClickListener: ((Vn) -> Unit)? = null

    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VnItemViewHolder {
        Log.d("onCreateViewHolder", "onCreateViewHolder, count: ${++count}")
        val binding = ItemVnInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VnItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VnItemViewHolder, position: Int) {
        val vn = getItem(position)
        holder.binding.tittle.text = vn.title
        holder.binding.rating.text = vn.rating.toString()
        holder.binding.poster.load(vn.image?.url) {
            crossfade(true)
            crossfade(250)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        holder.binding.root.setOnClickListener { onVnClickListener?.invoke(vn) }
    }
}
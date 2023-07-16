package com.example.vndbviewer.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.vndbviewer.domain.Vn

class VnDiffCallback: DiffUtil.ItemCallback<Vn>() {

    override fun areItemsTheSame(oldItem: Vn, newItem: Vn): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vn, newItem: Vn): Boolean {
        return oldItem == newItem
    }
}
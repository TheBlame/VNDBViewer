package com.example.vndbviewer.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vndbviewer.databinding.ItemScreenshotsBinding
import com.example.vndbviewer.domain.ScreenshotList

class ScreenshotGroupAdapter :
    ListAdapter<ScreenshotList, ScreenshotGroupAdapter.ScreenshotGroupViewHolder>(DiffCallback) {

    class ScreenshotGroupViewHolder(val binding: ItemScreenshotsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<ScreenshotList>() {
        override fun areItemsTheSame(oldItem: ScreenshotList, newItem: ScreenshotList): Boolean {
            return oldItem.releaseId == newItem.releaseId
        }

        override fun areContentsTheSame(oldItem: ScreenshotList, newItem: ScreenshotList): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotGroupViewHolder {
        val binding = ItemScreenshotsBinding.inflate(LayoutInflater.from(parent.context))

        return ScreenshotGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotGroupViewHolder, position: Int) {
        val screenshotImgAdapter = ScreenshotImgAdapter()
        val group = getItem(position)
        holder.binding.screenshotsImages.adapter = screenshotImgAdapter
        holder.binding.releaseName.text = group.title
        screenshotImgAdapter.submitList(group.screenshotList)
        Log.d("scr", group.toString())
    }
}

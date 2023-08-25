package com.example.vndbviewer.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.ScreenshotFullscreenImgBinding

class ViewPagerAdapter(private val urlList: Array<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {

    class PagerVH(val binding: ScreenshotFullscreenImgBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val binding = ScreenshotFullscreenImgBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PagerVH(binding)
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        val img = urlList[position]
        holder.binding.screenshotFullscreen.load(img.replace("/st/", "/sf/")) {
            crossfade(true)
            crossfade(250)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }

    override fun getItemCount(): Int {
        return urlList.size
    }
}


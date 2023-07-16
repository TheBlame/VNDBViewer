package com.example.vndbviewer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.ItemVnInfoBinding
import com.example.vndbviewer.network.pojo.Vn


class VnListAdapter(private val context: Context) :
    ListAdapter<Vn, VnListAdapter.VnItemViewHolder>(VnDiffCallback()) {
    class VnItemViewHolder(val binding: ItemVnInfoBinding) : RecyclerView.ViewHolder(binding.root)

    var onVnClickListener: ((Vn) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VnItemViewHolder {
        val binding = ItemVnInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VnItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VnItemViewHolder, position: Int) {
        val vn = getItem(position)
        holder.binding.tittle.text = vn.title
        holder.binding.rating.text = vn.rating.toString()
        holder.binding.poster.load(vn.image?.url) {
            crossfade(true)
            crossfade(200)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        holder.binding.root.setOnClickListener { onVnClickListener?.invoke(vn) }


        //Picasso.get().load(vn.image.url).into(holder.binding.poster)
        /*try {
            Glide.with(context)
                .load(vn.image.url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).transition(withCrossFade())
                .override(LayoutParams.MATCH_PARENT)
                .into(holder.binding.poster)
        } catch (e: Exception) {
            e.message?.let { Log.e("glide", it) }
        }

         */
    }
}
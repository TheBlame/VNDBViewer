package com.example.vndbviewer.adapters

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.vndbviewer.R
import com.example.vndbviewer.databinding.ItemVnInfoBinding
import com.example.vndbviewer.network.pojo.VnList
import com.squareup.picasso.Picasso


class VnListAdapter(private val context: Context) :
    RecyclerView.Adapter<VnListAdapter.VnListViewHolder>() {

    class VnListViewHolder(val binding: ItemVnInfoBinding) : RecyclerView.ViewHolder(binding.root)

    var vnList: List<VnList> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onVnClickListener: OnVnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VnListViewHolder {
        val binding = ItemVnInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VnListViewHolder(binding)
    }

    override fun getItemCount(): Int = vnList.size


    override fun onBindViewHolder(holder: VnListViewHolder, position: Int) {
        val vn = vnList[position]
        holder.binding.tittle.text = vn.title
        holder.binding.rating.text = vn.rating.toString()
        holder.binding.poster.load(vn.image?.url) {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
        holder.binding.root.setOnClickListener { onVnClickListener?.onVnClick(vn) }


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

    interface OnVnClickListener {
        fun onVnClick(vnList: VnList)
    }
}
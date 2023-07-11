package com.example.vndbviewer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vndbviewer.databinding.ItemVnInfoBinding
import com.example.vndbviewer.network.pojo.VnList
import com.squareup.picasso.Picasso


class VnListAdapter(private val context: Context) :
    RecyclerView.Adapter<VnListAdapter.VnListViewHolder>() {

    class VnListViewHolder(val binding: ItemVnInfoBinding): RecyclerView.ViewHolder(binding.root)

    var vnList:List<VnList> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VnListViewHolder {
        val binding = ItemVnInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VnListViewHolder(binding)
    }

    override fun getItemCount(): Int = vnList.size


    override fun onBindViewHolder(holder: VnListViewHolder, position: Int) {
        val vn = vnList[position]
        holder.binding.tittle.text = vn.title
        holder.binding.rating.text = vn.rating.toString()
        Picasso.get().load(vn.image.url).into(holder.binding.poster)
    }
}
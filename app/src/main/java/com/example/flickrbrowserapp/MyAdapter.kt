package com.example.flickrbrowserapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.filckercell.view.*

class MyAdapter(val activity: MainActivity,var item:ArrayList<Item>):RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {
    class ItemViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.filckercell,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var items = item[position]
        holder.itemView.apply {
            textView.text = items.text
            Glide.with(activity).load(items.img).into(imageView)
            llo.setOnClickListener { activity.showimg(items.img) }


        }
}

    override fun getItemCount(): Int = item.size
}
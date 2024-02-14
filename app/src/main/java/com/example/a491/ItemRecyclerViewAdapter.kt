package com.example.a491

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemRecyclerViewAdapter(
    private val items: List<Item>
    //private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    /* Refer and Access each View Element */
    inner class ItemViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Item? = null
        val mItemImage: ImageView = mView.findViewById<ImageView>(R.id.itemImage)
        val mItemTitle: TextView = mView.findViewById<TextView>(R.id.itemTitle)
        val mItemPrice: TextView = mView.findViewById<TextView>(R.id.itemPrice)
        /*val mItemDesc: TextView = mView.findViewById<TextView>(R.id.itemDesc)

        override fun toString(): String {
            return mItemTitle.toString() + " '" + mItemDesc.text + "'"
        }*/
    }

    /* Binds Views in the ViewHolder with the Data obtained */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position] // Get specific item

        /* Populate Text */
        holder.mItem = item
        holder.mItemTitle.text = item.itemTitle
        holder.mItemPrice.text = item.itemPrice
        //holder.mItemDesc.text = item.itemDesc

        /* Set image using Glide */
        Glide.with(holder.mView)
            .load(item.itemImageUrl)
            .centerInside()
            .placeholder(R.drawable.loading) // Loading Image
            .error(R.drawable.placeholder) // Error Image for when image is isn't found
            .into(holder.mItemImage)

        holder.mView.setOnClickListener {
            //TODO: open item page
        }
    }

    //Mandatory RecyclerView adapter method
    override fun getItemCount(): Int {
        return items.size
    }


}
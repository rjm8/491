package com.example.a491

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ITEM_EXTRA = "ITEM_EXTRA"
class ItemRecyclerViewAdapter(
    private val items: List<Item>,
    private val context: Context,
    private val vertical: Boolean,
    private val currently_renting: Boolean,
    private val listing: Boolean,
    private val previously_rented: Boolean
    )
    : RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View
        if(vertical == true) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.horizontal_item_card, parent, false)
        }
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
//            .load(ContextCompat.getDrawable(context, R.drawable.shekhmus))
            .centerInside()
            .placeholder(R.drawable.loading) // Loading Image
            .error(R.drawable.placeholder) // Error Image for when image is isn't found
            .into(holder.mItemImage)

        holder.mView.setOnClickListener {
            //TODO: open item page
            val intent: Intent
            if (currently_renting) {
                intent = Intent(context, CurrentlyRentItemDetail::class.java)
            } else if (listing) {
                intent = Intent(context, ListedItemDetail::class.java)
            } else if (previously_rented){
                if (item.itemAvailable) {
                    intent = Intent(context, ItemDetail::class.java)
                } else {
                    Toast.makeText(context, "Item not available currently", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                intent = Intent(context, ItemDetail::class.java)
            }
            intent.putExtra(ITEM_EXTRA, item)
            context.startActivity(intent)
        }
    }

    //Mandatory RecyclerView adapter method
    override fun getItemCount(): Int {
        return items.size
    }


}
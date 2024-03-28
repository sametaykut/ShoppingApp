package com.samet.shoppapp.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samet.shoppapp.R
import com.samet.shoppapp.models.ProductItem


class ShoppingItemsAdapter : RecyclerView.Adapter<ShoppingItemsAdapter.ShoppingItemViewHolder>() {

    inner class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    lateinit var itemImage: ImageView
    lateinit var itemTitle: TextView
    lateinit var itemDescription: TextView
    lateinit var itemPrice: TextView
    lateinit var itemRating: TextView

    private val differCallback = object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(
            oldItem: ProductItem,
            newItem: ProductItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductItem,
            newItem: ProductItem
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_items, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ProductItem) -> Unit)? = null

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = differ.currentList[position]
        itemImage = holder.itemView.findViewById(R.id.itemImage)
        itemTitle = holder.itemView.findViewById(R.id.itemTitle)
        itemDescription = holder.itemView.findViewById(R.id.itemDescription)
        itemPrice = holder.itemView.findViewById(R.id.itemPrice)
        itemRating = holder.itemView.findViewById(R.id.itemRating)

        holder.itemView.apply {
           Glide.with(this).load(item.image).into(itemImage)
            itemTitle.text = item.title
            itemDescription.text = item.description
            itemPrice.text = item.price.toString()
            itemRating.text = item.rating.rate.toString()

            if ( 4.0 <= item.rating.rate && item.rating.rate <= 5.0){
                val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.baseline_star_24)
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                itemRating.setCompoundDrawables(drawable, null, null, null)
            }else if ( 2.5 <= item.rating.rate && item.rating.rate < 4.0  ){
                val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.baseline_star_half_24)
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                itemRating.setCompoundDrawables(drawable, null, null, null)
            }else{
                val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.baseline_star_border_24)
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                itemRating.setCompoundDrawables(drawable, null, null, null)
            }



            setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }

            }
        }
    }

    fun setOnClickListener(listener: (ProductItem) -> Unit) {
        onItemClickListener = listener
    }
}
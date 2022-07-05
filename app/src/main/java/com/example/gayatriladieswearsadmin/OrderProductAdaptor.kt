package com.example.gayatriladieswearsadmin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OrderProductAdaptor(private val context: Context, private var fragment: Fragment, private var list: ArrayList<CartItem>) : RecyclerView.Adapter<OrderProductAdaptor.myViewHolder>()  {

    class myViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.order_product_image)
        var name = view.findViewById<TextView>(R.id.order_product_name)
        var dis = view.findViewById<TextView>(R.id.order_product_des)
        var size = view.findViewById<TextView>(R.id.order_product_size)
        var color = view.findViewById<TextView>(R.id.order_product_color)
        var price = view.findViewById<TextView>(R.id.order_product_price)
        var quantity_text = view.findViewById<TextView>(R.id.order_product_quantity)
        var sku = view.findViewById<TextView>(R.id.order_sku)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return OrderProductAdaptor.myViewHolder(
            LayoutInflater.from(context).inflate(R.layout.order_product_iteam, parent, false)
        )
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val model = list[position]

        Glide
            .with(context)
            .load(model.image)
            .placeholder(R.drawable.ic_baseline_image_24)
            .centerCrop()
            .into(holder.image)

        holder.name.text = model.name
        holder.dis.text = model.dis
        holder.price.text = model.price.toString()
        holder.size.text = model.size
        holder.color.text = model.color
        holder.quantity_text.text = "Quantity - ${model.cartQuantity}"
        holder.sku.text = "SKU - ${model.productId}"
    }

    override fun getItemCount(): Int {
        return list.size
    }


}


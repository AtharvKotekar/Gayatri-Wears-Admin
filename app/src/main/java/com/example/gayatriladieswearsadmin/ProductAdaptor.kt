package com.example.gayatriladieswearsadmin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class ProductAdaptor(private val context: Context, private var fragment: EditProductFragment, private var list: ArrayList<Products>) : RecyclerView.Adapter<ProductAdaptor.myViewHolder>()  {
    class myViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.product_image)
        var name = view.findViewById<TextView>(R.id.product_name)
        var dis = view.findViewById<TextView>(R.id.product_des)
        var price = view.findViewById<TextView>(R.id.product_price)
        var mrp = view.findViewById<TextView>(R.id.mrp_text)
        var category = view.findViewById<TextView>(R.id.product_category)
        var color = view.findViewById<TextView>(R.id.product_color)
        var delet = view.findViewById<ImageView>(R.id.delete_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return ProductAdaptor.myViewHolder(
            LayoutInflater.from(context).inflate(R.layout.product_iteam, parent, false)
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
        holder.price.text = "Price - ${model.price}Rs"
        holder.mrp.text = "Mrp - ${model.mrp}Rs"
        holder.category.text = "Category - ${model.category}"
        holder.color.text = "Color - ${model.color}"
        holder.delet.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(context)
            dialog.setTitle("Remove Product")
            dialog.setMessage("Do you really want to remove ${model.name} from Product List?")
            dialog.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            dialog.setPositiveButton("Remove") { dialog, which ->
                fragment.dialog.show()
                FirebaseFirestore.getInstance().collection("Products")
                    .document(model.id)
                    .delete()
                fragment.getData()
            }
            dialog.show()
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name",model.name)
            bundle.putString("dis",model.dis)
            bundle.putInt("price",model.price)
            bundle.putInt("mrp",model.mrp)
            bundle.putInt("stock",model.stock)
            bundle.putString("category",model.category)
            bundle.putString("color",model.color)
            bundle.putString("fabric",model.fabric)
            bundle.putString("pattern",model.pattern)
            bundle.putString("occasion",model.occasion)
            bundle.putString("deal",model.deal)
            bundle.putStringArrayList("size",model.size)
            bundle.putStringArrayList("tag",model.tag)
            bundle.putString("id",model.id)
            bundle.putString("image",model.image)

            holder.itemView.findNavController().navigate(R.id.action_editProductFragment_to_editProductDetialFragment,bundle)
        }

    }

    override fun getItemCount(): Int {
        return  list.size
    }
}
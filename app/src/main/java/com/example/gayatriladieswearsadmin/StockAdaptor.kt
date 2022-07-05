package com.example.gayatriladieswearsadmin

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class StockAdaptor(private val context: Context, private var fragment: AddStockFragment, private var list: ArrayList<Products>) : RecyclerView.Adapter<StockAdaptor.myViewHolder>()   {
    class myViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.stock_image)
        var name = view.findViewById<TextView>(R.id.stock_name)
        var dis = view.findViewById<TextView>(R.id.stock_dis)
        var price = view.findViewById<TextView>(R.id.stock_price)
        var stock = view.findViewById<TextView>(R.id.stock_text)
        var category = view.findViewById<TextView>(R.id.stock_category)
        var color = view.findViewById<TextView>(R.id.stock_color)
        var edittext = view.findViewById<EditText>(R.id.stock_edittext)
        var addBtn = view.findViewById<Button>(R.id.stock_add_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return StockAdaptor.myViewHolder(
            LayoutInflater.from(context).inflate(R.layout.stock_iteam, parent, false)
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
        holder.stock.text = "Current Stock - ${model.stock.toString()}"
        holder.category.text = "Category - ${model.category}"
        holder.color.text = "Color - ${model.color}"

        holder.addBtn.setOnClickListener {
            if(holder.edittext.text.toString() == ""){
                val snackbar = Snackbar.make(context,fragment.view!!.rootView, "Please Add Stock", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                fragment.vibratePhone()
            }
            else{
                fragment.dialog.show()
                FirebaseFirestore.getInstance().collection("Products")
                    .document(model.id)
                    .update("stock",(model.stock + holder.edittext.text.toString().toInt()))
                    .addOnSuccessListener {
                        fragment.getStockData()
                    }
                    .addOnFailureListener {
                        val snackbar = Snackbar.make(context,fragment.view!!.rootView, "${it.localizedMessage}", Snackbar.LENGTH_LONG)
                        val snackbarView = snackbar.view
                        snackbarView.setBackgroundColor(Color.RED)
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                        fragment.vibratePhone()
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}
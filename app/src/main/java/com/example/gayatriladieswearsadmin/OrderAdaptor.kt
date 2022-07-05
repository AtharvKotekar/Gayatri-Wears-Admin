package com.example.gayatriladieswearsadmin

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class OrderAdaptor(private val context: Context, private var fragment: PendingOrderFragment, private var list: ArrayList<Order>) : RecyclerView.Adapter<OrderAdaptor.myViewHolder>()  {

    class myViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var status = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_iteam, parent, false)
        return OrderAdaptor.myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val model = list[position]
        if("R" in model.orderId){
            holder.itemView.findViewById<TextView>(R.id.order_id).text = model.orderId
            holder.itemView.findViewById<TextView>(R.id.order_transactionId).text = model.transactionId
            holder.itemView.findViewById<TextView>(R.id.order_quantity).text = model.totalQuantity
            holder.itemView.findViewById<TextView>(R.id.order_price).text = model.amout.toString()
            holder.itemView.findViewById<TextView>(R.id.order_date_text).text = model.date


            holder.status = "Returned"
            holder.itemView.findViewById<TextView>(R.id.order_status).text = holder.status
            holder.itemView.findViewById<TextView>(R.id.order_status).setTextColor(fragment.resources.getColor(android.R.color.darker_gray))
        }else if ("C" in model.orderId){
            holder.itemView.findViewById<TextView>(R.id.order_id).text = model.orderId
            holder.itemView.findViewById<TextView>(R.id.order_transactionId).text = model.transactionId
            holder.itemView.findViewById<TextView>(R.id.order_quantity).text = model.totalQuantity
            holder.itemView.findViewById<TextView>(R.id.order_price).text = model.amout.toString()
            holder.itemView.findViewById<TextView>(R.id.order_date_text).text = model.date


            holder.status = "Canceled"
            holder.itemView.findViewById<TextView>(R.id.order_status).text = holder.status
            holder.itemView.findViewById<TextView>(R.id.order_status).setTextColor(fragment.resources.getColor(android.R.color.holo_red_dark))
        }else{
            holder.itemView.findViewById<TextView>(R.id.order_id).text = model.orderId
            holder.itemView.findViewById<TextView>(R.id.order_transactionId).text = model.transactionId
            holder.itemView.findViewById<TextView>(R.id.order_quantity).text = model.totalQuantity
            holder.itemView.findViewById<TextView>(R.id.order_price).text = model.amout.toString()
            holder.itemView.findViewById<TextView>(R.id.order_date_text).text = model.date
            if(model.courierId == ""){
                holder.status = "Packing"
            }
            holder.itemView.findViewById<TextView>(R.id.order_status).text = holder.status

            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id",model.orderId)
                bundle.putString("transactionId",model.transactionId)
                bundle.putString("amout",model.amout.toString())
                bundle.putString("date",model.date)
                bundle.putString("status",holder.status)
                bundle.putString("userId",model.userId)
                bundle.putString("address",model.address)
                bundle.putString("pincode",model.pincode)
                bundle.putString("name",model.name)
                bundle.putString("phone",model.contact)
                bundle.putString("landmark",model.landMark)
                bundle.putString("tag",model.userId)
                bundle.putString("totalQuantity",model.totalQuantity)
                bundle.putString("email",model.email)
                bundle.putParcelableArrayList("orderedProducts", model.orderedProducts)
                holder.itemView.findNavController().navigate(R.id.action_pendingOrderFragment_to_orderDetailFragment,bundle)
            }
        }



    }

    override fun getItemCount(): Int {
        return list.size
    }

}
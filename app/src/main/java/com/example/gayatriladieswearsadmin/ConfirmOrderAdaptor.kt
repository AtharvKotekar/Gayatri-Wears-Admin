package com.example.gayatriladieswearsadmin

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfirmOrderAdaptor (private val context: Context, private var fragment: Fragment, private var list: ArrayList<Order>) : RecyclerView.Adapter<ConfirmOrderAdaptor.myViewHolder>() {

    class myViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.confirm_order_iteam, parent, false)
        return ConfirmOrderAdaptor.myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {


        val model = list[position]

        when(fragment){
            is ConfirmOrderFragment -> {
                if("R" in model.orderId){
                    holder.itemView.findViewById<TextView>(R.id.order_id).text = model.orderId
                    holder.itemView.findViewById<TextView>(R.id.order_transactionId).text = model.transactionId
                    holder.itemView.findViewById<TextView>(R.id.order_quantity).text = model.totalQuantity
                    holder.itemView.findViewById<TextView>(R.id.order_price).text = model.amout.toString()
                    holder.itemView.findViewById<TextView>(R.id.order_date_text).text = model.date

                    holder.itemView.findViewById<TextView>(R.id.order_status).text = "Returned"
                    holder.itemView.findViewById<TextView>(R.id.order_status).setTextColor(fragment.resources.getColor(android.R.color.darker_gray))
                    (fragment as ConfirmOrderFragment).dialog.dismiss()
                }else if ("C" in model.orderId){
                    holder.itemView.findViewById<TextView>(R.id.order_id).text = model.orderId
                    holder.itemView.findViewById<TextView>(R.id.order_transactionId).text = model.transactionId
                    holder.itemView.findViewById<TextView>(R.id.order_quantity).text = model.totalQuantity
                    holder.itemView.findViewById<TextView>(R.id.order_price).text = model.amout.toString()
                    holder.itemView.findViewById<TextView>(R.id.order_date_text).text = model.date

                    holder.itemView.findViewById<TextView>(R.id.order_status).text = "Canceled"
                    holder.itemView.findViewById<TextView>(R.id.order_status).setTextColor(fragment.resources.getColor(android.R.color.holo_red_dark))
                    (fragment as ConfirmOrderFragment).dialog.dismiss()
                }else{
                    holder.itemView.findViewById<TextView>(R.id.order_id).text = model.orderId
                    holder.itemView.findViewById<TextView>(R.id.order_transactionId).text = model.transactionId
                    holder.itemView.findViewById<TextView>(R.id.order_quantity).text = model.totalQuantity
                    holder.itemView.findViewById<TextView>(R.id.order_price).text = model.amout.toString()
                    holder.itemView.findViewById<TextView>(R.id.order_date_text).text = model.date

                    CoroutineScope(IO).launch {
                        val retrofitBuilder = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BASE_URL)
                            .build()

                        val orderServices = retrofitBuilder.create(OrderServices::class.java)

                        val tokenc = Token("gayatriauth@gmail.com","Gayatri@2022")
                        val call = orderServices.getToken(tokenc)
                        call.enqueue(object :Callback<TokenCall>{
                            override fun onResponse(call: Call<TokenCall>, response: Response<TokenCall>) {
                                if (response.isSuccessful){
                                    val token = "Bearer ${response.body()!!.token}"

                                    val headerMap: HashMap<String, String> = HashMap<String,String>()
                                    headerMap["Content-Type"] = "application/json"
                                    headerMap["Authorization"] = token

                                    val ordercall = orderServices.getOrderDetail(headerMap,model.orderId)
                                    ordercall.enqueue(object :Callback<JsonObject>{
                                        override fun onResponse(
                                            call: Call<JsonObject>,
                                            response2: Response<JsonObject>
                                        ) {
                                            if(response2.isSuccessful){
                                                val data = response2.body()?.getAsJsonObject("data")?.get("status").toString()
                                                holder.itemView.findViewById<TextView>(R.id.order_status).text = data.replace("\"", "")
                                                (fragment as ConfirmOrderFragment).dialog.dismiss()
                                            }else{
                                                (fragment as ConfirmOrderFragment).dialog.dismiss()
                                                Log.e(TAG, "onResponse: ${response2.raw()}", )
                                                val snackBar = Snackbar.make(
                                                    fragment.requireActivity().findViewById(android.R.id.content),
                                                    "Something Went Wrong",
                                                    Snackbar.LENGTH_LONG
                                                )
                                                snackBar.setBackgroundTint(context.resources.getColor(android.R.color.holo_red_dark))
                                                snackBar.setTextColor(context.resources.getColor(R.color.white))
                                                snackBar.show()
                                                fragment.vibratePhone()
                                            }
                                        }

                                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                            (fragment as ConfirmOrderFragment).dialog.dismiss()
                                            Log.e(TAG, "onResponse: ${t.localizedMessage}", )
                                            val snackBar = Snackbar.make(
                                                fragment.requireActivity().findViewById(android.R.id.content),
                                                "Something Went Wrong",
                                                Snackbar.LENGTH_LONG
                                            )
                                            snackBar.setBackgroundTint(context.resources.getColor(android.R.color.holo_red_dark))
                                            snackBar.setTextColor(context.resources.getColor(R.color.white))
                                            snackBar.show()
                                            fragment.vibratePhone()
                                        }

                                    })


                                }else{
                                    (fragment as ConfirmOrderFragment).dialog.dismiss()
                                    Log.e(TAG, "onResponse: ${response.raw()}", )
                                    val snackBar = Snackbar.make(
                                        fragment.requireActivity().findViewById(android.R.id.content),
                                        "Something Went Wrong",
                                        Snackbar.LENGTH_LONG
                                    )
                                    snackBar.setBackgroundTint(context.resources.getColor(android.R.color.holo_red_dark))
                                    snackBar.setTextColor(context.resources.getColor(R.color.white))
                                    snackBar.show()
                                    fragment.vibratePhone()
                                }
                            }

                            override fun onFailure(call: Call<TokenCall>, t: Throwable) {
                                (fragment as ConfirmOrderFragment).dialog.dismiss()
                                Log.e(TAG, "onResponse: ${t.localizedMessage}", )
                                val snackBar = Snackbar.make(
                                    fragment.requireActivity().findViewById(android.R.id.content),
                                    "Something Went Wrong",
                                    Snackbar.LENGTH_LONG
                                )
                                snackBar.setBackgroundTint(context.resources.getColor(android.R.color.holo_red_dark))
                                snackBar.setTextColor(context.resources.getColor(R.color.white))
                                snackBar.show()
                                fragment.vibratePhone()
                            }

                        })
                    }
                }
            }
        }



    }



    override fun getItemCount(): Int {
        return list.size
    }
}
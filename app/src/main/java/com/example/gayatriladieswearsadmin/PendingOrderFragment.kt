package com.example.gayatriladieswearsadmin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gayatriladieswearsadmin.databinding.FragmentAddStockBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentPendingOrderBinding
import com.google.firebase.firestore.FirebaseFirestore

class PendingOrderFragment : Fragment() {
    private lateinit var binding: FragmentPendingOrderBinding
    lateinit var dialog: Dialog
    lateinit var adaptor:OrderAdaptor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPendingOrderBinding.inflate(inflater,container,false)

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

        getOrderData()

        return binding.root
    }

    fun getOrderData(){
        FirebaseFirestore.getInstance().collection("Orders")
            .whereEqualTo("courierId","")
            .get()
            .addOnSuccessListener {
                val iteamList: ArrayList<Order> = ArrayList()
                for (i in it.documents){
                    val iteam = i.toObject(Order::class.java)
                    iteamList.add(iteam!!)
                }
                listStockData(iteamList)
            }
    }

    private fun listStockData(iteamList: ArrayList<Order>) {
        adaptor = OrderAdaptor(requireContext(),this,iteamList)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        dialog.dismiss()
    }
}
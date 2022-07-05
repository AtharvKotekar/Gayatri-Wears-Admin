package com.example.gayatriladieswearsadmin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gayatriladieswearsadmin.databinding.FragmentAddStockBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentEditProductBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddStockFragment : Fragment() {
    lateinit var dialog: Dialog
    private lateinit var binding:FragmentAddStockBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStockBinding.inflate(inflater,container,false)
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

        getStockData()






        return binding.root
    }

    fun listStockData(iteamList:ArrayList<Products>){
        val adaptor = StockAdaptor(requireActivity(),this,iteamList)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        dialog.dismiss()
    }


    fun getStockData(){
        FirebaseFirestore.getInstance().collection("Products")
            .whereLessThanOrEqualTo("stock",2)
            .get()
            .addOnSuccessListener {
                val iteamList: ArrayList<Products> = ArrayList()
                for (i in it.documents){
                    val iteam = i.toObject(Products::class.java)
                    iteamList.add(iteam!!)
                }
                listStockData(iteamList)
            }
    }

}
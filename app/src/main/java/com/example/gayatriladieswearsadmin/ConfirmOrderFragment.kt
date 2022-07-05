package com.example.gayatriladieswearsadmin

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gayatriladieswearsadmin.databinding.FragmentAddProductBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentConfirmOrderBinding
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmOrderFragment : Fragment() {
    lateinit var binding: FragmentConfirmOrderBinding
    lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmOrderBinding.inflate(inflater,container,false)


        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

        getData()


        return binding.root
    }

    fun getData() {
        FirebaseFirestore.getInstance().collection("Orders")
            .whereNotEqualTo("courierId","")
            .get()
            .addOnSuccessListener {
                val iteamList: ArrayList<Order> = ArrayList()
                for (i in it.documents){
                    val iteam = i.toObject(Order::class.java)
                    iteamList.add(iteam!!)
                }
                listOrderData(iteamList)
            }
    }

    private fun listOrderData(iteamList: ArrayList<Order>) {
        val adaptor = ConfirmOrderAdaptor(requireContext(),this,iteamList)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }

}
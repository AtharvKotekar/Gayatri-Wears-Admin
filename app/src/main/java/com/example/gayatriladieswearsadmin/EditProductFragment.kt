package com.example.gayatriladieswearsadmin

import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gayatriladieswearsadmin.databinding.FragmentEditProductBinding
import com.google.firebase.firestore.FirebaseFirestore

class EditProductFragment : Fragment() {

    private lateinit var binding:FragmentEditProductBinding
    lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProductBinding.inflate(inflater)
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

       getData()

        binding.searchText.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                    dialog.show()
                    getSearchData(textView.text.toString())
                return@setOnEditorActionListener true
            }else{
                return@setOnEditorActionListener false
            }

        }





        return binding.root
    }

    fun getProducts(iteamList:ArrayList<Products>){
        val adaptor = ProductAdaptor(requireContext(),this,iteamList)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        dialog.dismiss()
    }

    fun getData(){
        FirebaseFirestore.getInstance().collection("Products")
            .orderBy("name")
            .get()
            .addOnSuccessListener {
                val iteamList: ArrayList<Products> = ArrayList()
                for (i in it.documents){
                    val iteam = i.toObject(Products::class.java)
                    iteamList.add(iteam!!)
                }
                getProducts(iteamList)
            }
    }

    fun getSearchData(searchText:String){
        FirebaseFirestore.getInstance().collection("Products")
            .whereArrayContains("keywords",searchText.lowercase().trim())
            .get()
            .addOnSuccessListener {
                val iteamList: ArrayList<Products> = ArrayList()
                for (i in it.documents){
                    val iteam = i.toObject(Products::class.java)
                    iteamList.add(iteam!!)
                }
                getProducts(iteamList)
            }
            .addOnFailureListener {
                Log.e(TAG, "getSearchData: ${it.localizedMessage}", )
            }
    }


}
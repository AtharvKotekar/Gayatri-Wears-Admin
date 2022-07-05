package com.example.gayatriladieswearsadmin

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gayatriladieswearsadmin.databinding.FragmentEditProductDetialBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentOrderDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class OrderDetailFragment : Fragment() {
    var id: String = ""
    var transactionId: String = ""
    var amout: String = ""
    var date: String = ""
    var status: String = ""
    var address: String = ""
    var pincode: String = ""
    var name: String = ""
    var phone: String = ""
    var landmark: String = ""
    var tAg : String = ""
    var userId: String = ""
    var email: String = ""
    var totalQuantity: String = ""
    lateinit var orderedProducts: ArrayList<CartItem>

    lateinit var mdialog: Dialog
   private lateinit var binding: FragmentOrderDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailBinding.inflate(inflater,container,false)


        mdialog = Dialog(requireContext())
        mdialog.setContentView(R.layout.loading_dialog)
        mdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mdialog.setCancelable(false)


        id = arguments?.getString("id").toString()
        transactionId = arguments?.getString("transactionId").toString()
        amout = arguments?.getString("amout").toString()
        date = arguments?.getString("date").toString()
        status = arguments?.getString("status").toString()
        userId = arguments?.getString("userId").toString()
        address = arguments?.getString("address").toString()
        pincode = arguments?.getString("pincode").toString()
        name = arguments?.getString("name").toString()
        phone = arguments?.getString("phone").toString()
        landmark = arguments?.getString("landmark").toString()
        email = arguments?.getString("email").toString()
        orderedProducts = arguments?.getParcelableArrayList<CartItem>("orderedProducts") as ArrayList<CartItem>
        totalQuantity = arguments?.getString("totalQuantity").toString()


        binding.orderId.text = "Order ID - $id"
        binding.orderTransactionId.text = "Transaction ID - $transactionId"
        binding.orderAmout.text = "Amount - $amout"
        binding.orderDate.text = "Date - $date"
        binding.orderAddress.text = "Address - $address"
        binding.orderPincode.text = "Pincode - $pincode"
        binding.orderName.text = "Name - $name"
        binding.orderLandmark.text = "LandMark - $landmark"
        binding.orderTotalquantity.text = "Total Quantity - $totalQuantity"
        binding.orderPhone.text = "Phone - ${phone}"
        binding.orderMail.text = "Email - ${email}"







        getProducts(orderedProducts)


        return binding.root
    }


    fun getProducts(itemlist:ArrayList<CartItem>){
        val adaptor = OrderProductAdaptor(requireContext(),this,itemlist)
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }



}
package com.example.gayatriladieswearsadmin

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.gayatriladieswearsadmin.databinding.FragmentAddProductBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentAddStockBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentEditProductBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import java.security.cert.Extension
import java.util.*
import kotlin.collections.ArrayList

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var imageUrl:String
    lateinit var listSize:ArrayList<CheckBox>
    lateinit var listtag:ArrayList<CheckBox>
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater,container,false)
        imageUrl = ""

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

        listSize = ArrayList()
        listSize.add(binding.s)
        listSize.add(binding.m)
        listSize.add(binding.l)
        listSize.add(binding.xl)
        listSize.add(binding.xxl)
        listSize.add(binding.xxxl)
        listSize.add(binding.xxxxl)
        listSize.add(binding.xxxxxl)

        listtag = ArrayList()
        listtag.add(binding.spotlighton)
        listtag.add(binding.traditional)
        listtag.add(binding.newCollection)


        binding.addLoadImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }


        binding.addProductBtn.setOnClickListener {
            if(binding.addProductName.text.toString() == ""){
                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Name.", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                vibratePhone()

            }else{
                if (binding.addProductDes.text.toString() == ""){
                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Description.", Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    snackbarView.setBackgroundColor(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                    vibratePhone()

                }else{
                    if(binding.addPrice.text.toString() == ""){
                        val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Final Price.", Snackbar.LENGTH_LONG)
                        val snackbarView = snackbar.view
                        snackbarView.setBackgroundColor(Color.RED)
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                        vibratePhone()
                    }else{
                        if(binding.addMrp.text.toString() == ""){
                            val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product MRP.", Snackbar.LENGTH_LONG)
                            val snackbarView = snackbar.view
                            snackbarView.setBackgroundColor(Color.RED)
                            snackbar.setTextColor(Color.WHITE)
                            snackbar.show()
                            vibratePhone()
                        }else{
                            if(binding.addStock.text.toString() == ""){
                                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Stock.", Snackbar.LENGTH_LONG)
                                val snackbarView = snackbar.view
                                snackbarView.setBackgroundColor(Color.RED)
                                snackbar.setTextColor(Color.WHITE)
                                snackbar.show()
                                vibratePhone()
                            }else{
                                if(binding.spinnerCategory.selectedItem.toString() == "Category"){
                                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Category.", Snackbar.LENGTH_LONG)
                                    val snackbarView = snackbar.view
                                    snackbarView.setBackgroundColor(Color.RED)
                                    snackbar.setTextColor(Color.WHITE)
                                    snackbar.show()
                                    vibratePhone()
                                }else{
                                    if(binding.spinnerColor.selectedItem.toString() == "Color"){
                                        val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Color.", Snackbar.LENGTH_LONG)
                                        val snackbarView = snackbar.view
                                        snackbarView.setBackgroundColor(Color.RED)
                                        snackbar.setTextColor(Color.WHITE)
                                        snackbar.show()
                                        vibratePhone()
                                    }else{
                                        if(binding.spinnerMaterial.selectedItem.toString() == "Material"){
                                            val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Material.", Snackbar.LENGTH_LONG)
                                            val snackbarView = snackbar.view
                                            snackbarView.setBackgroundColor(Color.RED)
                                            snackbar.setTextColor(Color.WHITE)
                                            snackbar.show()
                                            vibratePhone()
                                        }else{
                                            if(binding.spinnerPattern.selectedItem.toString() == "Pattern"){
                                                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Pattern.", Snackbar.LENGTH_LONG)
                                                val snackbarView = snackbar.view
                                                snackbarView.setBackgroundColor(Color.RED)
                                                snackbar.setTextColor(Color.WHITE)
                                                snackbar.show()
                                                vibratePhone()
                                            }else{
                                                if(binding.spinnerOccasion.selectedItem.toString() == "Occasion"){
                                                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Occcasion.", Snackbar.LENGTH_LONG)
                                                    val snackbarView = snackbar.view
                                                    snackbarView.setBackgroundColor(Color.RED)
                                                    snackbar.setTextColor(Color.WHITE)
                                                    snackbar.show()
                                                    vibratePhone()
                                                }else{
                                                    if(binding.spinnerDeal.selectedItem.toString() == "Deal"){
                                                        val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Deal.", Snackbar.LENGTH_LONG)
                                                        val snackbarView = snackbar.view
                                                        snackbarView.setBackgroundColor(Color.RED)
                                                        snackbar.setTextColor(Color.WHITE)
                                                        snackbar.show()
                                                        vibratePhone()
                                                    }else{
                                                        val checkList:ArrayList<CheckBox> = ArrayList()
                                                        for(i in listSize){
                                                            if (i.isChecked){
                                                                checkList.add(i)
                                                            }
                                                        }
                                                        if(checkList.isEmpty()){
                                                            val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Size.", Snackbar.LENGTH_LONG)
                                                            val snackbarView = snackbar.view
                                                            snackbarView.setBackgroundColor(Color.RED)
                                                            snackbar.setTextColor(Color.WHITE)
                                                            snackbar.show()
                                                            vibratePhone()
                                                        }else{
                                                            if(imageUrl == ""){
                                                                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Image.", Snackbar.LENGTH_LONG)
                                                                val snackbarView = snackbar.view
                                                                snackbarView.setBackgroundColor(Color.RED)
                                                                snackbar.setTextColor(Color.WHITE)
                                                                snackbar.show()
                                                                vibratePhone()
                                                            }else{
                                                                val size:ArrayList<String> = ArrayList()
                                                                val tag:ArrayList<String> = ArrayList()
                                                                for (i in listSize){
                                                                    if(i.isChecked){
                                                                        size.add(i.text.toString())
                                                                    }
                                                                }
                                                                for (i in listtag){
                                                                    if(i.isChecked){
                                                                        tag.add(i.text.toString())
                                                                    }
                                                                }
                                                                setProduct(size,tag)
                                                            }

                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            if (data?.data == null){
                Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show()
            }else{
                dialog.show()
                imageUri = data.data
                val imageExtension = imageUri.toString().substring(imageUri.toString().lastIndexOf(".")+1)
                val sRef = FirebaseStorage.getInstance().reference.child("/Products").child(
                    binding.addProductName.text.toString() + "_" + System.currentTimeMillis()+ "." + imageExtension.toString()
                )

                sRef.putFile(imageUri!!)
                    .addOnSuccessListener {takeSnapShot ->
                        takeSnapShot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {
                                dialog.dismiss()
                                imageUrl = it.toString()
                                binding.addImage.setImageURI(imageUri)
                            }
                            .addOnFailureListener {
                                dialog.dismiss()
                                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "${it.localizedMessage}", Snackbar.LENGTH_LONG)
                                val snackbarView = snackbar.view
                                snackbarView.setBackgroundColor(Color.RED)
                                snackbar.setTextColor(Color.WHITE)
                                snackbar.show()
                                vibratePhone()
                            }

                    }

            }

        }
    }

    fun setProduct(size:ArrayList<String>,tag:ArrayList<String>){
        dialog.show()
        val id:String = getRandomString(20)
        val products = Products(binding.addProductName.text.toString(),
        binding.addProductDes.text.toString(),binding.spinnerCategory.selectedItem.toString(),binding.spinnerColor.selectedItem.toString(),
        binding.spinnerDeal.selectedItem.toString(),binding.spinnerMaterial.selectedItem.toString(),id,imageUrl,generateKeywords(binding.addProductName.text.toString().lowercase()),
        binding.addMrp.text.toString().toInt(),binding.addPrice.text.toString().toInt(),binding.spinnerPattern.selectedItem.toString(),binding.addStock.text.toString().toInt(),binding.spinnerOccasion.selectedItem.toString(),size
        ,tag)

        FirebaseFirestore.getInstance().collection("Products")
            .document(id)
            .set(products, SetOptions.merge())
            .addOnSuccessListener {
                dialog.dismiss()
                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Done", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.BLACK)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                vibratePhone()
                binding.addProductName.text = null
                binding.addProductDes.text = null
                binding.addPrice.text = null
                binding.addMrp.text = null
                binding.addStock.text = null
                binding.spinnerCategory.setSelection(0)
                binding.spinnerColor.setSelection(0)
                binding.spinnerMaterial.setSelection(0)
                binding.spinnerPattern.setSelection(0)
                binding.spinnerOccasion.setSelection(0)
                listSize.forEach {
                    it.isChecked = false
                }
                listtag.forEach {
                    it.isChecked = false
                }
                imageUrl = ""
            }
            .addOnFailureListener {
                dialog.dismiss()
                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "${it.localizedMessage}", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                vibratePhone()

            }
    }


    fun getRandomString(length: Int) : String {
        val ALLOWED_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random()
        val sb = StringBuilder(length)
        for (i in 0 until length)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }


    fun generateKeywords(name: String): ArrayList<String> {
        val keywords = ArrayList<String>()
        for (i in 0 until name.length) {
            for (j in (i+1)..name.length) {
                keywords.add(name.slice(i until j))
            }
        }
        return keywords
    }




}
package com.example.gayatriladieswearsadmin

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.gayatriladieswearsadmin.databinding.FragmentEditProductBinding
import com.example.gayatriladieswearsadmin.databinding.FragmentEditProductDetialBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage


class EditProductDetialFragment : Fragment() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    var newImageUrl:String = ""
    var name: String = ""
    var price: String = ""
    var dis: String = ""
    var fabric: String = ""
    var image: String = ""
    var category: String = ""
    var color: String = ""
    var pattern: String = ""
    var occasion: String = ""
    var mrp: String = ""
    var deal: String = ""
    var id: String = ""
    var stock: String = ""
    var tag: ArrayList<String> = ArrayList()
    var size: ArrayList<String> = ArrayList()
    lateinit var mdialog: Dialog
    lateinit var listSize:ArrayList<CheckBox>
    lateinit var listtag:ArrayList<CheckBox>

    private lateinit var binding: FragmentEditProductDetialBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProductDetialBinding.inflate(inflater, container, false)

        mdialog = Dialog(requireContext())
        mdialog.setContentView(R.layout.loading_dialog)
        mdialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mdialog.setCancelable(false)
        mdialog.show()

        name = arguments?.getString("name").toString()
        price = arguments?.getInt("price").toString()
        dis = arguments?.getString("dis").toString()
        fabric = arguments?.getString("fabric").toString()
        image = arguments?.getString("image").toString()
        category = arguments?.getString("category").toString()
        color = arguments?.getString("color").toString()
        pattern = arguments?.getString("pattern").toString()
        occasion = arguments?.getString("occasion").toString()
        deal = arguments?.getString("deal").toString()
        mrp = arguments?.getInt("mrp").toString()
        stock = arguments?.getInt("stock").toString()
        tag = arguments?.getStringArrayList("tag") as ArrayList<String>
        size = arguments?.getStringArrayList("size") as ArrayList<String>
        id = arguments?.getString("id").toString()



        Glide
            .with(requireContext())
            .load(image)
            .placeholder(R.drawable.ic_baseline_image_24)
            .centerCrop()
            .into(binding.editAddImage)

        val categoryArray: Array<out String> = resources.getStringArray(R.array.categories)
        val colorArray: Array<out String> = resources.getStringArray(R.array.colors)
        val fabricArray: Array<out String> = resources.getStringArray(R.array.material)
        val patternArray: Array<out String> = resources.getStringArray(R.array.pattern)
        val occasionArray: Array<out String> = resources.getStringArray(R.array.occasion)
        val dealArray: Array<out String> = resources.getStringArray(R.array.deal)

        binding.editProductName.text = name.toEditable()
        binding.editProductDes.text = dis.toEditable()
        binding.editPrice.text = price.toEditable()
        binding.editMrp.text = mrp.toEditable()
        binding.editStock.text = stock.toEditable()
        binding.editSpinnerCategory.setSelection(categoryArray.indexOf(category))
        binding.editSpinnerColor.setSelection(colorArray.indexOf(color))
        binding.editSpinnerMaterial.setSelection(fabricArray.indexOf(fabric))
        binding.editSpinnerPattern.setSelection(patternArray.indexOf(pattern))
        binding.editSpinnerOccasion.setSelection(occasionArray.indexOf(occasion))
        binding.spinnerDeal.setSelection(dealArray.indexOf(deal))


        for(it in size) {
            when(it.toString()){
                "S" -> { binding.s.isChecked = true}
                "M" -> { binding.m.isChecked = true}
                "L" -> { binding.l.isChecked = true}
                "XL" -> { binding.xl.isChecked = true}
                "XXL" -> { binding.xxl.isChecked = true}
                "XXXL" -> { binding.xxxl.isChecked = true}
                "4XL" -> { binding.xxxxl.isChecked = true}
                "5XL" -> { binding.xxxxxl.isChecked = true}
            }
        }

        for (it in tag){
            when(it.toString()){
                "Spotlight On" -> { binding.spotlighton.isChecked = true}
                "Traditional" -> { binding.traditional.isChecked = true}
                "New Collection" -> { binding.newcollection.isChecked = true}
            }
        }

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
        listtag.add(binding.newcollection)

        mdialog.dismiss()


        binding.addLoadImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.addProductBtn.setOnClickListener {
            if(binding.editProductName.text.toString() == ""){
                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Name.", Snackbar.LENGTH_LONG)
                val snackbarView = snackbar.view
                snackbarView.setBackgroundColor(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
                vibratePhone()

            }else{
                if (binding.editProductDes.text.toString() == ""){
                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Description.", Snackbar.LENGTH_LONG)
                    val snackbarView = snackbar.view
                    snackbarView.setBackgroundColor(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                    vibratePhone()

                }else{
                    if(binding.editPrice.text.toString() == ""){
                        val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Final Price.", Snackbar.LENGTH_LONG)
                        val snackbarView = snackbar.view
                        snackbarView.setBackgroundColor(Color.RED)
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                        vibratePhone()
                    }else{
                        if(binding.editMrp.text.toString() == ""){
                            val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product MRP.", Snackbar.LENGTH_LONG)
                            val snackbarView = snackbar.view
                            snackbarView.setBackgroundColor(Color.RED)
                            snackbar.setTextColor(Color.WHITE)
                            snackbar.show()
                            vibratePhone()
                        }else{
                            if(binding.editStock.text.toString() == ""){
                                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Stock.", Snackbar.LENGTH_LONG)
                                val snackbarView = snackbar.view
                                snackbarView.setBackgroundColor(Color.RED)
                                snackbar.setTextColor(Color.WHITE)
                                snackbar.show()
                                vibratePhone()
                            }else{
                                if(binding.editSpinnerCategory.selectedItem.toString() == "Category"){
                                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Category.", Snackbar.LENGTH_LONG)
                                    val snackbarView = snackbar.view
                                    snackbarView.setBackgroundColor(Color.RED)
                                    snackbar.setTextColor(Color.WHITE)
                                    snackbar.show()
                                    vibratePhone()
                                }else{
                                    if(binding.editSpinnerColor.selectedItem.toString() == "Color"){
                                        val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Color.", Snackbar.LENGTH_LONG)
                                        val snackbarView = snackbar.view
                                        snackbarView.setBackgroundColor(Color.RED)
                                        snackbar.setTextColor(Color.WHITE)
                                        snackbar.show()
                                        vibratePhone()
                                    }else{
                                        if(binding.editSpinnerMaterial.selectedItem.toString() == "Material"){
                                            val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Material.", Snackbar.LENGTH_LONG)
                                            val snackbarView = snackbar.view
                                            snackbarView.setBackgroundColor(Color.RED)
                                            snackbar.setTextColor(Color.WHITE)
                                            snackbar.show()
                                            vibratePhone()
                                        }else{
                                            if(binding.editSpinnerPattern.selectedItem.toString() == "Pattern"){
                                                val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Please Add Product Pattern.", Snackbar.LENGTH_LONG)
                                                val snackbarView = snackbar.view
                                                snackbarView.setBackgroundColor(Color.RED)
                                                snackbar.setTextColor(Color.WHITE)
                                                snackbar.show()
                                                vibratePhone()
                                            }else{
                                                if(binding.editSpinnerOccasion.selectedItem.toString() == "Occasion"){
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

                                                                if(newImageUrl == ""){
                                                                    newImageUrl = image
                                                                }

                                                                val products = Products(binding.editProductName.text.toString(),
                                                                    binding.editProductDes.text.toString(),binding.editSpinnerCategory.selectedItem.toString(),binding.editSpinnerColor.selectedItem.toString(),
                                                                    binding.spinnerDeal.selectedItem.toString(),binding.editSpinnerMaterial.selectedItem.toString(),id,newImageUrl,generateKeywords(binding.editProductName.text.toString().lowercase()),
                                                                    binding.editMrp.text.toString().toInt(),binding.editPrice.text.toString().toInt(),binding.editSpinnerPattern.selectedItem.toString(),binding.editStock.text.toString().toInt(),binding.editSpinnerOccasion.selectedItem.toString(),size
                                                                    ,tag)

                                                                val dialog2 = MaterialAlertDialogBuilder(requireContext())
                                                                dialog2.setTitle("Confirm")
                                                                dialog2.setMessage("Do you really want to update ${name} ?")
                                                                dialog2.setNegativeButton("Cancel") { dialog, which ->
                                                                    mdialog.dismiss()
                                                                }
                                                                dialog2.setPositiveButton("Yes") { dialog, which ->
                                                                    mdialog.show()
                                                                    FirebaseFirestore.getInstance().collection("Products")
                                                                        .document(id)
                                                                        .delete()
                                                                        .addOnSuccessListener {
                                                                            FirebaseFirestore.getInstance().collection("Products")
                                                                                .document(id)
                                                                                .set(products,
                                                                                    SetOptions.merge())
                                                                                .addOnSuccessListener {
                                                                                    mdialog.dismiss()
                                                                                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "Done", Snackbar.LENGTH_LONG)
                                                                                    val snackbarView = snackbar.view
                                                                                    snackbarView.setBackgroundColor(Color.BLACK)
                                                                                    snackbar.setTextColor(Color.WHITE)
                                                                                    snackbar.show()
                                                                                    vibratePhone()
                                                                                    listSize.clear()
                                                                                    listtag.clear()
                                                                                    size.clear()
                                                                                    tag.clear()
                                                                                    activity?.onBackPressed()
                                                                                }
                                                                                .addOnFailureListener {
                                                                                    mdialog.dismiss()
                                                                                    val snackbar = Snackbar.make(requireContext(),view!!.rootView, "${it.localizedMessage}", Snackbar.LENGTH_LONG)
                                                                                    val snackbarView = snackbar.view
                                                                                    snackbarView.setBackgroundColor(Color.RED)
                                                                                    snackbar.setTextColor(Color.WHITE)
                                                                                    snackbar.show()
                                                                                    vibratePhone()
                                                                                }
                                                                        }
                                                                        .addOnFailureListener {
                                                                            mdialog.dismiss()
                                                                            val snackbar = Snackbar.make(requireContext(),view!!.rootView, "${it.localizedMessage}", Snackbar.LENGTH_LONG)
                                                                            val snackbarView = snackbar.view
                                                                            snackbarView.setBackgroundColor(Color.RED)
                                                                            snackbar.setTextColor(Color.WHITE)
                                                                            snackbar.show()
                                                                            vibratePhone()
                                                                        }
                                                                }
                                                                dialog2.show()


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

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun generateKeywords(name: String): ArrayList<String> {
        val keywords = ArrayList<String>()
        for (i in 0 until name.length) {
            for (j in (i+1)..name.length) {
                keywords.add(name.slice(i until j))
            }
        }
        return keywords
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            if (data?.data == null){
                Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show()
            }else{
                mdialog.show()
                imageUri = data.data
                val imageExtension = imageUri.toString().substring(imageUri.toString().lastIndexOf(".")+1)
                val sRef = FirebaseStorage.getInstance().reference.child("/Products").child(
                    binding.editProductName.text.toString() + "_" + System.currentTimeMillis()+ "." + imageExtension.toString()
                )

                sRef.putFile(imageUri!!)
                    .addOnSuccessListener {takeSnapShot ->
                        takeSnapShot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {
                                mdialog.dismiss()
                                newImageUrl = it.toString()
                                binding.editAddImage.setImageURI(imageUri)
                            }
                            .addOnFailureListener {
                                mdialog.dismiss()
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

}
package com.example.gayatriladieswearsadmin

data class Products(
    val name:String = "",
    val dis:String = "",
    val category:String = "",
    val color:String = "",
    val deal:String = "",
    val fabric:String = "",
    val id:String = "",
    val image:String = "",
    val keywords:ArrayList<String> = ArrayList(),
    val mrp:Int = 0,
    val price:Int = 0,
    val pattern:String = "",
    val stock:Int = 0,
    val occasion:String = "",
    val size:ArrayList<String> = ArrayList(),
    val tag:ArrayList<String> = ArrayList()
)

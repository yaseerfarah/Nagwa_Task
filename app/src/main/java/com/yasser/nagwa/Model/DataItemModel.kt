package com.yasser.nagwa.Model

data class DataItemModel(
    val id:Int,
    val type:String,
    val url:String,
    val name:String,
    var isDownloaded:Boolean=false,
    var progress:Int=0
)

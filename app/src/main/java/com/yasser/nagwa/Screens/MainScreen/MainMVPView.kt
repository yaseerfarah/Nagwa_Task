package com.yasser.nagwa.Screens.MainScreen

import com.perfex.app.main.ui.base.callback.MvpView
import com.yasser.nagwa.Model.DataItemModel
import java.lang.Exception

interface MainMVPView : MvpView {


    fun onError(e:Throwable)
    fun onSuccessData(list:List<DataItemModel>)

    fun onDownloadProgress(progress:Int)
    fun onDownloadSuccess()
    fun onDownloadError(e:String)


}
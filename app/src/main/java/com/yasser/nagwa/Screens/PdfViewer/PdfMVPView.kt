package com.yasser.nagwa.Screens.PdfViewer

import com.perfex.app.main.ui.base.callback.MvpView
import com.yasser.nagwa.Model.DataItemModel
import java.lang.Exception

interface PdfMVPView : MvpView {

    fun onSuccessLoad()
    fun onError(e:Throwable)

}
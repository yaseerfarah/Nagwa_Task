package com.yasser.nagwa.Screens.PdfViewer


import com.github.barteksc.pdfviewer.PDFView
import com.perfex.app.main.ui.base.component.BasePresenter
import com.yasser.nagwa.Data.Repostitory
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.net.URL
import javax.inject.Inject


class PdfPresenter() : BasePresenter<PdfMVPView>() {


    fun preparePdfViewer(pdfViewer: PDFView,link:String){
        var  pdfReference=WeakReference<PDFView>(pdfViewer)

        compositeDisposable.add(Completable.create {emit->
            pdfReference.get()?.fromStream(URL(link).openStream())?.onLoad {
                if (!emit.isDisposed){
                    emit.onComplete()
                }
            }?.onError {
                if (!emit.isDisposed){
                    emit.onError(it)
                }
            }?.load()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mvpView?.onSuccessLoad()
            },{
                mvpView?.onError(it)
            }))


    }



}
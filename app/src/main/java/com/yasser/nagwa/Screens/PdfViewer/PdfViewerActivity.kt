package com.yasser.nagwa.Screens.PdfViewer

import android.content.Context
import android.content.Intent
import android.util.Log
import com.perfex.app.main.ui.base.component.BaseActivity
import com.yasser.nagwa.R
import com.yasser.nagwa.Utils.ViewUtils.Companion.show
import com.yasser.nagwa.databinding.ActivityMainBinding
import com.yasser.nagwa.databinding.ActivityPdfViewerBinding
import javax.inject.Inject


class PdfViewerActivity : BaseActivity<PdfMVPView, PdfPresenter>(),
    PdfMVPView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context,title:String,url:String) {
            val intent = Intent(context, PdfViewerActivity::class.java)
            intent.putExtra("link",url)
            intent.putExtra("Title",title)
            runCurrentActivity(context, intent)
        }
    }
    @Inject
    lateinit var pdfPresenter: PdfPresenter

    private lateinit var mBinding: ActivityPdfViewerBinding

    override val layoutResourceId: Int
        get() = R.layout.activity_pdf_viewer

    override fun getActivityPresenter(): PdfPresenter {
        return pdfPresenter
    }
    override fun startUI() {
        mBinding = viewDataBinding as ActivityPdfViewerBinding
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(intent.getStringExtra("Title")!!)
        var link=intent.getStringExtra("link")!!
        presenter.preparePdfViewer(mBinding.pdfViewer,link)

    }







    override fun stopUI() {

    }

    override fun onSuccessLoad() {
        mBinding.progress.hide()
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
       Log.e("ErrorLink",e.message.toString())
    }


}
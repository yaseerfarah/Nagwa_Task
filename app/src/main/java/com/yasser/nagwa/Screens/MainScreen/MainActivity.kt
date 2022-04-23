package com.yasser.nagwa.Screens.MainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.perfex.app.main.ui.base.component.BaseActivity
import com.yasser.nagwa.Model.DataItemModel
import com.yasser.nagwa.R
import com.yasser.nagwa.Screens.PdfViewer.PdfViewerActivity
import com.yasser.nagwa.Utils.ViewUtils
import com.yasser.nagwa.Utils.ViewUtils.Companion.hide
import com.yasser.nagwa.Utils.ViewUtils.Companion.show
import com.yasser.nagwa.Utils.callback.ItemDownloadClickListener
import com.yasser.nagwa.Utils.callback.ItemPlayVideoClickListener
import com.yasser.nagwa.Utils.callback.ItemViewPdfClickListener
import com.yasser.nagwa.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : BaseActivity<MainMVPView, MainPresenter>(),
    MainMVPView {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            runCurrentActivity(context, Intent(context, MainActivity::class.java))
        }
    }
    @Inject
    lateinit var mainPresenter: MainPresenter

    private lateinit var mBinding: ActivityMainBinding
    private val adapter: ItemsAdapter by lazy { ItemsAdapter(this) }
    private val listOfItems= mutableListOf<DataItemModel>()
    private var simpleExoPlayer:ExoPlayer? = null
    private var downloadItem:DataItemModel?=null

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun getActivityPresenter(): MainPresenter {
        return mainPresenter
    }
    override fun startUI() {
        mBinding = viewDataBinding as ActivityMainBinding
        setData()
        presenter.getItemsData()




    }







    private fun setData() {
        ViewUtils.initializeRecyclerView(
            mBinding.listRecycler,
            adapter,
            object : ItemDownloadClickListener<DataItemModel>
            {

                override fun onItemClick(view: View, item: DataItemModel, position: Int) {
                    if (downloadItem!=null){
                        ViewUtils.showSnackBar(this@MainActivity,"Wait until file finish download",false)
                    }else{
                        downloadItem=item
                        presenter.downloadItem(item)
                        adapter.isDownload=true

                    }
                }
            },
            object : ItemPlayVideoClickListener<DataItemModel>
            {

                override fun onItemClick(player: ExoPlayer?, item: DataItemModel, position: Int) {
                    releasePlayer()
                    simpleExoPlayer=player
                    startPlayer()
                }
            },
            object : ItemViewPdfClickListener<DataItemModel>
            {

                override fun onItemClick(view: View, item: DataItemModel, position: Int) {
                    PdfViewerActivity.startActivity(this@MainActivity,item.name,item.url)
                    //openWebPage(item.url)

                }
            },
            null,
            LinearLayoutManager(this),
            null,
            null,
        )

    }

    fun openWebPage(url: String?) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun startPlayer() {
        simpleExoPlayer?.playWhenReady=true
    }

    private fun releasePlayer() {
        simpleExoPlayer?.release()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
        adapter.releasePlayer()
    }

    override fun stopUI() {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        Log.e("DataError",e.message.toString())
    }

    override fun onSuccessData(list: List<DataItemModel>) {
        mBinding.progress.hide()
        mBinding.listRecycler.show()
       listOfItems.addAll(list)
        adapter.clear()
        adapter.addItems(listOfItems)


    }

    override fun onDownloadProgress(progress: Int) {
        var oldItem=downloadItem
        if (progress<0){
            downloadItem!!.progress++
        }else{
            downloadItem!!.progress=progress
        }

      adapter.updateItem(downloadItem!!,oldItem!!)
    }

    override fun onDownloadSuccess() {
        adapter.isDownload=false
        var oldItem=downloadItem
        downloadItem!!.isDownloaded=true
        downloadItem!!.progress=0
        adapter.updateItem(downloadItem!!,oldItem!!)
        downloadItem=null
    }

    override fun onDownloadError(e:String) {
        if (downloadItem!=null){
            adapter.isDownload=false
            var oldItem=downloadItem
            downloadItem!!.progress=0
            adapter.updateItem(downloadItem!!,oldItem!!)
            downloadItem=null
        }

        Log.e("DataError",e)
    }


}
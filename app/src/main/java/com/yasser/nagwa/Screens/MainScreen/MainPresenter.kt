package com.yasser.nagwa.Screens.MainScreen


import android.os.Build
import android.os.Environment
import android.util.Log
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.perfex.app.main.ui.base.component.BasePresenter
import com.yasser.nagwa.Data.Repostitory
import com.yasser.nagwa.Model.DataItemModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection


class MainPresenter( val repostitory:Repostitory) : BasePresenter<MainMVPView>() {

    val pathSave= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "Nagwa_Task"


    fun getItemsData(){

        repostitory.getItemsData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            val list=it.map {item->
                val namesArr=item.url.split("/")
                val file=File(pathSave+File.separator+item.id.toString()+"-"+namesArr[namesArr.size-1])
                if (file.exists()){
                    item.isDownloaded=true
                }
                return@map item
            }.toList()
           mvpView?.onSuccessData(list)
        },{
           mvpView?.onError(it)

        })

    }


    fun downloadItem(item:DataItemModel){
        val namesArr=item.url.split("/")
        var prevProgress=-1
        PRDownloader.download(item.url, pathSave, item.id.toString()+"-"+namesArr[namesArr.size-1])
            .setTag(item.id)
            .build()
            .setOnStartOrResumeListener {

            }
            .setOnPauseListener {

            }
            .setOnProgressListener {
                var progress=((it.currentBytes*100)/it.totalBytes).toInt()
                if (progress>prevProgress) {
                    prevProgress=progress
                    mvpView?.onDownloadProgress(progress)
                }
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {

                    mvpView?.onDownloadSuccess()
                }
                override fun onError(error: com.downloader.Error?) {
                   Log.e("onError" ,error.toString())
                    mvpView?.onDownloadError(error.toString())
                }

            })



    }




    private fun getFileSizeOfUrl(url: String): Long {
        var urlConnection: URLConnection? = null
        try {
            val uri = URL(url)
            urlConnection = uri.openConnection()
            urlConnection!!.connect()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                return urlConnection.contentLengthLong
            val contentLengthStr = urlConnection.getHeaderField("content-length")
            return if (contentLengthStr.isNullOrEmpty()) -1 else contentLengthStr.toLong()
        } catch (ignored: Exception) {
        } finally {
            if (urlConnection is HttpURLConnection)
                urlConnection.disconnect()
        }
        return -1
    }

}
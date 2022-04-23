package com.yasser.nagwa.Screens.MainScreen

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.perfex.app.main.ui.base.component.BaseAdapter
import com.perfex.app.main.ui.base.component.BaseViewHolder
import com.yasser.nagwa.Model.DataItemModel
import com.yasser.nagwa.R
import com.yasser.nagwa.Utils.ViewUtils.Companion.hide
import com.yasser.nagwa.Utils.ViewUtils.Companion.show
import com.yasser.nagwa.Utils.makeItInvisible
import com.yasser.nagwa.Utils.makeItVisible
import com.yasser.nagwa.databinding.ItemLayoutBinding


class ItemsAdapter(val context: Context) : BaseAdapter<DataItemModel>() {
    public var statesList:List<DataItemModel>?=null
    var playerIndex=-1
    var prevPlayer:ItemLayoutBinding?=null
    var currentExoPlayer:ExoPlayer?=null
    var isDownload=false



    override fun isEqual(left: DataItemModel, right: DataItemModel): Boolean {
        return left == right
    }

    override fun newViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DataItemModel> {
        return ItemViewHolder(
                inflate(
                        parent,
                        R.layout.item_layout
                )
        )
    }


    override fun onViewRecycled(holder: BaseViewHolder<DataItemModel>) {
        if (holder.absoluteAdapterPosition==playerIndex){
            currentExoPlayer?.release()
            releasePlayer()
        }
    }

     fun releasePlayer() {
        currentExoPlayer = null
        prevPlayer?.playVideo?.show()
        prevPlayer?.image?.show()
        prevPlayer = null
        playerIndex = -1
    }

    inner class ItemViewHolder(binding: ViewDataBinding) : BaseViewHolder<DataItemModel>(binding) {
        private var mBinding = binding as ItemLayoutBinding
        override fun bind(item: DataItemModel) {

            mBinding.title.text=item.name



            if (item.type=="VIDEO") {

                Glide.with(itemView).load(item.url)
                    .apply(RequestOptions.timeoutOf(5 * 60 * 1000))
                    .placeholder(R.drawable.placeholder)
                    .into(mBinding.image)
                mBinding.playVideo.makeItVisible()
                mBinding.pdfViewBtn.makeItInvisible()






                mBinding.playVideo.setOnClickListener {
                    var simpleExoPlayer: ExoPlayer? = initExoPlayer(item)

                    mItemPlayVideoClickListener?.onItemClick(simpleExoPlayer,item,absoluteAdapterPosition)
                    prevPlayer?.playVideo?.show()
                    prevPlayer?.image?.show()
                    currentExoPlayer=simpleExoPlayer
                    playerIndex=absoluteAdapterPosition
                    mBinding.playVideo.hide()
                    mBinding.image.hide()
                    prevPlayer=mBinding
                }

            }else{
                Glide.with(itemView).load("https://play-lh.googleusercontent.com/pdO3MTPb7XDqLeM8Zd54QWfL1gxkG-gWkq5GKj85AWXTSmdgvdmwA8cuYUCSbKo5PXs")
                    .apply(RequestOptions.timeoutOf(5 * 60 * 1000))
                    .placeholder(R.drawable.placeholder)
                    .into(mBinding.image)
                mBinding.pdfViewBtn.makeItVisible()
                mBinding.playVideo.makeItInvisible()
                mBinding.pdfViewBtn.setOnClickListener {
                    mItemViewPdfClickListener?.onItemClick(mBinding.downloadBtn,item,absoluteAdapterPosition)
                }
            }



            if (playerIndex==absoluteAdapterPosition){
                mBinding.playVideo.hide()
                mBinding.image.hide()
            }else{
                mBinding.playVideo.show()
                mBinding.image.show()
            }

            if (item.isDownloaded){
                mBinding.downloadBtn.show()
                mBinding.loadingLayout.hide()
                mBinding.downloadBtn.text="Downloaded"
                mBinding.downloadBtn.setStrokeColorResource(R.color.teal_700)
                mBinding.downloadBtn.setTextColor(context.resources.getColor(R.color.teal_700))
            }else if (!item.isDownloaded&&item.progress>0){
                mBinding.downloadBtn.hide()
                mBinding.loadingLayout.show()
                mBinding.progressText.text=item.progress.toString()+"%"
            }else{
                mBinding.downloadBtn.show()
                mBinding.loadingLayout.hide()
                mBinding.downloadBtn.text="Download"
                mBinding.downloadBtn.setStrokeColorResource(R.color.white)
                mBinding.downloadBtn.setTextColor(context.resources.getColor(R.color.white))
            }

            mBinding.downloadBtn.setOnClickListener {
                if (!item.isDownloaded){
                    if (!isDownload){
                        mBinding.downloadBtn.hide()
                        mBinding.loadingLayout.show()
                        mBinding.progressText.text=item.progress.toString()+"%"
                        if (playerIndex==absoluteAdapterPosition){
                            playerIndex=-1
                            currentExoPlayer?.release()
                            mBinding.playVideo.show()
                            mBinding.image.show()
                        }
                    }
                    mItemDownloadClickListener?.onItemClick(mBinding.downloadBtn,item,absoluteAdapterPosition)
                }

            }





        }
        fun isPlaying(exoPlayer:ExoPlayer?): Boolean {
            return exoPlayer?.getPlaybackState() === Player.STATE_READY && exoPlayer?.getPlayWhenReady()
        }
        private fun initExoPlayer(item: DataItemModel): ExoPlayer? {
            var simpleExoPlayer: ExoPlayer? = null


            val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(context)

            val mediaSource =
                ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
                    MediaItem.fromUri(item.url)
                )

            val mediaSourceFactory: MediaSourceFactory =
                DefaultMediaSourceFactory(mediaDataSourceFactory)

            simpleExoPlayer = ExoPlayer.Builder(context)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()

            simpleExoPlayer.addMediaSource(mediaSource)
            mBinding.player.player = simpleExoPlayer
            simpleExoPlayer.prepare()
            return simpleExoPlayer
        }

    }
}
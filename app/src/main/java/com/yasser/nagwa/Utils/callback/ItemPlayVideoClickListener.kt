package com.yasser.nagwa.Utils.callback

import com.google.android.exoplayer2.ExoPlayer
import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.perfex.app.main.ui.base.component.BaseAdapter

interface ItemPlayVideoClickListener<T> : ItemClickListener<T> {
    /**
     * This method sets this click listener to the adapter
     *
     * @param adapter RecyclerView adapter
     * */
    fun setAdapter(adapter: BaseAdapter<T>) {
        adapter.setItemPlayVideoClickListener(this)
    }

    fun onItemClick(player: ExoPlayer?, item: T, position: Int) {}
}
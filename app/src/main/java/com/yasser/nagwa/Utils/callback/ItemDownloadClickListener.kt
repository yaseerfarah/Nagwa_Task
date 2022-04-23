package com.yasser.nagwa.Utils.callback

import com.perfex.app.main.ui.base.callback.ItemClickListener
import com.perfex.app.main.ui.base.component.BaseAdapter

interface ItemDownloadClickListener<T> : ItemClickListener<T> {
    /**
     * This method sets this click listener to the adapter
     *
     * @param adapter RecyclerView adapter
     * */
    fun setAdapter(adapter: BaseAdapter<T>) {
        adapter.setItemDownloadClickListener(this)
    }
}
package com.yasser.nagwa.Utils

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.perfex.app.main.ui.base.component.BasePresenter


/**
 * This file is the collection of all the extension functions to be used in the UI
 * @author Mohd. Asfaq-E-Azam Rifat
 * */

fun View.isVisibile(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.makeItVisible() {
    visibility = View.VISIBLE
}

fun View.makeItInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeItGone() {
    visibility = View.GONE
}


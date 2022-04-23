package com.yasser.nagwa.Dagger.Component

import com.yasser.nagwa.Screens.MainScreen.MainActivity
import com.yasser.nagwa.Screens.PdfViewer.PdfViewerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector()
    abstract fun contributePdfViewerActivity(): PdfViewerActivity?


}
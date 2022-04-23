package com.yasser.nagwa.Dagger

import android.content.Context
import com.yasser.nagwa.Data.Repostitory
import com.yasser.nagwa.Screens.MainScreen.MainPresenter
import com.yasser.nagwa.Screens.PdfViewer.PdfPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
 class PresenterModule {

    @Singleton
    @Provides
    fun ProvideMainPresenter(repostitory: Repostitory):MainPresenter{
        return MainPresenter(repostitory)
    }

    @Singleton
    @Provides
    fun ProvidePdfViewerPresenter():PdfPresenter{
        return PdfPresenter()
    }

}